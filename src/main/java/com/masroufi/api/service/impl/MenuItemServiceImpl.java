package com.masroufi.api.service.impl;

import com.masroufi.api.dto.response.MenuItemDto;
import com.masroufi.api.dto.response.MenuItemPermissionsDto;
import com.masroufi.api.entity.Permission;
import com.masroufi.api.entity.Role;
import com.masroufi.api.enums.PermissionType;
import com.masroufi.api.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private List<MenuItemDto> getApplicationMenuItems() {
        return Arrays.asList(
                MenuItemDto.builder().code("MANAGE_DASHBOARD").label("DASHBOARD").icon("pi pi-fw pi-home").routerLink("/main/dashboard").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_USERS").label("USERS").icon("pi pi-fw pi-users").routerLink("/main/users").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_ROLES").label("ROLES_AND_PERMISSIONS").icon("pi pi-fw pi-sitemap").routerLink("/main/roles").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_CASH_FLOW").label("CASH").icon("pi pi-fw pi-database").routerLink("/main/cash-flow").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_CASH_FLOW_REGISTRY").label("CASH_FLOW_REGISTRY").icon("pi pi-fw pi-wallet").routerLink("/main/cash-flow-registry").visible(false).build(),
                MenuItemDto.builder().code("STATISTICS").label("STATISTICS").icon("pi pi-fw pi-chart-bar").routerLink("/main/statistics").visible(false).build(),
                MenuItemDto.builder().code("KPI").label("KPI").icon("pi pi-fw pi-chart-line").routerLink("/main/kpi").visible(false).build(),
                MenuItemDto.builder().code("SIMULATOR").label("SIMULATOR").icon("pi pi-fw pi-calculator").routerLink("/main/simulator").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_PROFILE").label("MY_PROFILE").icon("pi pi-fw pi-user").routerLink("/main/profile").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_CONFIG").label("CONFIGURATION").icon("pi pi-fw pi-cog").routerLink("/main/configuration").visible(false).build(),
                MenuItemDto.builder().code("VIEW_ABOUT").label("ABOUT").icon("pi pi-fw pi-info-circle").routerLink("/main/about").visible(false).build()
        );
    }

    private List<MenuItemPermissionsDto> getApplicationMenuItemPermissions() {
        return Arrays.asList(
                MenuItemPermissionsDto.builder().code("MANAGE_DASHBOARD").permissions(
                        Arrays.asList(
                                PermissionType.VIEW_SUPER_ADMIN_DASHBOARD,
                                PermissionType.VIEW_ADMIN_DASHBOARD,
                                PermissionType.VIEW_CUSTOMER_DASHBOARD
                        )
                ).build(),
                MenuItemPermissionsDto.builder().code("MANAGE_PROFILE").permissions(
                        Collections.singletonList(PermissionType.MANGE_PROFILE)
                ).build(),
                MenuItemPermissionsDto.builder().code("MANAGE_CONFIG").permissions(
                        Collections.singletonList(PermissionType.MANAGE_CONFIG)
                ).build(),
                MenuItemPermissionsDto.builder().code("MANAGE_USERS").permissions(
                        Collections.singletonList(PermissionType.MANAGE_USERS)
                ).build(),
                MenuItemPermissionsDto.builder().code("MANAGE_ROLES").permissions(
                        Collections.singletonList(PermissionType.MANGE_ROLES)
                ).build(),
                MenuItemPermissionsDto.builder().code("MANAGE_CASH_FLOW").permissions(
                        Collections.singletonList(PermissionType.MANAGE_SYSTEM_LISTS)
                ).build(),
                MenuItemPermissionsDto.builder().code("MANAGE_CASH_FLOW_REGISTRY").permissions(
                        Collections.singletonList(PermissionType.MANAGE_EXPENSES)
                ).build(),
                MenuItemPermissionsDto.builder().code("STATISTICS").permissions(
                        Collections.singletonList(PermissionType.MANAGE_EXPENSES)
                ).build(),
                MenuItemPermissionsDto.builder().code("KPI").permissions(
                        Collections.singletonList(PermissionType.MANAGE_EXPENSES)
                ).build(),
                MenuItemPermissionsDto.builder().code("SIMULATOR").permissions(
                        Collections.singletonList(PermissionType.MANAGE_EXPENSES)
                ).build(),
                MenuItemPermissionsDto.builder().code("VIEW_ABOUT").permissions(
                        Arrays.asList(
                                PermissionType.VIEW_SUPER_ADMIN_DASHBOARD,
                                PermissionType.VIEW_ADMIN_DASHBOARD,
                                PermissionType.VIEW_CUSTOMER_DASHBOARD
                        )
                ).build()
        );
    }

    private void processMenuItemVisibility(MenuItemDto menuItemDto, List<PermissionType> permissionTypes) {
        if (menuItemDto.getItems() == null || menuItemDto.getItems().isEmpty()) {
            List<MenuItemPermissionsDto> menuItemPermissions = this.getApplicationMenuItemPermissions();
            List<MenuItemPermissionsDto> concernedMenuItemPermissions = menuItemPermissions.stream().filter(item -> item.getCode().equals(menuItemDto.getCode())).collect(Collectors.toList());
            if (!concernedMenuItemPermissions.isEmpty()) {
                for (PermissionType permissionType: permissionTypes) {
                    if (concernedMenuItemPermissions.get(0).getPermissions().contains(permissionType)) {
                        menuItemDto.setVisible(true);
                        break;
                    }
                }
            }
        } else {
            List<MenuItemDto> items = menuItemDto.getItems();
            for (MenuItemDto menuItem: items) {
                this.processMenuItemVisibility(menuItem, permissionTypes);
                if (menuItem.isVisible()) {
                    menuItemDto.setVisible(true);
                }
            }
        }
    }

    @Override
    public List<MenuItemDto> getRoleMenuItems(Role role) {
        List<PermissionType> permissionTypes = role.getPermissions().stream().map(Permission::getType).collect(Collectors.toList());
        List<MenuItemDto> menuItemList = this.getApplicationMenuItems();
        for (MenuItemDto menuItemDto: menuItemList) {
            this.processMenuItemVisibility(menuItemDto, permissionTypes);
        }
        return menuItemList;
    }
}
