package com.masroufi.api.controller;

import com.masroufi.api.dto.CashFlowConfigDto;
import com.masroufi.api.dto.SubscriptionConfigDto;
import com.masroufi.api.service.AccountConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-config")
public class AccountConfigurationController {

    @Autowired
    private AccountConfigurationService accountConfigurationService;

    @GetMapping("/cash-flow")
    CashFlowConfigDto getCashFlowConfig() {
        return this.accountConfigurationService.getCashFlowConfig();
    }

    @PutMapping("/cash-flow")
    CashFlowConfigDto updateCashFlowConfig(@RequestBody CashFlowConfigDto configDto) {
        return this.accountConfigurationService.updateCashFlowConfig(configDto);
    }

    @GetMapping("/subscription")
    SubscriptionConfigDto getSubscriptionConfig() {
        return this.accountConfigurationService.getSubscriptionConfig();
    }
}
