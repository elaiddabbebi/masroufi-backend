package com.masroufi.api.repository;

import com.masroufi.api.entity.AggregatedCustomerCashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatedCustomerCashFlowRepository extends JpaRepository<AggregatedCustomerCashFlow, Long>, JpaSpecificationExecutor<AggregatedCustomerCashFlow> {

    AggregatedCustomerCashFlow findByUuid(String uuid);

    List<AggregatedCustomerCashFlow> findByCustomerId(Long customerId);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndYear(Long customerId, int year);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndYearAndMonth(Long customerId, int year, int month);

    List<AggregatedCustomerCashFlow> findByCustomerIdAndYearAndMonthAndDay(Long customerId, int year, int month, int day);
}
