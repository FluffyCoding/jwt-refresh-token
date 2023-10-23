package com.unity.jwtrefresh.config;

import com.unity.jwtrefresh.dao.ServiceUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
