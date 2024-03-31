package com.unity.jwtrefresh.services.impl;

import com.unity.jwtrefresh.dao.AppModuleRepository;
import com.unity.jwtrefresh.dao.AppRoleRepository;
import com.unity.jwtrefresh.dao.ModulesPermissionRepository;
import com.unity.jwtrefresh.dtos.*;
import com.unity.jwtrefresh.entities.AppModule;
import com.unity.jwtrefresh.entities.PagePermission;
import com.unity.jwtrefresh.entities.AppRole;
import com.unity.jwtrefresh.entities.ModulesPermission;
import com.unity.jwtrefresh.services.RollModulesPermissionsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Log4j2
public class RollModulesPermissionsServiceImpl implements RollModulesPermissionsService {

    private final AppRoleRepository roleRepository;
    private final AppModuleRepository moduleRepository;
    private final ModulesPermissionRepository modulesPermissionRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppRoleResponseDto registerNewRole(AppRoleRequestDto requestDto) {
        boolean present = roleRepository.findByRoleNameIgnoreCase(requestDto.roleName()).isPresent();
        if (present) throw new EntityExistsException("role already exists");
        AppRole appRole = roleRepository.save(modelMapper.map(requestDto, AppRole.class));
        return modelMapper.map(appRole, AppRoleResponseDto.class);
    }

    @Override
    public AppRoleResponseDto updateExistingRole(String rollId, AppRoleRequestDto requestDto) {

        AppRole appRole = roleRepository
                .findById(rollId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        modelMapper.map(requestDto, appRole);
        return modelMapper.map(roleRepository.save(appRole), AppRoleResponseDto.class);

    }

    @Override
    public List<AppRoleResponseDto> findAllExistingRoles() {
        return roleRepository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, AppRoleResponseDto.class))
                .toList();
    }

    @Override
    public void deleteExistingRole(String roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public List<AppModuleResponseDto> readAllRegisteredModule() {
        return moduleRepository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, AppModuleResponseDto.class))
                .toList();
    }

    @Override
    public List<String> fetchAllModuleAgainstRole(@NotNull String roleId) {
        return moduleRepository.fetchAllModulesAgainstRollIdNative(roleId);
    }


    @Override
    public String assignPermissionsOfModuleToRole(RegisterPermissionsDto permissionsDto) {
        return assignModuleAndPermissionsToRole(permissionsDto);
    }


    @Override
    public String updatePermissionsOfModuleToRole(RegisterPermissionsDto permissionsDto) {
        int all = modulesPermissionRepository.deleteAllByRoll(permissionsDto.getRollId());
        log.info("Output From Delete Api {}", all);
        return assignPermissionsOfModuleToRole(permissionsDto);
    }

    @Override
    public String deleteAllPermissionsToRole(String roleId) {
        int all = modulesPermissionRepository.deleteAllByRoll(roleId);
        return String.valueOf(all);
    }

    @Override
    public List<String> fetchAllGrantedPermissionAgainstRole(String roleId) {
        return modulesPermissionRepository.getAllAuthorities(roleId);
    }

    private String assignModuleAndPermissionsToRole(RegisterPermissionsDto permissionsDto) {
        List<AuthorizedModule> modules = permissionsDto.getAuthorizedModules();
        Set<ModulesPermission> permissionSet = new HashSet<>();

        for (var m : modules) {
            for (var p : m.getPermissions()) {
                var module = new AppModule(m.getModuleId());
                var permission = new PagePermission(p.getGrantedPermissionId());
                var modulesPermission = new ModulesPermission(module, permission, permissionsDto.getRollId());
                permissionSet.add(modulesPermission);
            }
        }
        modulesPermissionRepository.saveAll(permissionSet);

        return modules.size() + " modules and " + permissionSet.size() + " are assigned to this role ";
    }


}


