package com.masroufi.api.service;

import com.masroufi.api.dto.response.GenericObject;
import com.masroufi.api.dto.response.StatisticsResult;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.search.criteria.impl.StatisticsSearchCriteria;

import java.util.List;

public interface StatisticsService {

    StatisticsResult searchStatistics(StatisticsSearchCriteria criteria);

    List<GenericObject> getCustomerCategories(CashFlowType cashFlowType);
}
