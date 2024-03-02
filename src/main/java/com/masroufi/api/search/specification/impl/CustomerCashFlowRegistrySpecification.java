package com.masroufi.api.search.specification.impl;

import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.search.criteria.impl.CustomerCashFlowRegistrySearchCriteria;
import com.masroufi.api.search.specification.AbstractSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class CustomerCashFlowRegistrySpecification extends AbstractSpecificationBuilder<CustomerCashFlowRegistry> {

    public static Specification<CustomerCashFlowRegistry> of(CustomerCashFlowRegistrySearchCriteria searchCriteria) {
        return (root, query, builder) -> null;
    }
}
