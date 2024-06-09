package com.masroufi.api.repository;

import com.masroufi.api.dto.StatisticsItem;
import com.masroufi.api.dto.response.GenericObject;
import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.enums.CashFlowType;

import java.util.Date;
import java.util.List;

public interface StatisticsRepository {

    List<GenericObject> getCategoriesByCustomerAndCashFlowType(Long customerId, CashFlowType cashFlowType);

    List<Integer> getYearsListByCustomer(Long customerId);

    List<StatisticsItem> getCashFlowByCategoryAndTypeAndCustomerAndDateBetween(
            Long customerId,
            CashFlowType cashFlowType,
            String categoryUuid,
            Date startDate,
            Date endDate,
            int limit
    );

    List<StatisticsItem> getCashFlowByCustomerAndTypeAndDateBetween(
            Long customerId,
            CashFlowType cashFlowType,
            Date startDate,
            Date endDate,
            int limit
    );

    List<MonthAmount> getExpensesPerMonthByCustomerAndYear(Long customerId, int year);

    List<MonthAmount> getRevenuesPerMonthByCustomerAndYear(Long customerId, int year);

    Double getBalanceByCustomerAndDateBetween(Long customerId, Date startDate, Date endDate);

    Double getExpenseByCustomerAndDateBetween(Long customerId, Date startDate, Date endDate);

    Double getRevenueByCustomerAndDateBetween(Long customerId, Date startDate, Date endDate);
}
