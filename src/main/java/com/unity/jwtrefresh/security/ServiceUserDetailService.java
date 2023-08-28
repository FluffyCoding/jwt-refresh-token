package com.unity.jwtrefresh.security;

import com.unity.jwtrefresh.dao.ServiceUserRepository;
import com.unity.jwtrefresh.entities.ServiceUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.unity.jwtrefresh.constants.UnityConstants.FORBIDDEN_MESSAGE;

@Service
@RequiredArgsConstructor
public class ServiceUserDetailService implements UserDetailsService {

    private final ServiceUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServiceUser serviceUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Invalid Username or Password."));
        return new ServiceUserDetails(serviceUser);
    }
}
