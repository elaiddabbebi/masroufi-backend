package com.masroufi.api.controller;

import com.masroufi.api.dto.response.GenericObject;
import com.masroufi.api.dto.response.StatisticsResult;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.search.criteria.impl.StatisticsSearchCriteria;
import com.masroufi.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/search")
    StatisticsResult searchStatistics(@ModelAttribute StatisticsSearchCriteria criteria) {
        return this.statisticsService.searchStatistics(criteria);
    }

    @GetMapping("/cash-flow-categories")
    List<GenericObject> getCustomerCategories(@RequestParam CashFlowType cashFlowType) {
        return this.statisticsService.getCustomerCategories(cashFlowType);
    }
}
