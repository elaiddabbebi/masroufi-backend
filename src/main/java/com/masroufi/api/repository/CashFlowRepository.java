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

    @Query(
            value = "Select distinct(c.name) " +
            "From cash_flow c " +
            "where (c.is_deleted is false or c.is_deleted is null) " +
            "and c.status <> 'REJECTED'",
            nativeQuery = true
    )
    List<String> findAllCashFlowNamesForSupperAdmin();

    @Query(
            value = "Select distinct(c.name) " +
            "From cash_flow c " +
            "Left join cash_flow_category ca on c.category_id = ca.id " +
            "where (c.is_deleted is false or c.is_deleted is null) " +
            "and c.status <> 'REJECTED' " +
            "and upper(ca.name) = upper(:category) ",
            nativeQuery = true
    )
    List<String> findAllByCategoryForSupperAdmin(String category);

    @Query(
            value = "Select distinct(c.name) " +
            "From cash_flow c " +
            "where (c.is_deleted is false or c.is_deleted is null) " +
            "and c.status = 'VALIDATED' " +
            "and (c.created_by = :customerId or c.published is true) " +
            "order by c.name",
            nativeQuery = true
    )
    List<String> findAllCashFlowNamesByCustomer(Long customerId);

    @Query(
            value = "Select distinct(c.name) " +
            "From cash_flow c " +
            "Left join cash_flow_category ca on c.category_id = ca.id " +
            "where (c.is_deleted is false or c.is_deleted is null) " +
            "and c.status = 'VALIDATED' " +
            "and (c.created_by = :customerId or c.published is true) " +
            "and upper(ca.name) = upper(:category) " +
            "order by c.name",
            nativeQuery = true
    )
    List<String> findAllByCustomerAndCategory(Long customerId, String category);

}
