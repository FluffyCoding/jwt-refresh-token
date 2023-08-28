package com.unity.jwtrefresh.services;

import com.unity.jwtrefresh.dao.UserTokenRepository;
import com.unity.jwtrefresh.dtos.LoginResponseDto;
import com.unity.jwtrefresh.dtos.ServiceUserLoginDto;
import com.unity.jwtrefresh.dtos.ServiceUserRegisterDto;
import com.unity.jwtrefresh.dtos.ServiceUserResponseDto;
import com.unity.jwtrefresh.entities.ServiceUser;

public interface ServiceUserAuthService {

    LoginResponseDto registerNewUser(ServiceUserRegisterDto registerDto);

    LoginResponseDto authenticateUser(ServiceUserLoginDto loginDto);

    ServiceUserResponseDto findUserById(Long id);

    LoginResponseDto renewAcccessTokenWIthRefresToken(String token, String user);

}
