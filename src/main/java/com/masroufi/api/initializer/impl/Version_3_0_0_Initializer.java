package com.masroufi.api.initializer.impl;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.enums.AppLocale;
import com.masroufi.api.initializer.ApplicationInitializer;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class Version_3_0_0_Initializer implements ApplicationInitializer {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void initApplication() {
//         this.initAllAccountsLocale(); // Already executed
    }

    private void initAllAccountsLocale() {
        List<Account> accountList = this.accountRepository.findAll();

        if (accountList != null && !accountList.isEmpty()) {
            for (Account account: accountList) {
                if (account != null) {
                    account.setLocale(AppLocale.defaultLocale());
                    this.accountRepository.save(account);
                }
            }
        }
    }
}
