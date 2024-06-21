package com.masroufi.api.repository;

import com.masroufi.api.entity.CashFlowCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashFlowCategoryRepository extends JpaRepository<CashFlowCategory, Long>, JpaSpecificationExecutor<CashFlowCategory> {

    CashFlowCategory findByUuid(String uuid);

    List<CashFlowCategory> findAllByIsDeletedIsFalseOrIsDeletedIsNullOrderByIdDesc();

    List<CashFlowCategory> findAllByNameEqualsIgnoreCaseAndIsDeletedIsFalse(String name);

    List<CashFlowCategory> findTop10ByNameLikeIgnoreCaseAndIsDeletedIsFalse(String name);

    @Query(
            value = "Select distinct(c.name) " +
            "From cash_flow_category c " +
            "where (c.is_deleted is false or c.is_deleted is null) " +
            "and c.status <> 'REJECTED'",
            nativeQuery = true
    )
    List<String> findAllCategoryNamesForSupperAdmin();

    @Query(
            value = "Select distinct(c.name) " +
            "From cash_flow_category c " +
            "where (c.is_deleted is false or c.is_deleted is null) " +
            "and c.status = 'VALIDATED' " +
            "and (c.created_by = :customerId or c.published is true) " +
            "order by c.name",
            nativeQuery = true
    )
    List<String> findAllCategoryNamesByCustomer(Long customerId);
}
