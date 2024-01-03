package com.masroufi.api.dto;

import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.enums.CashFlowType;
import lombok.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashFlowDto {
    private String uuid;
    private Date date;
    private String name;
    private String category;
    private Double amount;
    private CashFlowType type;

    public static CashFlowDto buildFromCashFlow(CashFlow cashFlow) {
        if (cashFlow == null) {
            return null;
        }
        return CashFlowDto.builder()
                .uuid(cashFlow.getUuid())
                .date(cashFlow.getDate())
                .name(cashFlow.getSystemCashFlow().getName())
                .category(cashFlow.getSystemCashFlow().getCategory().getName())
                .amount(cashFlow.getAmount())
                .type(cashFlow.getType())
                .build();
    }
}
