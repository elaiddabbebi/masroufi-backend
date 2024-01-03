package com.masroufi.api.repository;

import com.masroufi.api.entity.CashFlowCategory;
import com.masroufi.api.enums.CashFlowCategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashFlowCategoryRepository extends JpaRepository<CashFlowCategory, Long>, JpaSpecificationExecutor<CashFlowCategory> {

    CashFlowCategory findByUuid(String uuid);

    CashFlowCategory findByNameIgnoreCase(String name);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndExpenseCategoryIsTrue(String name);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndGainCategoryIsTrue(String name);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndExpenseCategoryIsTrueAndStatus(String name, CashFlowCategoryStatus status);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndGainCategoryIsTrueAndStatus(String name, CashFlowCategoryStatus status);

    List<CashFlowCategory> findAllByNameLikeIgnoreCase(String name);
}
