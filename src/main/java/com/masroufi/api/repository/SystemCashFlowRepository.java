package com.masroufi.api.repository;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.entity.CashFlowCategory;
import com.masroufi.api.entity.SystemCashFlow;
import com.masroufi.api.enums.SystemCashFlowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemCashFlowRepository extends JpaRepository<SystemCashFlow, Long>, JpaSpecificationExecutor<SystemCashFlow> {

    SystemCashFlow findByUuid(String uuid);

    List<SystemCashFlow> findAllByNameIgnoreCase(String name);

    List<SystemCashFlow> findAllByNameLikeIgnoreCase(String name);

    List<SystemCashFlow> findAllByNameLikeIgnoreCaseAndCategoryAndStatus(String name, CashFlowCategory category, SystemCashFlowStatus status);
}
