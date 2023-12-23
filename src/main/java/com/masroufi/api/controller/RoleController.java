package com.masroufi.api.controller;

import com.masroufi.api.dto.PermissionDto;
import com.masroufi.api.dto.RoleDto;
import com.masroufi.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/get-all")
    List<RoleDto> getAllRoles() {
        return this.roleService.getAllRoles();
    }

    @GetMapping("/get-all-permissions")
    List<PermissionDto> getAllPermissions() {
        return this.roleService.getAllPermissions();
    }

    @GetMapping("/{uuid}")
    RoleDto getRoleDetails(@PathVariable String uuid) {
        return this.roleService.getRoleDetails(uuid);
    }

    @PutMapping
    RoleDto updateRole(@RequestBody RoleDto roleDto) {
        return this.roleService.updateRole(roleDto);
    }

    @PostMapping
    RoleDto createRole(@RequestBody RoleDto roleDto) {
        return this.roleService.createRole(roleDto);
    }

    @DeleteMapping("/{uuid}")
    RoleDto deleteRole(@PathVariable String uuid) {
        return this.roleService.deleteRole(uuid);
    }
}
