package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.ServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {
    Optional<ServiceUser> findByEmail(String email);

    Optional<ServiceUser> findByLoginId(String loginId);




}
