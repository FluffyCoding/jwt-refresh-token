package com.unity.jwtrefresh.controllers;

import com.unity.jwtrefresh.dtos.LoginResponseDto;
import com.unity.jwtrefresh.dtos.ServiceUserLoginDto;
import com.unity.jwtrefresh.dtos.ServiceUserRegisterDto;
import com.unity.jwtrefresh.jwt.JwtTokenUtils;
import com.unity.jwtrefresh.services.ServiceUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {


    @Value("${jwt-cookie-name}")
    private String accessTokenName;

    @Value("${jwt-refresh-cookie-name}")
    private String refreshTokenName;

    private final ServiceUserAuthService authService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/register")
    public LoginResponseDto registerNewUser(@RequestBody ServiceUserRegisterDto userRegisterDto) {
        return authService.registerNewUser(userRegisterDto);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticationUser(@RequestBody ServiceUserLoginDto loginDto) {
        LoginResponseDto responseDto = authService.authenticateUser(loginDto);

        ResponseCookie accessTokenCookie = jwtTokenUtils.generateHttpOnlyCookie(accessTokenName, responseDto.getToken(), "/v1/");
        ResponseCookie refreshTokenCookie = jwtTokenUtils.generateHttpOnlyCookie(refreshTokenName, responseDto.getRefreshToken(), "/v1/auth/");


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(responseDto);
    }

}
