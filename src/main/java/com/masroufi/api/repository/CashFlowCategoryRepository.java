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

    List<CashFlowCategory> findAllByNameEqualsIgnoreCaseAndIsDeletedIsFalse(String name);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCase(String name);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndIsDeletedIsFalse(String name);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndExpenseIsTrue(String name);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndGainIsTrue(String name);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndStatusAndIsDeletedIsFalse(String name, CashFlowCategoryStatus status);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndExpenseIsTrueAndStatusAndIsDeletedIsFalse(String name, CashFlowCategoryStatus status);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndGainIsTrueAndStatusAndIsDeletedIsFalse(String name, CashFlowCategoryStatus status);
}
