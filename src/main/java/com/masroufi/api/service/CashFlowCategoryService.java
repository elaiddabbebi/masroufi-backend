package com.masroufi.api.service;

import com.masroufi.api.dto.CashFlowCategoryDto;

import java.util.List;

public interface CashFlowCategoryService {
    CashFlowCategoryDto createCashFlowCategory(CashFlowCategoryDto cashFlowCategory);

    CashFlowCategoryDto updateCashFlowCategory(String uuid, CashFlowCategoryDto cashFlowCategory);

    CashFlowCategoryDto deleteCashFlowCategory(String uuid);

    CashFlowCategoryDto findCashFlowCategory(String uuid);

    CashFlowCategoryDto updateCashFlowCategoryValidity(String uuid, boolean valid);

    List<CashFlowCategoryDto> findAll();

    boolean checkIfCategoryExist(String categoryName);
}
