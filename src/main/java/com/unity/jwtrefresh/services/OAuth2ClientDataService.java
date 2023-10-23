package com.unity.jwtrefresh.services;

import com.unity.jwtrefresh.dtos.LoginResponseDto;
import com.unity.jwtrefresh.dtos.OAuth2ClientDto;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

public interface OAuth2ClientDataService {

    LoginResponseDto findClientAgainstClient(OAuth2AuthorizedClient client);

    LoginResponseDto saveNewClient(OAuth2AuthorizedClient client);

}
