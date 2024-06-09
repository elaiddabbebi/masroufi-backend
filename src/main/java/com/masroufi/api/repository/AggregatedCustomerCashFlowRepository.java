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

    List<AggregatedCustomerCashFlow> findAllByCustomerIdAndDateBetweenOrderByDate(Long customerId, Date startDate, Date endDate);
}
