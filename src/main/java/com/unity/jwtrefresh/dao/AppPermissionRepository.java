package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.PagePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPermissionRepository extends JpaRepository<PagePermission, String> {
}