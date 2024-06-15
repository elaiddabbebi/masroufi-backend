package com.masroufi.api.controller;

import com.masroufi.api.dto.CashFlowConfigDto;
import com.masroufi.api.dto.SubscriptionConfigDto;
import com.masroufi.api.dto.request.UserLanguageConfigModel;
import com.masroufi.api.enums.AppLocale;
import com.masroufi.api.service.AccountConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account-config")
public class AccountConfigurationController {

    @Autowired
    private AccountConfigurationService accountConfigurationService;

    @PreAuthorize("@applicationSecurityContext.isCustomer()")
    @GetMapping("/cash-flow")
    CashFlowConfigDto getCashFlowConfig() {
        return this.accountConfigurationService.getCashFlowConfig();
    }

    @PreAuthorize("@applicationSecurityContext.isCustomer()")
    @PutMapping("/cash-flow")
    CashFlowConfigDto updateCashFlowConfig(@RequestBody CashFlowConfigDto configDto) {
        return this.accountConfigurationService.updateCashFlowConfig(configDto);
    }

    @PreAuthorize("@applicationSecurityContext.isCustomer()")
    @GetMapping("/subscription")
    SubscriptionConfigDto getSubscriptionConfig() {
        return this.accountConfigurationService.getSubscriptionConfig();
    }

    @GetMapping("/language")
    AppLocale getLanguageConfig() {
        return this.accountConfigurationService.getLanguageConfig();
    }

    @PutMapping("/language")
    AppLocale updateLanguageConfig(@RequestBody UserLanguageConfigModel languageConfigModel) {
        return this.accountConfigurationService.updateLanguageConfig(languageConfigModel);
    }
}
