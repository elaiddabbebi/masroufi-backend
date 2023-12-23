package com.masroufi.api.enums;

import java.util.Arrays;
import java.util.List;

public enum PermissionType {

    MANAGE_USERS,
    VIEW_SUPER_ADMIN_DASHBOARD,
    VIEW_ADMIN_DASHBOARD,
    VIEW_CUSTOMER_DASHBOARD,
    MANGER_ROLES,
    MANAGE_EXPENSES,
    MANAGE_SYSTEM_LISTS
    ;

    public static List<PermissionType> getAllPermissions() {
        return Arrays.asList(
                MANAGE_USERS,
                VIEW_ADMIN_DASHBOARD,
                VIEW_CUSTOMER_DASHBOARD,
                MANAGE_EXPENSES,
                VIEW_SUPER_ADMIN_DASHBOARD,
                MANAGE_SYSTEM_LISTS,
                MANGER_ROLES
        );
    }

    public static List<PermissionType> getSuperAdminPermissions() {
        return Arrays.asList(
                MANAGE_USERS,
                VIEW_SUPER_ADMIN_DASHBOARD,
                MANGER_ROLES,
                MANAGE_SYSTEM_LISTS
        );
    }

    public static List<PermissionType> getAdminPermissions() {
        return Arrays.asList(
                MANAGE_USERS,
                VIEW_ADMIN_DASHBOARD
        );
    }

    public static List<PermissionType> getCustomerPermissions() {
        return Arrays.asList(
                VIEW_CUSTOMER_DASHBOARD,
                MANAGE_EXPENSES
        );
    }
}
