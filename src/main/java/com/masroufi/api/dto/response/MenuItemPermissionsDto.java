package com.masroufi.api.dto.response;

import com.masroufi.api.enums.PermissionType;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemPermissionsDto {
    private String code;
    private List<PermissionType> permissions;
}
