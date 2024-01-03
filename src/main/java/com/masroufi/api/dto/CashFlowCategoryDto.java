package com.masroufi.api.dto;

import com.masroufi.api.entity.CashFlowCategory;
import com.masroufi.api.enums.CashFlowCategoryStatus;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlowCategoryDto {

    private String uuid;
    private String name;
    private CashFlowCategoryStatus status;
    private boolean systemCategory;
    private boolean expenseCategory;
    private boolean gainCategory;

    public static CashFlowCategoryDto buildFromCashFlowCategory(CashFlowCategory cashFlowCategory) {
        if (cashFlowCategory == null) {
            return null;
        }
        return CashFlowCategoryDto.builder()
                .uuid(cashFlowCategory.getUuid())
                .name(cashFlowCategory.getName())
                .status(cashFlowCategory.getStatus())
                .systemCategory(cashFlowCategory.isSystemCategory())
                .expenseCategory(cashFlowCategory.isExpenseCategory())
                .gainCategory(cashFlowCategory.isGainCategory())
                .build();
    }
}
