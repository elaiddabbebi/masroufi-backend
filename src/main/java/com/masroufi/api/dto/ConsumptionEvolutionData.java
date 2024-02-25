package com.masroufi.api.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumptionEvolutionData {
    private List<String> daysOfMonth;
    private MonthConsumptionData currentMonthData;
    private MonthConsumptionData lastMonthData;
}
