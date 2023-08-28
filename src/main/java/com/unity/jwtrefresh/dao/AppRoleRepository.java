package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    Optional<AppRole> findByRoleNameIgnoreCase(String roleName);





}