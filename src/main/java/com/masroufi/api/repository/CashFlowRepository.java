package com.masroufi.api.repository;

import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.entity.CashFlowCategory;
import com.masroufi.api.enums.SystemCashFlowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, Long>, JpaSpecificationExecutor<CashFlow> {

    CashFlow findByUuid(String uuid);

    List<CashFlow> findAllByNameIgnoreCase(String name);

    List<CashFlow> findAllByNameLikeIgnoreCase(String name);

    List<CashFlow> findAllByNameLikeIgnoreCaseAndCategoryAndStatus(String name, CashFlowCategory category, SystemCashFlowStatus status);
}
