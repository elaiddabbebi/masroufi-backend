package com.masroufi.api.service.impl;

import com.masroufi.api.repository.AggregatedCustomerCashFlowRepository;
import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private AggregatedCustomerCashFlowRepository aggregatedCustomerCashFlowRepository;

    @Autowired
    private CustomerCashFlowRegistryRepository customerCashFlowRegistryRepository;
}
