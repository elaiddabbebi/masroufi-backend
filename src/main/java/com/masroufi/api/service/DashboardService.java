package com.masroufi.api.service;

import com.masroufi.api.dto.ConsumptionEvolutionData;

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

}
