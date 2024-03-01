package com.masroufi.api.repository;

import com.masroufi.api.entity.AggregatedCustomerCashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AggregatedCustomerCashFlowRepository extends JpaRepository<AggregatedCustomerCashFlow, Long>, JpaSpecificationExecutor<AggregatedCustomerCashFlow> {

    AggregatedCustomerCashFlow findByUuid(String uuid);

    @Query("Select agg " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.date between :startDate and :endDate " +
            "Order by agg.date asc ")
    List<AggregatedCustomerCashFlow> findAllByCustomerBetween(Long customerId, Date startDate, Date endDate);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndDate(Long customerId, Date date);

    @Query("Select COALESCE(SUM(agg.expenseAmount), 0) " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.date between :startDate and :endDate")
    Double calculateExpenseByCustomerBetween(Long customerId, Date startDate, Date endDate);

    @Query("Select COALESCE(SUM(agg.gainAmount), 0) " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.date between :startDate and :endDate")
    Double calculateGainByCustomerBetween(Long customerId, Date startDate, Date endDate);
}
