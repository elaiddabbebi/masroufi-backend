package com.masroufi.api.service;

import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CustomerCashFlowRegistry;

import java.util.List;

public interface CustomerCashFlowRegistryService {

    List<CustomerCashFlowRegistryDto> findAll();

    CustomerCashFlowRegistryDto create(CustomerCashFlowRegistryDto dto);

    CustomerCashFlowRegistryDto delete(String uuid);

    CustomerCashFlowRegistryDto findByUuid(String uuid);

    CustomerCashFlowRegistryDto update(String uuid, CustomerCashFlowRegistryDto dto);

    void processCustomerCashFlowTransaction(CustomerCashFlowRegistry cashFlow);

    void reverseCustomerCashFlowTransaction(CustomerCashFlowRegistry cashFlow);

    void processCustomerCashStateTransaction(CustomerCashFlowRegistry cashFlow);

    void reverseCustomerCashStateTransaction(CustomerCashFlowRegistry cashFlow);

    void processAllAggregatedDataOf(CustomerCashFlowRegistry cashFlow);

    void reverseAllAggregatedDataOf(CustomerCashFlowRegistry cashFlow);
}
