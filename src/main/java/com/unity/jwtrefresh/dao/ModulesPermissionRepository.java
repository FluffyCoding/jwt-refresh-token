package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.entities.ModulesPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModulesPermissionRepository extends JpaRepository<ModulesPermission, String> {


    @Query(value = "CALL fetch_granted_authorities(:roleId);",nativeQuery = true)
    List<String> getAllAuthorities(@Param("roleId") String roleId);

    @Transactional
    @Modifying
    @Query("delete from ModulesPermission m where m.roleId = ?1")
    int deleteAllByRoll(@NotEmpty String roleId);


}