package com.masroufi.api.service;

import com.masroufi.api.dto.CashFlowConfigDto;
import com.masroufi.api.dto.SubscriptionConfigDto;
import com.masroufi.api.dto.request.UserLanguageConfigModel;
import com.masroufi.api.entity.Account;
import com.masroufi.api.enums.AppLocale;

public interface AccountConfigurationService {

    CashFlowConfigDto getCashFlowConfig();

    CashFlowConfigDto updateCashFlowConfig(CashFlowConfigDto configDto);

    SubscriptionConfigDto getSubscriptionConfig();

    AppLocale getLanguageConfig();

    AppLocale updateLanguageConfig(UserLanguageConfigModel languageConfigModel);
}
