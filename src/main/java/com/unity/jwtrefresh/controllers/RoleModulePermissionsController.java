package com.unity.jwtrefresh.controllers;

import com.unity.jwtrefresh.dtos.AppModuleResponseDto;
import com.unity.jwtrefresh.dtos.AppRoleResponseDto;
import com.unity.jwtrefresh.dtos.RegisterPermissionsDto;
import com.unity.jwtrefresh.services.impl.RollModulesPermissionsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/rolls")
public class RoleModulePermissionsController {


    private final RollModulesPermissionsServiceImpl permissionsService;


    @PostMapping(path = "/assign")
    public ResponseEntity<String> assignPermissions(@RequestBody RegisterPermissionsDto permissionsDto) {
        String message = permissionsService.assignPermissionsOfModuleToRole(permissionsDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AppRoleResponseDto>> findAllRoles() {
        return ResponseEntity.ok(permissionsService.findAllExistingRoles());
    }

    @GetMapping(path = "/modules/all")
    public ResponseEntity<List<AppModuleResponseDto>> findAllModule() {
        return ResponseEntity.ok(permissionsService.readAllRegisteredModule());
    }

    @GetMapping(path = "/modules/id")
    public ResponseEntity<List<String>> findRegisterModuleAgainstRole(@RequestParam String roleId) {
        return ResponseEntity.ok(permissionsService.fetchAllModuleAgainstRole(roleId));
    }

    @GetMapping(path = "/modules/permissions")
    public ResponseEntity<List<String>> findRegisterPermissionsAgainstRole(@RequestParam String roleId) {
        return ResponseEntity.ok(permissionsService.fetchAllGrantedPermissionAgainstRole(roleId));
    }

}
