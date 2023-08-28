package com.unity.jwtrefresh.config;

import com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor;
import com.unity.jwtrefresh.dao.ServiceUserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Key;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

    private final ServiceUserRepository userRepository;


//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> (UserDetails)
//                userRepository.findByEmail(username).orElseThrow(EntityExistsException::new);
//    }



    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }

}
