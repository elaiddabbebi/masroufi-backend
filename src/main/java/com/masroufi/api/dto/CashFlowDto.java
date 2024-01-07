package com.masroufi.api.dto;

import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.enums.CashFlowStatus;
import com.masroufi.api.enums.CashFlowType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlowDto {

    private String uuid;
    private String name;
    private CashFlowType type;
    private CashFlowCategoryDto category;
    private CashFlowStatus status;
    private boolean systemCashFlow;

    public static CashFlowDto buildFromCashFlow(CashFlow cashFlow) {
        if (cashFlow == null) {
            return null;
        }
        return CashFlowDto.builder()
                .uuid(cashFlow.getUuid())
                .name(cashFlow.getName())
                .type(cashFlow.getType())
                .systemCashFlow(cashFlow.isSystemCashFlow())
                .category(CashFlowCategoryDto.buildFromCashFlowCategory(cashFlow.getCategory()))
                .status(cashFlow.getStatus())
                .build();
    }
}
