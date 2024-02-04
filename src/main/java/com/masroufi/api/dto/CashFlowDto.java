package com.masroufi.api.dto;

import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.enums.CashFlowStatus;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlowDto {

    private String uuid;
    private String name;
    private boolean gain;
    private boolean expense;
    private CashFlowCategoryDto category;
    private CashFlowStatus status;
    private boolean published;
    private String createdBy;

    public static CashFlowDto buildFromCashFlow(CashFlow cashFlow) {
        if (cashFlow == null) {
            return null;
        }
        return CashFlowDto.builder()
                .uuid(cashFlow.getUuid())
                .name(cashFlow.getName())
                .gain(cashFlow.isGain())
                .expense(cashFlow.isExpense())
                .published(cashFlow.isPublished())
                .category(CashFlowCategoryDto.buildFromCashFlowCategory(cashFlow.getCategory()))
                .status(cashFlow.getStatus())
                .build();
    }
}
