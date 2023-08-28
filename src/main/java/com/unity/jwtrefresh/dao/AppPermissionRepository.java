package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.AppPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPermissionRepository extends JpaRepository<AppPermission, String> {
}