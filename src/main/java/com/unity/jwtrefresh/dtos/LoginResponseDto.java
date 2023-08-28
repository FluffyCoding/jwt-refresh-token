package com.unity.jwtrefresh.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private String loginId;
    private String token;
    private String expiryTime;
    private String refreshToken;


}
