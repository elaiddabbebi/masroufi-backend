package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CashFlowConfigDto;
import com.masroufi.api.dto.SubscriptionConfigDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.embeddable.CashFlowConfig;
import com.masroufi.api.entity.embeddable.CustomerCashState;
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

    private static double getNewCashAmount(Double currentCashAmount, Double oldInitialCashAmount,Double newInitialCashAmount) {
        double safeCurrentCashAmount = currentCashAmount != null ? currentCashAmount : 0;
        double safeOldInitialCashAmount = oldInitialCashAmount != null ? oldInitialCashAmount : 0;
        double safeNewInitialCashAmount = newInitialCashAmount != null ? newInitialCashAmount : 0;

        return safeCurrentCashAmount - safeOldInitialCashAmount + safeNewInitialCashAmount;
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

            CustomerCashState customerCashState = currentUser.getCustomerCashState();
            if (customerCashState == null) {
                customerCashState = new CustomerCashState();
            }
            customerCashState.setCurrentCashAmount(
                    getNewCashAmount(
                            customerCashState.getCurrentCashAmount(),
                            config.getInitialCashAmount(),
                            configDto.getInitialCashAmount()
                    )
            );
            currentUser.setCustomerCashState(customerCashState);

            this.accountRepository.save(currentUser);
            return CashFlowConfigDto.buildFromCashFlowConfig(config);
        }
        return null;
    }

    @Override
    public SubscriptionConfigDto getSubscriptionConfig() {
        Account currentUser = this.applicationSecurityContext.getCurrentUser();
        if (currentUser != null) {
            return SubscriptionConfigDto.buildFromSubscriptionConfig(currentUser.getSubscriptionConfig());
        } else {
            return null;
        }
    }
}
