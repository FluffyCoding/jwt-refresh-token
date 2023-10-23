package com.unity.jwtrefresh.services.impl;

import com.unity.jwtrefresh.dao.OAuth2ClientLocalRepository;
import com.unity.jwtrefresh.dao.ServiceUserRepository;
import com.unity.jwtrefresh.dtos.LoginResponseDto;
import com.unity.jwtrefresh.entities.OAuth2ClientLocal;
import com.unity.jwtrefresh.jwt.JwtTokenUtils;
import com.unity.jwtrefresh.services.OAuth2ClientDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Service
@RequiredArgsConstructor
public class OAuth2ClientDataServiceImplementation implements OAuth2ClientDataService {

    private final OAuth2ClientLocalRepository oAuth2ClientLocalRepository;
    private final WebClient webClient;
    private final JwtTokenUtils jwtTokenUtils;
    private final ServiceUserRepository userRepository;

    @Override
    public LoginResponseDto findClientAgainstClient(OAuth2AuthorizedClient client) {
        var clientFound = oAuth2ClientLocalRepository.findByOauthId(client.getPrincipalName().toString())
                .orElse(null);
        return generateNewToken(clientFound);
    }

    @Override
    public LoginResponseDto saveNewClient(OAuth2AuthorizedClient client) {

        var clientFound = oAuth2ClientLocalRepository.findByOauthId(client.getPrincipalName().toString()).orElse(null);

        if (Objects.nonNull(clientFound)) {
            clientFound.setLastAccessed(LocalDateTime.now());
            oAuth2ClientLocalRepository.save(clientFound);
            return generateNewToken(clientFound);
        } else {
            OAuth2ClientLocal clientLocal = new OAuth2ClientLocal();
            var clientAttributes = getClientAttributes(client);

            clientLocal.setOauthId(client.getPrincipalName().toString());
            if (clientAttributes.containsKey("email"))
                clientLocal.setEmail(clientAttributes.get("email"));
            clientLocal.setFullName(clientAttributes.get("name"));
            clientLocal.setPictureUrl(clientAttributes.get("picture"));
            clientLocal.setAuthorities(new String[]{"basic:user"});
            clientLocal.setCreatedAt(LocalDateTime.now());
            OAuth2ClientLocal clientSaved = oAuth2ClientLocalRepository.save(clientLocal);
            return generateNewToken(clientSaved);
        }
    }

    private Map<String, String> getClientAttributes(OAuth2AuthorizedClient authorizedClient) {
        String userInfoEndpointUri = authorizedClient.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        Map userAttributes = this.webClient
                .get()
                .uri(userInfoEndpointUri)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return userAttributes;
    }


    private LoginResponseDto generateNewToken(OAuth2ClientLocal clientLocal) {
        String accessToken = jwtTokenUtils.generateToken(clientLocal);
        Date expiryDate = jwtTokenUtils.getTokenExpiryDate(accessToken);
        var totalLifeTime = expiryDate.getTime() - new Date().getTime();
        var time = TimeUnit.MILLISECONDS.toMinutes(totalLifeTime);
        SecurityContextHolder.clearContext();
        return LoginResponseDto.builder()
                .token(accessToken)
                .expiryTime(time + 1 + " Minutes")
                .loginId(clientLocal.getOauthId())
                .build();
    }


}
