package com.masroufi.api.service;

import com.masroufi.api.dto.CashFlowDto;
import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.entity.CashFlow;

import java.util.List;

public interface CashFlowService {
    CashFlowDto createCashFlow(CashFlowDto cashFlowDto);

    CashFlowDto updateCashFlow(String uuid, CashFlowDto cashFlowDto);

    CashFlowDto deleteCashFlow(String uuid);

    CashFlowDto findCashFlowByUuid(String uuid);

    CashFlowDto updateCashFlowValidity(String uuid, boolean valid);

    List<CashFlowDto> findAll();

    CashFlow findOrCreatedOrUpdateCashFlow(String name, String category, boolean gain, boolean expense);
}
