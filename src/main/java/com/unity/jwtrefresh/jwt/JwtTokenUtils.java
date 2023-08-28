package com.unity.jwtrefresh.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.unity.jwtrefresh.security.ServiceUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.unity.jwtrefresh.constants.UnityConstants.*;
import static java.util.Arrays.stream;

@Component
@Log4j2
public class JwtTokenUtils {

    @Value("${access.token.key}")
    private String accessTokenKey;

    @Value("${refresh.token.key}")
    private String refreshTokenKey;

    @Value("${get.issuer}")
    private String getIssuer;

    @Value("${get.audience}")
    private String getAudience;


    public String generateJwtToken(ServiceUserDetails userDetails) {
        String[] claims = getClaimsFromUser(userDetails); // method for extract user authorities
        return JWT.create()
                .withIssuer(getIssuer)
                .withAudience(getAudience)
                .withIssuedAt(new Date())
                .withSubject(userDetails.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(accessTokenKey.getBytes()));
    }


    public String generateRefreshToken(ServiceUserDetails userDetails) {

        return JWT.create()
                .withIssuer(getIssuer)
                .withAudience(getAudience)
                .withIssuedAt(new Date())
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + THREE_DAYS_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(refreshTokenKey.getBytes()));
    }


    private String[] getClaimsFromUser(ServiceUserDetails userDetails) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority ga : userDetails.getAuthorities()) {
            authorities.add(ga.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        String[] claims = getClaimsFromToken(token);
        assert claims != null;
        return stream(claims).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJwtVerifier(accessTokenKey);
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    public Date getTokenExpiryDate(@NonNull String token) {
        JWTVerifier verifier = this.getJwtVerifier(accessTokenKey);
        return verifier.verify(token).getExpiresAt();
    }

    private JWTVerifier getJwtVerifier(String secret) {
        final JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(getIssuer).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    public String getSubject(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier(accessTokenKey);
        String subject = null;
        try {
            subject = jwtVerifier.verify(token).getSubject();
        } catch (TokenExpiredException | SignatureVerificationException e) {
            log.error("Exception: {} --- Dated: {}", e.getLocalizedMessage(), LocalDateTime.now());
        }
        return subject;
    }

    public boolean isAccessTokenValid(String token, String userName) {
        JWTVerifier verifier = getJwtVerifier(accessTokenKey);
        return StringUtils.isNotEmpty(userName) && !isAccessTokenExpired(verifier, token);
    }


    private boolean isAccessTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }


    public Authentication getAuthentication(String userName,
                                            List<GrantedAuthority> grantedAuthorities,
                                            HttpServletRequest request) {

        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(userName, null, grantedAuthorities);
        token.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        return token;
    }


    public ResponseCookie generateHttpOnlyCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(THREE_DAYS_EXPIRATION_TIME)
                .httpOnly(true)
                .domain("localhost")
                .secure(true)
                .build();
    }

    public String getHttpOnlyCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (Objects.nonNull(cookie)) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

}
