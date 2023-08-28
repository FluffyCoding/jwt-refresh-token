package com.unity.jwtrefresh.dao;

import com.unity.jwtrefresh.dtos.ModuleAgainstRoleResponse;
import com.unity.jwtrefresh.entities.AppModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppModuleRepository extends JpaRepository<AppModule, String> {


    @Query(value = """
            select am.id
            from app_modules am
                     join roles_modules rm on (am.id = rm.app_modules_id)
                     join app_roles ar on (rm.app_roles_id = ar.id)
            where rm.app_roles_id = :id
            """, nativeQuery = true)
    List<String> fetchAllModulesAgainstRollIdNative(@Param("id") String id);

    @Query(value = """
            select rm.appRoles.roleName, am.moduleName
            from AppModule am
            join RolesModule rm on am.id =  rm.appRoles.id
            join AppRole ar on rm.appRoles.id = ar.id
            where rm.appRoles.id = ?1
            """)
    List<ModuleAgainstRoleResponse> fetchAllModulesAgainsRollId(String id);

}