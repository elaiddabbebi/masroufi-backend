package com.masroufi.api.repository;

import com.masroufi.api.entity.AggregatedCustomerCashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface AggregatedCustomerCashFlowRepository extends JpaRepository<AggregatedCustomerCashFlow, Long>, JpaSpecificationExecutor<AggregatedCustomerCashFlow> {

    AggregatedCustomerCashFlow findByUuid(String uuid);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndDate(Long customerId, Date date);

    @Query(
            "Select agg " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.date between :startDate and :endDate " +
            "Order by agg.date asc "
    )
    List<AggregatedCustomerCashFlow> findAllByCustomerIdAndDateBetween(Long customerId, Date startDate, Date endDate);

    @Query(
            "Select COALESCE(SUM(agg.expenseAmount), 0) " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.date between :startDate and :endDate"
    )
    Double calculateExpenseByCustomerBetween(Long customerId, Date startDate, Date endDate);

    @Query(
            "Select COALESCE(SUM(agg.gainAmount), 0) " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and agg.date between :startDate and :endDate"
    )
    Double calculateGainByCustomerBetween(Long customerId, Date startDate, Date endDate);

    @Query(
            "Select EXTRACT(month from agg.date) as month, SUM(agg.expenseAmount) as amount " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and EXTRACT(year from agg.date) = :year " +
            "group by month " +
            "order by EXTRACT(month from agg.date) ASC"
    )
    List<Map<String, Object>> calculateExpenseByCustomerAndMonthAndYear(Long customerId, int year);

    @Query(
            "Select EXTRACT(month from agg.date) as month, SUM(agg.gainAmount) as amount " +
            "From AggregatedCustomerCashFlow  agg " +
            "WHERE agg.customerId = :customerId " +
            "and EXTRACT(year from agg.date) = :year " +
            "group by month " +
            "order by EXTRACT(month from agg.date) ASC"
    )
    List<Map<String, Object>> calculateGainByCustomerAndMonthAndYear(Long customerId, int year);
}
