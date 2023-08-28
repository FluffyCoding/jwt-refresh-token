package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserRefreshToken, Long> {



}
