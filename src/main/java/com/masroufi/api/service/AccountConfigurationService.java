package com.masroufi.api.service;

import com.masroufi.api.dto.CashFlowConfigDto;

public interface AccountConfigurationService {

    CashFlowConfigDto getCashFlowConfig();

    CashFlowConfigDto updateCashFlowConfig(CashFlowConfigDto configDto);
}
