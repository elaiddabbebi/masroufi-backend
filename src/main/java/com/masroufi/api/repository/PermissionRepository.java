package com.masroufi.api.repository;

import com.masroufi.api.entity.Permission;
import com.masroufi.api.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    Permission findByUuid(String uuid);

    Permission findByType(PermissionType type);

    List<Permission> findAllByTypeIn(List<PermissionType> permissionTypes);
}
