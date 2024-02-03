package com.masroufi.api.shared.context;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.Permission;
import com.masroufi.api.entity.Role;
import com.masroufi.api.enums.PermissionType;
import com.masroufi.api.enums.RoleType;
import com.masroufi.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationSecurityContext {

    @Autowired
    private AccountRepository accountRepository;

    public String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public Account getCurrentUser() {
        String email = this.getUserEmail();
        if (email != null) {
            return this.accountRepository.findByEmailIgnoreCase(email);
        }
        return null;
    }

    public Role getCurrentUserRole() {
        Account currentUser = this.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getRole();
        }
        return null;
    }

    public boolean isSupperAdmin() {
        return this.isRoleType(RoleType.SUPER_ADMIN);
    }

    public boolean isCustomer() {
        return this.isRoleType(RoleType.CUSTOMER);
    }

    public boolean isCustomerAdmin() {
        return this.isRoleType(RoleType.ADMIN);
    }

    private boolean isRoleType(RoleType roleType) {
        Role role = getCurrentUserRole();
        if (role == null) {
            return false;
        } else {
            return role.getType() != null && role.getType().equals(roleType);
        }
    }

    public void isSupperAdminOrThrowException() {
        this.isRoleTypeOrThrowException(RoleType.SUPER_ADMIN);
    }

    public void isCustomerAdminOrThrowException() {
        this.isRoleTypeOrThrowException(RoleType.ADMIN);
    }

    public void isCustomerOrThrowException() {
        this.isRoleTypeOrThrowException(RoleType.CUSTOMER);
    }

    private void isRoleTypeOrThrowException(RoleType roleType) {
        if (!this.isRoleType(roleType)) {
            throw new RuntimeException("FORBIDDEN");
        }
    }

    public boolean hasPermission(PermissionType permissionType) {
        Account currentUser = this.getCurrentUser();
        if (currentUser != null) {
            Role userRole = currentUser.getRole();
            if (userRole != null) {
                List<Permission> permissions = userRole.getPermissions();
                for (Permission permission: permissions) {
                    if (permission != null && permissionType.equals(permission.getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
