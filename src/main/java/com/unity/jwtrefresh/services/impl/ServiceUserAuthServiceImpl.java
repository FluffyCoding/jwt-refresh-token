package com.unity.jwtrefresh.services.impl;

import com.unity.jwtrefresh.dao.ServiceUserRepository;
import com.unity.jwtrefresh.dtos.LoginResponseDto;
import com.unity.jwtrefresh.dtos.ServiceUserLoginDto;
import com.unity.jwtrefresh.dtos.ServiceUserRegisterDto;
import com.unity.jwtrefresh.dtos.ServiceUserResponseDto;
import com.unity.jwtrefresh.entities.ServiceUser;
import com.unity.jwtrefresh.enums.Role;
import com.unity.jwtrefresh.jwt.JwtTokenUtils;
import com.unity.jwtrefresh.security.ServiceUserDetails;
import com.unity.jwtrefresh.services.ServiceUserAuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.unity.jwtrefresh.constants.UnityConstants.FORBIDDEN_MESSAGE;

@Service
@RequiredArgsConstructor
public class ServiceUserAuthServiceImpl implements ServiceUserAuthService {

    private final ServiceUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtTokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto registerNewUser(ServiceUserRegisterDto userRegisterDto) {
        userRepository.findByLoginId(userRegisterDto.getLoginId())
                .ifPresent(user -> {
                    throw new EntityExistsException(user.getLoginId() + "already Exist");
                });

        ServiceUser serviceUser = modelMapper.map(userRegisterDto, ServiceUser.class);
        serviceUser.setActive(true);
        serviceUser.setDeleted(false);
        serviceUser.setPassword(passwordEncoder.encode(serviceUser.getPassword()));
        serviceUser.setRole("ROLE_ADMIN");
        serviceUser.setAuthorities(Role.ROLE_ADMIN.getAuthorities());
        ServiceUser savedUser = userRepository.save(serviceUser);
        return generateNewToken(savedUser);
    }

    @Override
    public LoginResponseDto authenticateUser(ServiceUserLoginDto loginDto) {
        var user = userRepository.findByLoginId(loginDto.loginId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Username or Password."));

        authenticateLoginRequest(user.getEmail(), loginDto.password());

        return generateNewToken(user);
    }

    @Override
    public ServiceUserResponseDto findUserById(Long id) {
        ServiceUser serviceUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        return modelMapper.map(serviceUser, ServiceUserResponseDto.class);
    }


    private void authenticateLoginRequest(String loginId, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginId, password));
    }


    private LoginResponseDto generateNewToken(ServiceUser savedUser) {
        String accessToken = tokenUtils.generateJwtToken(new ServiceUserDetails(savedUser));
        String refreshToken = tokenUtils.generateRefreshToken(new ServiceUserDetails(savedUser));
        Date expiryDate = tokenUtils.getTokenExpiryDate(accessToken);
        var totalLifeTime = expiryDate.getTime() - new Date().getTime();
        var time = TimeUnit.MILLISECONDS.toMinutes(totalLifeTime);


        return LoginResponseDto.builder()
                .token(accessToken)
                .expiryTime(time + 1 + " Minutes")
                .loginId(savedUser.getLoginId())
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public LoginResponseDto renewAcccessTokenWIthRefresToken(String token, String user) {
        return null;
    }
}
