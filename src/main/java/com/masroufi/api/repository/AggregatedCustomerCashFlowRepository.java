package com.masroufi.api.repository;

import com.masroufi.api.entity.AggregatedCustomerCashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatedCustomerCashFlowRepository extends JpaRepository<AggregatedCustomerCashFlow, Long>, JpaSpecificationExecutor<AggregatedCustomerCashFlow> {

    AggregatedCustomerCashFlow findByUuid(String uuid);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndYearAndMonth(Long customerId, int year, int month);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndYearAndMonthAndDay(Long customerId, int year, int month, int day);

    @Query("Select COALESCE(SUM(agg.expenseAmount), 0) " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.year >= :startYear " +
            "and agg.month >= :startMonth " +
            "and agg.day >= :startDay " +
            "and agg.year <= :endYear " +
            "and agg.month <= :endMonth " +
            "and agg.day <= :endDay ")
    Double calculateExpenseByCustomerBetween(Long customerId, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay);

    @Query("Select COALESCE(SUM(agg.gainAmount), 0) " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.year >= :startYear " +
            "and agg.month >= :startMonth " +
            "and agg.day >= :startDay " +
            "and agg.year <= :endYear " +
            "and agg.month <= :endMonth " +
            "and agg.day <= :endDay")
    Double calculateGainByCustomerBetween(Long customerId, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay);
}
