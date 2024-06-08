package com.masroufi.api.repository;

import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.enums.CashFlowType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CustomerCashFlowRegistryRepository extends JpaRepository<CustomerCashFlowRegistry, Long>, JpaSpecificationExecutor<CustomerCashFlowRegistry> {

    CustomerCashFlowRegistry findByUuid(String uuid);

    @Query(
            "Select cashFlowCategory.name as name, SUM(registry.amount) as amount " +
                    "From CashFlow cashFlow " +
                    "Left join CustomerCashFlowRegistry registry ON cashFlow = registry.cashFlow " +
                    "Left join Account account ON account = registry.customer " +
                    "Left join CashFlowCategory cashFlowCategory ON cashFlowCategory = cashFlow.category " +
                    "Where account.id = :customerId " +
                    "And registry.type = :cashFlowType " +
                    "And (registry.date >= :startDate And registry.date <= :endDate) " +
                    "And amount > 0 " +
                    "Group by cashFlowCategory.name " +
                    "Order by amount DESC "
    )
    List<Map<String, Object>> getCashFlowByCustomerBetween(
            Long customerId,
            CashFlowType cashFlowType,
            Date startDate,
            Date endDate,
            Pageable pageable
    );

    @Query(
            "Select cashFlow.name as name, SUM(registry.amount) as amount " +
                    "From CashFlow cashFlow " +
                    "Left join CustomerCashFlowRegistry registry ON cashFlow = registry.cashFlow " +
                    "Left join Account account ON account = registry.customer " +
                    "Left join CashFlowCategory cashFlowCategory ON cashFlowCategory = cashFlow.category " +
                    "Where account.id = :customerId " +
                    "And registry.type = :cashFlowType " +
                    "And cashFlowCategory.uuid = :categoryUuid " +
                    "And (registry.date >= :startDate And registry.date <= :endDate) " +
                    "And amount > 0 " +
                    "Group by cashFlow.name " +
                    "Order by amount DESC "
    )
    List<Map<String, Object>> getCashFlowByCategoryAndCustomerBetween(
            Long customerId,
            CashFlowType cashFlowType,
            String categoryUuid,
            Date startDate,
            Date endDate,
            Pageable pageable
    );

    @Query(
            "Select DISTINCT (cashFlowCategory.name) as key, cashFlowCategory.uuid as value " +
                    "From CashFlow cashFlow " +
                    "Left join CustomerCashFlowRegistry registry ON cashFlow = registry.cashFlow " +
                    "Left join Account account ON account = registry.customer " +
                    "Left join CashFlowCategory cashFlowCategory ON cashFlowCategory = cashFlow.category " +
                    "Where account.id = :customerId " +
                    "And registry.type = :cashFlowType " +
                    "Order by cashFlowCategory.name ASC "
    )
    List<Map<String, Object>> getCustomerCategories(Long customerId, CashFlowType cashFlowType);

    @Query("Select distinct (extract(year from cashFlow.createdAt))" +
            "From AggregatedCustomerCashFlow cashFlow " +
            "Where cashFlow.customerId = :customerId ")
    List<Integer> getYearsListByCustomer(Long customerId);

}
