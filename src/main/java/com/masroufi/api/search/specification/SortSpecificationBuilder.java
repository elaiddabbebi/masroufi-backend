package com.masroufi.api.search.specification;

import com.masroufi.api.search.criteria.AbstractSearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class SortSpecificationBuilder {
    public static Pageable pageOf(AbstractSearchCriteria criteria) {
        List<Sort.Order> orders = new ArrayList<>();

        if (criteria.getPrimarySortField() != null && criteria.getPrimarySortOrder() != null) {
            Sort.Order primaryOrder = new Sort.Order(Sort.Direction.valueOf(criteria.getPrimarySortOrder().name()), criteria.getPrimarySortField());
            orders.add(primaryOrder);
        }

        if (criteria.getSecondarySortField() != null && criteria.getSecondarySortOrder() != null) {
            Sort.Order secondaryOrder = new Sort.Order(Sort.Direction.valueOf(criteria.getSecondarySortOrder().name()), criteria.getSecondarySortField());
            orders.add(secondaryOrder);
        }

        return PageRequest.of(criteria.getPage() - 1, criteria.getSize(), Sort.by(orders));
    }
}
