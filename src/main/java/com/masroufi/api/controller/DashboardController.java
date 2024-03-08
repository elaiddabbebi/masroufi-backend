package com.masroufi.api.controller;


import com.masroufi.api.dto.ConsumptionEvolutionData;
import com.masroufi.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("current-cash-amount")
    Double getCurrentCashAmount() {
        return this.dashboardService.getCurrentCashAmount();
    }

    @GetMapping("current-week-consumption")
    Double getCurrentWeekConsumption() {
        return this.dashboardService.getCurrentWeekConsumption();
    }

    @GetMapping("last-week-consumption")
    Double getLastWeekConsumption() {
        return this.dashboardService.getLastWeekConsumption();
    }

    @GetMapping("current-week-balance")
    Double getCurrentWeekBalance() {
        return this.dashboardService.getCurrentWeekBalance();
    }

    @GetMapping("last-week-balance")
    Double getLastWeekBalance() {
        return this.dashboardService.getLastWeekBalance();
    }

    @GetMapping("current-month-consumption")
    Double getCurrentMonthConsumption() {
        return this.dashboardService.getCurrentMonthConsumption();
    }

    @GetMapping("last-month-consumption")
    Double getLastMonthConsumption() {
        return this.dashboardService.getLastMonthConsumption();
    }

    @GetMapping("current-month-balance")
    Double getCurrentMonthBalance() {
        return this.dashboardService.getCurrentMonthBalance();
    }

    @GetMapping("last-month-balance")
    Double getLastMonthBalance() {
        return this.dashboardService.getLastMonthBalance();
    }

    @GetMapping("current-year-revenue")
    Double getCurrentYearRevenue() {
        return this.dashboardService.getCurrentYearRevenue();
    }

    @GetMapping("current-year-balance")
    Double getCurrentYearBalance() {
        return this.dashboardService.getCurrentYearBalance();
    }

    @GetMapping("consumption-evolution")
    ConsumptionEvolutionData getConsumptionEvolution() {
        return this.dashboardService.getConsumptionEvolutionData();
    }
}
