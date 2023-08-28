package com.unity.jwtrefresh.services;

import com.unity.jwtrefresh.dtos.AppRoleRequestDto;
import com.unity.jwtrefresh.dtos.AppRoleResponseDto;
import com.unity.jwtrefresh.dtos.AppModuleResponseDto;
import com.unity.jwtrefresh.dtos.RegisterPermissionsDto;

import java.util.List;

public interface RollModulesPermissionsService {

    AppRoleResponseDto registerNewRole(AppRoleRequestDto requestDto);

    AppRoleResponseDto updateExistingRole(String roleId, AppRoleRequestDto requestDto);

    List<AppRoleResponseDto> findAllExistingRoles();

    void deleteExistingRole(String roleId);

    List<AppModuleResponseDto> readAllRegisteredModule();

    List<String> fetchAllModuleAgainstRole(String roleId);

    String assignPermissionsOfModuleToRole(RegisterPermissionsDto permissionsDto);

    String updatePermissionsOfModuleToRole(RegisterPermissionsDto permissionsDto);

    String deleteAllPermissionsToRole(String roleId);

    List<String> fetchAllGrantedPermissionAgainstRole(String roleId);

}
