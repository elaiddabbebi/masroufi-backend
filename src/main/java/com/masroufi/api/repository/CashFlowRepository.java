package com.masroufi.api.repository;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, Long>, JpaSpecificationExecutor<CashFlow> {

    CashFlow findByUuid(String uuid);

    List<CashFlow> findAllByUser(Account user);
}
