package com.masroufi.api.repository;

import com.masroufi.api.entity.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, Long>, JpaSpecificationExecutor<CashFlow> {

    CashFlow findByUuid(String uuid);

    List<CashFlow> findAllByNameIgnoreCase(String name);

    List<CashFlow> findAllByIsDeletedIsFalseOrIsDeletedIsNullOrderByIdDesc();

    @Query("Select distinct(c.name) " +
            "From CashFlow c " +
            "where (c.isDeleted is false or c.isDeleted is null) " +
            "and c.status <> 'REJECTED'")
    List<String> findAllCashFlowNamesForSupperAdmin();

    @Query("Select distinct(c.name) " +
            "From CashFlow c " +
            "where (c.isDeleted is false or c.isDeleted is null) " +
            "and c.status <> 'REJECTED' " +
            "and (c.createdBy = :customerId or c.published is true) " +
            "order by c.name")
    List<String> findAllCashFlowNamesByCustomer(Long customerId);

}
