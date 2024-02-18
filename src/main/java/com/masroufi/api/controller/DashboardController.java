package com.masroufi.api.controller;


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
}
