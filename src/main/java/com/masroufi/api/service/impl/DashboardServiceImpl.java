package com.masroufi.api.service.impl;

import com.masroufi.api.entity.Account;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import com.masroufi.api.service.DashboardService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private CustomerCashFlowRegistryService customerCashFlowRegistryService;

    @Override
    public Double getCurrentCashAmount() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        return this.customerCashFlowRegistryService.calculateCurrentCashAmountOfCustomer(customer);
    }
}
