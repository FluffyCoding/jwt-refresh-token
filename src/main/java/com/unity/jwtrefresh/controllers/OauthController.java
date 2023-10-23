package com.unity.jwtrefresh.controllers;

import com.unity.jwtrefresh.dtos.JwtAuthenticationResponse;
import com.unity.jwtrefresh.dtos.LoginResponseDto;
import com.unity.jwtrefresh.jwt.JwtTokenUtils;
import com.unity.jwtrefresh.services.OAuth2ClientDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OAuth2ClientDataService clientDataService;

    @GetMapping("/oauth2/success")
    public ResponseEntity<LoginResponseDto> oauth2Success(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
        return ResponseEntity.ok(clientDataService.saveNewClient(client));
    }


    @GetMapping(path = "/")
    public void homeCallback() {

    }
}
