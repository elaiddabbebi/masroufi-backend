package com.masroufi.api.repository;

import com.masroufi.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUuid(String uuid);

    Account findByEmailIgnoreCase(String email);

}
