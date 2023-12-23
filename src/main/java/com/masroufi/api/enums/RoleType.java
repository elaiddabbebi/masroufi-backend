package com.masroufi.api.enums;

import java.util.Arrays;
import java.util.List;

public enum RoleType {
    SUPER_ADMIN,
    ADMIN,
    CUSTOMER;

    public static List<RoleType> getAllRoleTypes() {
        return Arrays.asList(
                SUPER_ADMIN,
                ADMIN,
                CUSTOMER
        );
    }
}
