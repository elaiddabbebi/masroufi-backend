package com.masroufi.api.service;

import com.masroufi.api.dto.ConsumptionEvolutionData;
import com.masroufi.api.dto.MonthAmount;

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

}
