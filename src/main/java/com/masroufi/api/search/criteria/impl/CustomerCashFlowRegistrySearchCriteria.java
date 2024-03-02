package com.masroufi.api.search.criteria.impl;

import com.masroufi.api.search.criteria.AbstractSearchCriteria;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCashFlowRegistrySearchCriteria extends AbstractSearchCriteria {
    private Long customerId;
    private String cashFlow;
}
