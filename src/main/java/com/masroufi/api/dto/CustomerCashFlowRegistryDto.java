package com.masroufi.api.dto;

import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.enums.CashFlowType;
import lombok.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCashFlowRegistryDto {
    private String uuid;
    private Date date;
    private String name;
    private String category;
    private Double amount;
    private CashFlowType type;

    public static CustomerCashFlowRegistryDto buildFromCashFlow(CustomerCashFlowRegistry cashFlow) {
        if (cashFlow == null) {
            return null;
        }
        return CustomerCashFlowRegistryDto.builder()
                .uuid(cashFlow.getUuid())
                .date(cashFlow.getDate())
                .name(cashFlow.getCashFlow().getName())
                .category(cashFlow.getCashFlow().getCategory().getName())
                .amount(cashFlow.getAmount())
                .type(cashFlow.getType())
                .build();
    }
}
