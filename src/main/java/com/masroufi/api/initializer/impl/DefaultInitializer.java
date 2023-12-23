package com.masroufi.api.initializer.impl;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.Permission;
import com.masroufi.api.entity.Role;
import com.masroufi.api.enums.PermissionType;
import com.masroufi.api.enums.RoleType;
import com.masroufi.api.initializer.ApplicationInitializer;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.PermissionRepository;
import com.masroufi.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DefaultInitializer implements ApplicationInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void initApplication() {
        this.persistDefaultPermissions();
        this.persistDefaultRoles();
        this.persistDefaultSuperAdmin();
    }

    private void persistDefaultPermissions() {
        List<PermissionType> allPermissions = PermissionType.getAllPermissions();
        List<Permission> permissionsToSave = new ArrayList<>();
        for (PermissionType permissionType: allPermissions) {
            Permission savedPermission = this.permissionRepository.findByType(permissionType);
            if (savedPermission == null) {
                Permission permission = Permission.builder()
                        .type(permissionType)
                        .build();
                permissionsToSave.add(permission);
            }
        }
        if (!permissionsToSave.isEmpty()) {
            this.permissionRepository.saveAll(permissionsToSave);
        }
    }

    private void persistDefaultRoles() {
        List<Role> rolesToSave = new ArrayList<>();
        Role superAdminRole = this.roleRepository.findByName("Admin application");
        if (superAdminRole == null) {
            List<Permission> superAdminPermissions = this.permissionRepository.findAllByTypeIn(PermissionType.getSuperAdminPermissions());
            Role role = Role.builder()
                    .name("Admin application")
                    .isSystemRole(true)
                    .type(RoleType.SUPER_ADMIN)
                    .permissions(superAdminPermissions)
                    .build();
            rolesToSave.add(role);
        }

        Role adminRole = this.roleRepository.findByName("Admin");
        if (adminRole == null) {
            List<Permission> adminPermissions = this.permissionRepository.findAllByTypeIn(PermissionType.getAdminPermissions());
            Role role = Role.builder()
                    .name("Admin")
                    .isSystemRole(true)
                    .type(RoleType.ADMIN)
                    .permissions(adminPermissions)
                    .build();
            rolesToSave.add(role);
        }

        Role customerRole = this.roleRepository.findByName("Client");
        if (customerRole == null) {
            List<Permission> CustomerPermissions = this.permissionRepository.findAllByTypeIn(PermissionType.getCustomerPermissions());
            Role role = Role.builder()
                    .name("Client")
                    .isSystemRole(true)
                    .type(RoleType.CUSTOMER)
                    .permissions(CustomerPermissions)
                    .build();
            rolesToSave.add(role);
        }
        if (!rolesToSave.isEmpty()) {
            this.roleRepository.saveAll(rolesToSave);
        }
    }

    private void persistDefaultSuperAdmin() {
        Account account = this.accountRepository.findByEmailIgnoreCase("super.admin@gmail.com");
        if (account == null) {
            Role superAdminRole = this.roleRepository.findByName("Admin application");
            Account superAdmin = Account.builder()
                    .email("super.admin@gmail.com")
                    .phoneNumber("216 55 111 000")
                    .birthDate(new Date())
                    .role(superAdminRole)
                    .firstName("Super")
                    .lastName("Admin")
                    .password(this.bCryptPasswordEncoder.encode("test"))
                    .isActivated(true)
                    .build();
            this.accountRepository.save(superAdmin);
        }
    }
}
