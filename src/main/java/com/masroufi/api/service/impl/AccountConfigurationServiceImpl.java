package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CashFlowConfigDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.embeddable.CashFlowConfig;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.service.AccountConfigurationService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountConfigurationServiceImpl implements AccountConfigurationService {

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public CashFlowConfigDto getCashFlowConfig() {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        if (currentUser != null) {
            return CashFlowConfigDto.buildFromCashFlowConfig(currentUser.getCashFlowConfig());
        }
        return null;
    }

    @Override
    public CashFlowConfigDto updateCashFlowConfig(CashFlowConfigDto configDto) {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        if (currentUser != null) {
            CashFlowConfig config = currentUser.getCashFlowConfig();
            if (config == null) {
                config = new CashFlowConfig();
            }
            config.setInitialCashAmount(configDto.getInitialCashAmount());
            currentUser.setCashFlowConfig(config);
            this.accountRepository.save(currentUser);
            return CashFlowConfigDto.buildFromCashFlowConfig(config);
        }
        return null;
    }

    @Override
    public Double getInitialCashAmountOf(Account account) {
        CashFlowConfig config = account.getCashFlowConfig();
        if (config != null) {
            if (config.getInitialCashAmount() != null) {
                return config.getInitialCashAmount();
            }
        }
        return 0D;
    }
}
