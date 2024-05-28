package com.masroufi.api.service.impl;

import com.masroufi.api.dto.response.GenericObject;
import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.dto.response.StatisticsResult;
import com.masroufi.api.entity.Account;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.repository.AggregatedCustomerCashFlowRepository;
import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.search.criteria.impl.StatisticsSearchCriteria;
import com.masroufi.api.service.DashboardService;
import com.masroufi.api.service.StatisticsService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private AggregatedCustomerCashFlowRepository aggregatedCustomerCashFlowRepository;

    @Autowired
    private CustomerCashFlowRegistryRepository customerCashFlowRegistryRepository;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private DashboardService dashboardService;

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
        List<Map<String, Object>> result = this.customerCashFlowRegistryRepository.getCustomerCategories(currentUser.getId(), cashFlowType);;
        if (result != null && !result.isEmpty()) {
            return result
                    .stream()
                    .map(elt -> GenericObject
                            .builder()
                            .key((String) elt.get("key"))
                            .value(elt.get("value"))
                            .build()
                    )
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> getYearsList() {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        return this.customerCashFlowRegistryRepository.getYearsListByCustomer(currentUser.getId());
    }

    private StatisticsResult searchCashFlowStatsPerCategory(StatisticsSearchCriteria criteria) {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        List<Map<String, Object>> result;
        if (criteria.getCategoryUuid() != null && !criteria.getCategoryUuid().isEmpty()) {
            result = this.customerCashFlowRegistryRepository.getCashFlowByCategoryAndCustomerBetween(
                    currentUser.getId(),
                    criteria.getCashFlowType(),
                    criteria.getCategoryUuid(),
                    criteria.getStartDate(),
                    criteria.getEndDate(),
                    PageRequest.of(0, 15)
            );
        } else {
            result =  this.customerCashFlowRegistryRepository.getCashFlowByCustomerBetween(
                    currentUser.getId(),
                    criteria.getCashFlowType(),
                    criteria.getStartDate(),
                    criteria.getEndDate(),
                    PageRequest.of(0, 15)
            );
        }
        if (result != null && !result.isEmpty()) {
            return StatisticsResult.builder()
                    .translateLabels(false)
                    .labels(result.stream().map(elt -> (String) elt.get("name")).collect(Collectors.toList()))
                    .data(result.stream().map(elt -> (Double) elt.get("amount")).collect(Collectors.toList()))
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
        List<MonthAmount> result = this.dashboardService.calculateFlowByCustomerAndYear(
                currentUser.getId(),
                criteria.getYear(),
                criteria.getCashFlowType()
        );
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
