package com.masroufi.api.dto.response;

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
