package com.unity.jwtrefresh.security.filters;

import com.unity.jwtrefresh.jwt.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.unity.jwtrefresh.constants.UnityConstants.OPTIONS_HTTP_METHOD;
import static com.unity.jwtrefresh.constants.UnityConstants.TOKEN_PREFIX;

@RequiredArgsConstructor
@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    @Value("${jwt-cookie-name}")
    private String accessTokenName;

    @Value("${jwt-refresh-cookie-name}")
    private String refreshTokenName;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {




        String jwtToken = null;
        String accessToken = jwtTokenUtils.getHttpOnlyCookieValueByName(request, accessTokenName);
        log.info("Access Token From Cookie is {}", accessToken);

        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                if (Objects.nonNull(accessToken)) {
                    jwtToken = accessToken;
                } else {
                    filterChain.doFilter(request, response);
                    return;
                }

            } else {
                jwtToken = authorizationHeader.substring(TOKEN_PREFIX.length());
            }

            final String grantedUser = jwtTokenUtils.getSubject(jwtToken);

            if (jwtTokenUtils.isAccessTokenValid(jwtToken, grantedUser) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenUtils.getAuthoritiesFromToken(jwtToken);
                Authentication authentication = jwtTokenUtils.getAuthentication(grantedUser, authorities, request);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
