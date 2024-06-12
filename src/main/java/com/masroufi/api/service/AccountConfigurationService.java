package com.masroufi.api.service;

import com.masroufi.api.dto.CashFlowConfigDto;
import com.masroufi.api.dto.SubscriptionConfigDto;
import com.masroufi.api.entity.Account;

public interface AccountConfigurationService {

    CashFlowConfigDto getCashFlowConfig();

    CashFlowConfigDto updateCashFlowConfig(CashFlowConfigDto configDto);

    SubscriptionConfigDto getSubscriptionConfig();
}
