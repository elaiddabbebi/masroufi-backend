package com.masroufi.api.dto;

import com.masroufi.api.entity.SystemCashFlow;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.enums.SystemCashFlowStatus;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemCashFlowDto {

    private String uuid;
    private String name;
    private CashFlowType type;
    private CashFlowCategoryDto category;
    private SystemCashFlowStatus status;

    public static SystemCashFlowDto buildFromSystemCashFlow(SystemCashFlow systemCashFlow) {
        if (systemCashFlow == null) {
            return null;
        }
        return SystemCashFlowDto.builder()
                .uuid(systemCashFlow.getUuid())
                .name(systemCashFlow.getName())
                .type(systemCashFlow.getType())
                .category(CashFlowCategoryDto.buildFromCashFlowCategory(systemCashFlow.getCategory()))
                .status(systemCashFlow.getStatus())
                .build();
    }
}
