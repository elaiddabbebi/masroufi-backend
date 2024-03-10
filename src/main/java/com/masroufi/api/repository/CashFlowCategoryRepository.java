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

    @Query("Select distinct(c.name) " +
            "From CashFlowCategory c " +
            "where (c.isDeleted is false or c.isDeleted is null) " +
            "and c.status <> 'REJECTED'")
    List<String> findAllCategoryNamesForSupperAdmin();

    @Query("Select distinct(c.name) " +
            "From CashFlowCategory c " +
            "where (c.isDeleted is false or c.isDeleted is null) " +
            "and c.status = 'VALIDATED' " +
            "and (c.createdBy = :customerId or c.published is true) " +
            "order by c.name")
    List<String> findAllCategoryNamesByCustomer(Long customerId);
}
