package com.masroufi.api.service;

import com.masroufi.api.dto.response.ConsumptionEvolutionData;
import com.masroufi.api.dto.response.ExpenseRevenueEvolutionData;
import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.enums.CashFlowType;

import java.util.List;

public interface DashboardService {

    Double getCurrentCashAmount();

    Double getCurrentWeekConsumption();

    Double getLastWeekConsumption();

    Double getCurrentWeekBalance();

    Double getLastWeekBalance();

    Double getCurrentMonthConsumption();

    Double getLastMonthConsumption();

    Double getCurrentMonthBalance();

    Double getLastMonthBalance();

    Double getCurrentYearRevenue();

    Double getCurrentYearBalance();

    ConsumptionEvolutionData getConsumptionEvolutionData();

    List<MonthAmount> getCurrentYearRevenueEvolution();

    List<MonthAmount> getCurrentYearExpenseEvolution();

    ExpenseRevenueEvolutionData getCurrentYearExpenseRevenueEvolution();

    List<MonthAmount> calculateFlowByCustomerAndYear(Long customerId, int year, CashFlowType cashFlowType);

}
