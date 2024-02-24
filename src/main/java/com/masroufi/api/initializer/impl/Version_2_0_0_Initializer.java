package com.masroufi.api.initializer.impl;

import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.initializer.ApplicationInitializer;
import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class Version_2_0_0_Initializer implements ApplicationInitializer {

    @Autowired
    private CustomerCashFlowRegistryService customerCashFlowRegistryService;

    @Autowired
    private CustomerCashFlowRegistryRepository customerCashFlowRegistryRepository;

    @Override
    public void initApplication() {
        // this.processAllCashFlowRegistries(); // Already executed
    }

    private void processAllCashFlowRegistries() {
        List<CustomerCashFlowRegistry> allRegistries = this.customerCashFlowRegistryRepository.findAll();

        if (allRegistries != null && !allRegistries.isEmpty()) {
            for (CustomerCashFlowRegistry registry: allRegistries) {
                if (registry != null) {
                    this.customerCashFlowRegistryService.processAllAggregatedDataOf(registry);
                }
            }
        }
    }
}
