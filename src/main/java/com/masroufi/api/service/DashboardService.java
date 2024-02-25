package com.masroufi.api.service;

import com.masroufi.api.dto.ConsumptionEvolutionData;

public interface DashboardService {

    Double getCurrentCashAmount();

    Double getCurrentWeekConsumption();

    Double getLastWeekConsumption();

    Double getLastMonthBalance();

    ConsumptionEvolutionData getConsumptionEvolutionData();

}
