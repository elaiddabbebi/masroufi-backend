package com.masroufi.api.service.impl;

import com.masroufi.api.dto.PermissionDto;
import com.masroufi.api.dto.RoleDto;
import com.masroufi.api.entity.Permission;
import com.masroufi.api.entity.Role;
import com.masroufi.api.repository.PermissionRepository;
import com.masroufi.api.repository.RoleRepository;
import com.masroufi.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public RoleDto createRole(RoleDto role) {
        Role newRole = new Role();
        newRole.setIsSystemRole(false);
        newRole.setName(role.getName());
        newRole.setType(role.getType());
        List<Permission> permissions = new ArrayList<>();
        if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
            for (PermissionDto permissionDto: role.getPermissions()) {
                if (permissionDto != null && permissionDto.getUuid() != null) {
                    Permission permission = this.permissionRepository.findByUuid(permissionDto.getUuid());
                    if (permission != null) {
                        permissions.add(permission);
                    }
                }
            }
        }
        newRole.setPermissions(permissions);
        this.roleRepository.save(newRole);
        return RoleDto.buildFromRole(newRole);
    }

    @Override
    public RoleDto updateRole(RoleDto role) {
        if (role != null && role.getUuid() != null) {
            Role roleToUpdate = this.roleRepository.findByUuid(role.getUuid());
            if (roleToUpdate != null) {
                roleToUpdate.setName(role.getName());
                roleToUpdate.setType(role.getType());
                List<Permission> permissions = new ArrayList<>();
                if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                    for (PermissionDto permissionDto: role.getPermissions()) {
                        if (permissionDto != null && permissionDto.getUuid() != null) {
                            Permission permission = this.permissionRepository.findByUuid(permissionDto.getUuid());
                            if (permission != null) {
                                permissions.add(permission);
                            }
                        }
                    }
                }
                roleToUpdate.setPermissions(permissions);
                this.roleRepository.save(roleToUpdate);
                return RoleDto.buildFromRole(roleToUpdate);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public RoleDto deleteRole(String uuid) {
        Role roleToDelete = this.roleRepository.findByUuid(uuid);
        if (roleToDelete != null) {
            RoleDto returnValue = RoleDto.buildFromRole(roleToDelete);
            this.roleRepository.delete(roleToDelete);
            return returnValue;
        }
        return null;
    }

    @Override
    public RoleDto getRoleDetails(String uuid) {
        Role role = this.roleRepository.findByUuid(uuid);
        if (role != null) {
            return RoleDto.buildFromRole(role);
        }
        return null;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = this.roleRepository.findAll();
        List<RoleDto> returnValue = new ArrayList<>();
        for (Role role: roles) {
            returnValue.add(RoleDto.buildFromRole(role));
        }
        return returnValue;
    }

    @Override
    public List<PermissionDto> getAllPermissions() {
        List<Permission> allPermissions = this.permissionRepository.findAll();
        List<PermissionDto> returnValue = new ArrayList<>();
        for (Permission permission: allPermissions) {
            returnValue.add(PermissionDto.buildFromPermission(permission));
        }
        return returnValue;
    }
}
