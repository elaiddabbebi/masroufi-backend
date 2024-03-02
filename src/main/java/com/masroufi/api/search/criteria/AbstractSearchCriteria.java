package com.masroufi.api.search.criteria;

import java.util.List;

public abstract class AbstractSearchCriteria {
    public int page;
    public int size;
    public List<Sort> orderBy;
}
