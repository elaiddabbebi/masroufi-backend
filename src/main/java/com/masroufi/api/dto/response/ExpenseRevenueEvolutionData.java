package com.masroufi.api.dto.response;

import com.masroufi.api.enums.Month;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRevenueEvolutionData {
    private List<Month> months;
    private List<MonthAmount> revenueEvolution;
    private List<MonthAmount> expenseEvolution;
}
