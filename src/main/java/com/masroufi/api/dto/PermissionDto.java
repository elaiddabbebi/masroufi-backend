package com.masroufi.api.dto;

import com.masroufi.api.entity.Permission;
import com.masroufi.api.enums.PermissionType;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private String uuid;
    private PermissionType type;

    public static PermissionDto buildFromPermission(Permission permission) {
        return PermissionDto.builder()
                .uuid(permission.getUuid())
                .type(permission.getType())
                .build();
    }
}
