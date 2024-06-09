package com.masroufi.api.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsItem {
    private String label;
    private Double amount;
}
