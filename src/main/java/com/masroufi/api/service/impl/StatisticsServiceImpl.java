package com.masroufi.api.service.impl;

import com.masroufi.api.dto.StatisticsItem;
import com.masroufi.api.dto.response.GenericObject;
import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.dto.response.StatisticsResult;
import com.masroufi.api.entity.Account;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.repository.StatisticsRepository;
import com.masroufi.api.search.criteria.impl.StatisticsSearchCriteria;
import com.masroufi.api.service.StatisticsService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public StatisticsResult searchStatistics(StatisticsSearchCriteria criteria) {
        switch (criteria.getSearchType()) {
            case PER_MONTH: {
                return this.searchCashFlowStatsPerMonth(criteria);
            }
            case PER_CATEGORY: {
                return this.searchCashFlowStatsPerCategory(criteria);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public List<GenericObject> getCustomerCategories(CashFlowType cashFlowType) {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        return this.statisticsRepository.getCategoriesByCustomerAndCashFlowType(currentUser.getId(), cashFlowType);
    }

    @Override
    public List<Integer> getYearsList() {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        return this.statisticsRepository.getYearsListByCustomer(currentUser.getId());
    }

    private StatisticsResult searchCashFlowStatsPerCategory(StatisticsSearchCriteria criteria) {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        List<StatisticsItem> result;
        if (criteria.getCategoryUuid() != null && !criteria.getCategoryUuid().isEmpty()) {
            result = this.statisticsRepository.getCashFlowByCategoryAndTypeAndCustomerAndDateBetween(
                    currentUser.getId(),
                    criteria.getCashFlowType(),
                    criteria.getCategoryUuid(),
                    criteria.getStartDate(),
                    criteria.getEndDate(),
                    15
            );
        } else {
            result =  this.statisticsRepository.getCashFlowByCustomerAndTypeAndDateBetween(
                    currentUser.getId(),
                    criteria.getCashFlowType(),
                    criteria.getStartDate(),
                    criteria.getEndDate(),
                    15
            );
        }
        if (result != null && !result.isEmpty()) {
            return StatisticsResult.builder()
                    .translateLabels(false)
                    .labels(result.stream().map(StatisticsItem::getLabel).collect(Collectors.toList()))
                    .data(result.stream().map(StatisticsItem::getAmount).collect(Collectors.toList()))
                    .build();
        } else {
            return StatisticsResult.builder()
                    .translateLabels(false)
                    .labels(new ArrayList<>())
                    .data(new ArrayList<>())
                    .build();
        }
    }

    private StatisticsResult searchCashFlowStatsPerMonth(StatisticsSearchCriteria criteria) {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        List<MonthAmount> result;
        if (Objects.equals(criteria.getCashFlowType(), CashFlowType.EXPENSE)) {
            result = this.statisticsRepository.getExpensesPerMonthByCustomerAndYear(currentUser.getId(), criteria.getYear());
        } else {
            result = this.statisticsRepository.getRevenuesPerMonthByCustomerAndYear(currentUser.getId(), criteria.getYear());
        }
        if (result != null && !result.isEmpty()) {
            return StatisticsResult.builder()
                    .translateLabels(true)
                    .labels(result.stream().map(elt -> elt.getMonth().name()).collect(Collectors.toList()))
                    .data(result.stream().map(MonthAmount::getAmount).collect(Collectors.toList()))
                    .build();
        } else {
            return StatisticsResult.builder()
                    .translateLabels(true)
                    .labels(new ArrayList<>())
                    .data(new ArrayList<>())
                    .build();
        }
    }
}
