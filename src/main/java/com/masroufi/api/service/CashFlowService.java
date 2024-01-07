package com.masroufi.api.service;

import com.masroufi.api.dto.CashFlowDto;

import java.util.List;

public interface CashFlowService {
    CashFlowDto createCashFlow(CashFlowDto cashFlowDto);

    CashFlowDto updateCashFlow(String uuid, CashFlowDto cashFlowDto);

    CashFlowDto deleteCashFlow(String uuid);

    CashFlowDto findCashFlow(String uuid);

    CashFlowDto updateCashFlowValidity(String uuid, boolean valid);

    List<CashFlowDto> findAll();
}
