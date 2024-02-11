package com.masroufi.api.dto;

import com.masroufi.api.entity.embeddable.CashFlowConfig;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlowConfigDto {
    private Double initialCashAmount;

    public static CashFlowConfigDto buildFromCashFlowConfig(CashFlowConfig config) {
        if (config != null) {
            return CashFlowConfigDto.builder()
                    .initialCashAmount(config.getInitialCashAmount())
                    .build();
        } else {
            return null;
        }
    }
}
