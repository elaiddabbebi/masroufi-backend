package com.masroufi.api.repository;

import com.masroufi.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByUuid(String uuid);

    Role findByName(String name);
}
