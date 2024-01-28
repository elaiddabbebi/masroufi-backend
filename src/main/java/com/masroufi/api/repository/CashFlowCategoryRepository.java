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

    List<CashFlowCategory> findAllByIsDeletedIsFalseOrIsDeletedIsNullOrderByIdDesc();

    CashFlowCategory findByNameIgnoreCase(String name);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndExpenseIsTrue(String name);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndGainIsTrue(String name);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndExpenseIsTrueAndStatus(String name, CashFlowCategoryStatus status);

    List<CashFlowCategory> findAllByNameLikeIgnoreCaseAndGainIsTrueAndStatus(String name, CashFlowCategoryStatus status);

    List<CashFlowCategory> findAllByNameLikeIgnoreCase(String name);
}
