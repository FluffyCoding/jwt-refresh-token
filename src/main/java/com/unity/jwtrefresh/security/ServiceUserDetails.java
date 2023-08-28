package com.unity.jwtrefresh.security;

import com.unity.jwtrefresh.entities.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
public class ServiceUserDetails implements UserDetails {

    private final ServiceUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Assert.notNull(user.getAuthorities(), "Invalid User Authorities");
        return stream(user.getAuthorities())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
