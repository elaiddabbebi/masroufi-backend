package com.masroufi.api.search.specification.impl;

import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.search.criteria.impl.CustomerCashFlowRegistrySearchCriteria;
import com.masroufi.api.search.specification.AbstractSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerCashFlowRegistrySpecification extends AbstractSpecificationBuilder<CustomerCashFlowRegistry> {

    public static Specification<CustomerCashFlowRegistry> of(CustomerCashFlowRegistrySearchCriteria searchCriteria) {
        List<Specification<CustomerCashFlowRegistry>> specifications = new ArrayList<>();

        if (searchCriteria.getCustomerId() != null) {
            specifications.add((root, query, criteriaBuilder) ->  criteriaBuilder.equal(root.get("customer"), searchCriteria.getCustomerId()));
        }

        if (searchCriteria.getCashFlow() != null) {

        }

        Specification<CustomerCashFlowRegistry> specification = null;
        for (Specification<CustomerCashFlowRegistry> spec: specifications) {
            if (specification == null) {
                specification = spec;
            } else {
                specification.and(spec);
            }
        }
        return specification;
    }
}
