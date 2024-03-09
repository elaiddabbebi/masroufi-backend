package com.masroufi.api.dto.response;

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
    private List<Double> data;
}
