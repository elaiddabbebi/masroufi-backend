package com.masroufi.api.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class StatisticsResult {
    private Boolean translateLabels;
    private List<String> labels;
    private List<Double> data;
}
