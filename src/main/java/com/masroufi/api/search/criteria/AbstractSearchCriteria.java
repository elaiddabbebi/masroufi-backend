package com.masroufi.api.search.criteria;

import com.masroufi.api.enums.SortOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractSearchCriteria {
    protected int page;
    protected int size;
    protected String primarySortField;
    protected SortOrder primarySortOrder;
    protected String secondarySortField;
    protected SortOrder secondarySortOrder;
}
