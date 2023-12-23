package com.masroufi.api.service;

import com.masroufi.api.dto.PermissionDto;
import com.masroufi.api.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto role);

    RoleDto updateRole(RoleDto role);

    RoleDto deleteRole(String uuid);

    List<RoleDto> getRoles();

    RoleDto getRoleDetails(String uuid);

    List<RoleDto> getAllRoles();

    List<PermissionDto> getAllPermissions();
}
