package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CashFlowDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.enums.CashFlowStatus;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.CashFlowRepository;
import com.masroufi.api.service.CashFlowCategoryService;
import com.masroufi.api.service.CashFlowService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CashFlowServiceImpl implements CashFlowService {

    @Autowired
    private CashFlowRepository cashFlowRepository;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CashFlowCategoryService cashFlowCategoryService;

    private void isNewCashFlowOrThrowException(String cashFlowName) {
        List<CashFlow> cashFlowList = this.cashFlowRepository.findAllByNameIgnoreCase(cashFlowName);
        if (cashFlowList != null && !cashFlowList.isEmpty()) {
            throw new RuntimeException("Cash flow already exist");
        }
    }

    @Override
    public CashFlowDto createCashFlow(CashFlowDto cashFlow) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        if (cashFlow == null) {
            return null;
        } else {
            CashFlow newCashFlow = new CashFlow();
            newCashFlow.setName(cashFlow.getName().trim());
            newCashFlow.setCategory(this.cashFlowCategoryService.findOrCreateOrUpdateCashFlowCategory(cashFlow.getCategory()));
            newCashFlow.setGain(cashFlow.isGain());
            newCashFlow.setExpense(cashFlow.isExpense());
            newCashFlow.setPublished(cashFlow.isPublished());
            newCashFlow.setStatus(CashFlowStatus.VALIDATED);
            Account user = this.applicationSecurityContext.getCurrentUser();
            if (user != null) {
                newCashFlow.setCreatedBy(user.getId());
            }
            newCashFlow = this.cashFlowRepository.save(newCashFlow);
            return CashFlowDto.buildFromCashFlow(newCashFlow);
        }
    }

    @Override
    public CashFlowDto updateCashFlow(String uuid, CashFlowDto cashFlow) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        if (cashFlow == null) {
            return null;
        } else {
            CashFlow cashFlowToUpdate = this.cashFlowRepository.findByUuid(uuid);
            if (cashFlowToUpdate == null) {
                throw new RuntimeException("Cashflow not found");
            }
            cashFlowToUpdate.setName(cashFlow.getName().trim());
            cashFlowToUpdate.setCategory(this.cashFlowCategoryService.findOrCreateOrUpdateCashFlowCategory(cashFlow.getCategory()));
            cashFlowToUpdate.setGain(cashFlow.isGain());
            cashFlowToUpdate.setExpense(cashFlow.isExpense());
            cashFlowToUpdate.setPublished(cashFlow.isPublished());
            cashFlowToUpdate = this.cashFlowRepository.save(cashFlowToUpdate);
            return CashFlowDto.buildFromCashFlow(cashFlowToUpdate);
        }
    }

    @Override
    public CashFlowDto deleteCashFlow(String uuid) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        CashFlow cashFlowToDelete = this.cashFlowRepository.findByUuid(uuid);
        if (cashFlowToDelete == null) {
            throw new RuntimeException("Cashflow not found");
        }
        cashFlowToDelete.setIsDeleted(true);
        cashFlowToDelete = this.cashFlowRepository.save(cashFlowToDelete);
        return CashFlowDto.buildFromCashFlow(cashFlowToDelete);
    }

    @Override
    public CashFlowDto findCashFlowByUuid(String uuid) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        CashFlow cashFlow = this.cashFlowRepository.findByUuid(uuid);
        if (cashFlow == null) {
            throw new RuntimeException("Cashflow not found");
        }
        return CashFlowDto.buildFromCashFlow(cashFlow);
    }

    @Override
    public CashFlowDto updateCashFlowValidity(String uuid, boolean valid) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        CashFlow cashFlowToUpdate = this.cashFlowRepository.findByUuid(uuid);
        if (cashFlowToUpdate == null) {
            throw new RuntimeException("Cashflow not found");
        }
        cashFlowToUpdate.setStatus(valid ? CashFlowStatus.VALIDATED : CashFlowStatus.REJECTED);
        cashFlowToUpdate = this.cashFlowRepository.save(cashFlowToUpdate);
        return CashFlowDto.buildFromCashFlow(cashFlowToUpdate);
    }

    @Override
    public List<CashFlowDto> findAll() {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        List<CashFlow> allCashFlows = this.cashFlowRepository.findAllByIsDeletedIsFalseOrIsDeletedIsNullOrderByIdDesc();
        return allCashFlows.stream().map(cashFlow -> {
            CashFlowDto dto = CashFlowDto.buildFromCashFlow(cashFlow);
            if (cashFlow.getCreatedBy() != null) {
                if (this.accountRepository.findById(cashFlow.getCreatedBy()).isPresent()) {
                    dto.setCreatedBy(this.accountRepository.findById(cashFlow.getCreatedBy()).get().getFullName());
                }
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
