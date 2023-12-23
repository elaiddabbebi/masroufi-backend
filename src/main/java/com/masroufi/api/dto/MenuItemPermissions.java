package com.masroufi.api.dto;

import com.masroufi.api.enums.PermissionType;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemPermissions {
    private String code;
    private List<PermissionType> permissions;
}
