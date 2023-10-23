package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.OAuth2ClientLocal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2ClientLocalRepository extends JpaRepository<OAuth2ClientLocal, Long> {
    Optional<OAuth2ClientLocal> findByOauthId(String oathId);

}