package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CashFlowDto;
import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.enums.CashFlowStatus;
import com.masroufi.api.repository.CashFlowRepository;
import com.masroufi.api.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CashFlowServiceImpl implements CashFlowService {

    @Autowired
    private CashFlowRepository cashFlowRepository;

    @Override
    public CashFlowDto createCashFlow(CashFlowDto cashFlow) {
        if (cashFlow == null) {
            return null;
        } else {
            CashFlow newCashFlow = new CashFlow();
            newCashFlow.setName(cashFlow.getName().trim());

            newCashFlow.setSystemCashFlow(true);
            newCashFlow.setStatus(CashFlowStatus.VALIDATED);
            newCashFlow = this.cashFlowRepository.save(newCashFlow);
            return CashFlowDto.buildFromCashFlow(newCashFlow);
        }
    }

    @Override
    public CashFlowDto updateCashFlow(String uuid, CashFlowDto cashFlow) {
        if (cashFlow == null) {
            return null;
        } else {
            CashFlow cashFlowToUpdate = this.cashFlowRepository.findByUuid(uuid);
            if (cashFlowToUpdate == null) {
                throw new RuntimeException(" not found");
            }
            cashFlowToUpdate.setName(cashFlow.getName().trim());

            cashFlowToUpdate = this.cashFlowRepository.save(cashFlowToUpdate);
            return CashFlowDto.buildFromCashFlow(cashFlowToUpdate);
        }
    }

    @Override
    public CashFlowDto deleteCashFlow(String uuid) {
        CashFlow cashFlowToDelete = this.cashFlowRepository.findByUuid(uuid);
        if (cashFlowToDelete == null) {
            throw new RuntimeException(" not found");
        }
        cashFlowToDelete.setIsDeleted(true);
        cashFlowToDelete = this.cashFlowRepository.save(cashFlowToDelete);
        return CashFlowDto.buildFromCashFlow(cashFlowToDelete);
    }

    @Override
    public CashFlowDto findCashFlow(String uuid) {
        CashFlow cashFlow = this.cashFlowRepository.findByUuid(uuid);
        if (cashFlow == null) {
            throw new RuntimeException(" not found");
        }
        return CashFlowDto.buildFromCashFlow(cashFlow);
    }

    @Override
    public CashFlowDto updateCashFlowValidity(String uuid, boolean valid) {
        CashFlow cashFlowToUpdate = this.cashFlowRepository.findByUuid(uuid);
        if (cashFlowToUpdate == null) {
            throw new RuntimeException(" not found");
        }
        cashFlowToUpdate.setStatus(valid ? CashFlowStatus.VALIDATED : CashFlowStatus.REJECTED);
        cashFlowToUpdate = this.cashFlowRepository.save(cashFlowToUpdate);
        return CashFlowDto.buildFromCashFlow(cashFlowToUpdate);
    }

    @Override
    public List<CashFlowDto> findAll() {
        List<CashFlow> allCashFlows = this.cashFlowRepository.findAll();
        return allCashFlows.stream().map(CashFlowDto::buildFromCashFlow).collect(Collectors.toList());
    }
}
