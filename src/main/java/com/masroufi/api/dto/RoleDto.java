package com.masroufi.api.dto;

import com.masroufi.api.entity.Permission;
import com.masroufi.api.entity.Role;
import com.masroufi.api.enums.RoleType;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String uuid;
    private String name;
    private RoleType type;
    private List<PermissionDto> permissions;
    private Boolean isSystemRole;

    public static RoleDto buildFromRole(Role role) {
        if (role == null) {
            return null;
        }
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for (Permission permission: role.getPermissions()) {
            permissionDtos.add(PermissionDto.buildFromPermission(permission));
        }
        return RoleDto.builder()
                .uuid(role.getUuid())
                .name(role.getName())
                .type(role.getType())
                .permissions(permissionDtos)
                .isSystemRole(role.getIsSystemRole())
                .build();
    }
}
