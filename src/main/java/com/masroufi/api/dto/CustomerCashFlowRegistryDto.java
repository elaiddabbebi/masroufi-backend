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

    public static CustomerCashFlowRegistryDto buildFromCashFlowRegistry(CustomerCashFlowRegistry cashFlowRegistry) {
        if (cashFlowRegistry == null) {
            return null;
        }
        String cashFlowName = cashFlowRegistry.getCashFlow() != null ? cashFlowRegistry.getCashFlow().getName() : null;
        String cashFlowCategoryName = cashFlowRegistry.getCashFlow() != null && cashFlowRegistry.getCashFlow().getCategory() != null ?
                cashFlowRegistry.getCashFlow().getCategory().getName() : null;
        return CustomerCashFlowRegistryDto.builder()
                .uuid(cashFlowRegistry.getUuid())
                .date(cashFlowRegistry.getDate())
                .name(cashFlowName)
                .category(cashFlowCategoryName)
                .amount(cashFlowRegistry.getAmount())
                .type(cashFlowRegistry.getType())
                .build();
    }
}
