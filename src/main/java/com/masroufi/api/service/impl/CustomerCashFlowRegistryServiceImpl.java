package com.masroufi.api.service.impl;

import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCashFlowRegistryServiceImpl implements CustomerCashFlowRegistryService {

    @Autowired
    private CustomerCashFlowRegistryRepository customerCashFlowRegistryRepository;
}
