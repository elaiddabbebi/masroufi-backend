package com.masroufi.api.search.criteria.impl;

import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.enums.StatisticsSearchType;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class StatisticsSearchCriteria {
    private CashFlowType cashFlowType;
    private StatisticsSearchType searchType;
    private String category;
    private Integer year;
    private Date startDate;
    private Date endDate;
}
