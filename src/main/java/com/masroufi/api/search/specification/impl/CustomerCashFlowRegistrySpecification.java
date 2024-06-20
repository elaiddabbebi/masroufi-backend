package com.masroufi.api.search.specification.impl;

import com.masroufi.api.entity.*;
import com.masroufi.api.search.criteria.impl.CustomerCashFlowRegistrySearchCriteria;
import com.masroufi.api.search.specification.AbstractSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CustomerCashFlowRegistrySpecification extends AbstractSpecificationBuilder<CustomerCashFlowRegistry> {

    public static Specification<CustomerCashFlowRegistry> of(CustomerCashFlowRegistrySearchCriteria searchCriteria) {
        List<Specification<CustomerCashFlowRegistry>> specifications = new ArrayList<>();

        if (searchCriteria.getCustomerId() != null) {
            specifications.add((Specification<CustomerCashFlowRegistry>) (root, query, criteriaBuilder) ->  {
                Join<CustomerCashFlowRegistry, Account> accountJoin = root.join(CustomerCashFlowRegistry_.customer);
                return criteriaBuilder.equal(accountJoin.get(Account_.id), searchCriteria.getCustomerId());
            });
        }

        if (searchCriteria.getCashFlow() != null && !searchCriteria.getCashFlow().isEmpty()) {
            specifications.add((Specification<CustomerCashFlowRegistry>) (root, query, criteriaBuilder) ->  {
                Join<CustomerCashFlowRegistry, CashFlow> cashFlowJoin = root.join(CustomerCashFlowRegistry_.cashFlow);
                Join<CashFlow, CashFlowCategory> cashFlowCategoryJoin = cashFlowJoin.join(CashFlow_.category);
                Predicate likeCashFlowName = criteriaBuilder.like(criteriaBuilder.upper(cashFlowJoin.get(CashFlow_.name)), "%" + searchCriteria.getCashFlow().trim().toUpperCase() + "%");
                Predicate likeCashFlowCategory = criteriaBuilder.like(criteriaBuilder.upper(cashFlowCategoryJoin.get(CashFlowCategory_.name)), "%" + searchCriteria.getCashFlow().trim().toUpperCase() + "%");
                return criteriaBuilder.or(likeCashFlowName, likeCashFlowCategory);
            });
        }

        return specifications.stream().reduce(Specification::and).orElse(null);
    }
}

