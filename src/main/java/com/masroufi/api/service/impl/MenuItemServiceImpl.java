package com.masroufi.api.service.impl;

import com.masroufi.api.dto.MenuItemDto;
import com.masroufi.api.dto.MenuItemPermissions;
import com.masroufi.api.entity.Permission;
import com.masroufi.api.entity.Role;
import com.masroufi.api.enums.PermissionType;
import com.masroufi.api.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private List<MenuItemDto> getApplicationMenuItems() {
        return Arrays.asList(
                MenuItemDto.builder().code("MANAGE_DASHBOARD").label("DASHBOARD").icon("pi pi-fw pi-home").routerLink("/main/dashboard").visible(false).build(),
                MenuItemDto.builder().code("MANAGE_SETTINGS").label("SETTINGS").icon("pi pi-fw pi-cog").visible(false).items(
                        Arrays.asList(
                                MenuItemDto.builder().code("MANAGE_PROFILE").label("MY_PROFILE").icon("pi pi-fw pi-user").routerLink("/main/settings/profile").visible(false).build(),
                                MenuItemDto.builder().code("MANAGE_USERS").label("USERS").icon("pi pi-fw pi-users").routerLink("/main/settings/users").visible(false).build(),
                                MenuItemDto.builder().code("MANAGE_ROLES").label("ROLES").icon("pi pi-fw pi-cog").routerLink("/main/settings/roles").visible(false).build(),
                                MenuItemDto.builder().code("MANAGE_LISTS").label("LISTS").icon("pi pi-fw pi-list").routerLink("/main/settings/lists").visible(false).build()
                        )
                ).build(),
                MenuItemDto.builder().code("CASH_REGISTER").label("CASH_REGISTER").icon("pi pi-fw pi-dollar").visible(false).items(
                        Arrays.asList(
                                MenuItemDto.builder().code("GAINS_EXPENSES").label("GAINS_EXPENSES").icon("pi pi-fw pi-list").routerLink("/main/expenses/search").visible(false).build(),
                                MenuItemDto.builder().code("STATISTICS").label("STATISTICS").icon("pi pi-fw pi-chart-line").routerLink("/main/expenses/stat").visible(false).build(),
                                MenuItemDto.builder().code("SIMULATOR").label("SIMULATOR").icon("pi pi-fw pi-calculator").routerLink("/main/expenses/simulator").visible(false).build()
                        )
                ).build()
        );
    }

    private List<MenuItemPermissions> getApplicationMenuItemPermissions() {
        return Arrays.asList(
                MenuItemPermissions.builder().code("MANAGE_DASHBOARD").permissions(
                        Arrays.asList(
                                PermissionType.VIEW_SUPER_ADMIN_DASHBOARD,
                                PermissionType.VIEW_ADMIN_DASHBOARD,
                                PermissionType.VIEW_CUSTOMER_DASHBOARD
                        )
                ).build(),
                MenuItemPermissions.builder().code("MANAGE_PROFILE").permissions(
                        Arrays.asList(PermissionType.MANGE_PROFILE)
                ).build(),
                MenuItemPermissions.builder().code("MANAGE_USERS").permissions(
                        Arrays.asList(PermissionType.MANAGE_USERS)
                ).build(),
                MenuItemPermissions.builder().code("MANAGE_ROLES").permissions(
                        Arrays.asList(PermissionType.MANGE_ROLES)
                ).build(),
                MenuItemPermissions.builder().code("MANAGE_LISTS").permissions(
                        Arrays.asList(PermissionType.MANAGE_SYSTEM_LISTS)
                ).build(),
                MenuItemPermissions.builder().code("GAINS_EXPENSES").permissions(
                        Arrays.asList(PermissionType.MANAGE_EXPENSES)
                ).build(),
                MenuItemPermissions.builder().code("STATISTICS").permissions(
                        Arrays.asList(PermissionType.MANAGE_EXPENSES)
                ).build(),
                MenuItemPermissions.builder().code("SIMULATOR").permissions(
                        Arrays.asList(PermissionType.MANAGE_EXPENSES)
                ).build()
        );
    }

    private void processMenuItemVisibility(MenuItemDto menuItemDto, List<PermissionType> permissionTypes) {
        if (menuItemDto.getItems() == null || menuItemDto.getItems().isEmpty()) {
            List<MenuItemPermissions> menuItemPermissions = this.getApplicationMenuItemPermissions();
            List<MenuItemPermissions> concernedMenuItemPermissions = menuItemPermissions.stream().filter(item -> item.getCode().equals(menuItemDto.getCode())).collect(Collectors.toList());
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
