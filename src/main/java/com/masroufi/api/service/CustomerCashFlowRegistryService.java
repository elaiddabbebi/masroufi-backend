package com.masroufi.api.service;

import com.masroufi.api.dto.CustomerCashFlowRegistryDto;

import java.util.List;

public interface CustomerCashFlowRegistryService {

    List<CustomerCashFlowRegistryDto> findAll();

    CustomerCashFlowRegistryDto create(CustomerCashFlowRegistryDto dto);

    CustomerCashFlowRegistryDto delete(String uuid);

    CustomerCashFlowRegistryDto findByUuid(String uuid);

    CustomerCashFlowRegistryDto update(String uuid, CustomerCashFlowRegistryDto dto);
}
