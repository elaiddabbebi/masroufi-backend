package com.masroufi.api.dto;

import com.masroufi.api.enums.Month;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthConsumptionData {
    private Month month;
    List<Double> data;
}
