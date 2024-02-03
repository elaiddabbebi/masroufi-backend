package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CashFlowCategoryDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.entity.CashFlowCategory;
import com.masroufi.api.enums.CashFlowCategoryStatus;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.CashFlowCategoryRepository;
import com.masroufi.api.service.CashFlowCategoryService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashFlowCategoryServiceImpl implements CashFlowCategoryService {

    @Autowired
    private CashFlowCategoryRepository cashFlowCategoryRepository;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private AccountRepository accountRepository;

    private void isNewCategoryOrThrowException(String name) {
        List<CashFlowCategory> allByNameIgnoreCase = this.cashFlowCategoryRepository.findAllByNameIgnoreCase(name);
        if (allByNameIgnoreCase != null && !allByNameIgnoreCase.isEmpty()) {
            throw new RuntimeException("Cash flow category already exist");
        }
    }

    @Override
    public CashFlowCategoryDto createCashFlowCategory(CashFlowCategoryDto cashFlowCategory) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        if (cashFlowCategory == null) {
            return null;
        } else {
            this.isNewCategoryOrThrowException(cashFlowCategory.getName().trim());
            CashFlowCategory category = new CashFlowCategory();
            category.setName(cashFlowCategory.getName().trim());
            category.setGain(cashFlowCategory.isGain());
            category.setExpense(cashFlowCategory.isExpense());
            category.setSystemCategory(true);
            category.setStatus(CashFlowCategoryStatus.VALIDATED);
            Account user = this.applicationSecurityContext.getCurrentUser();
            if (user != null) {
                category.setCreatedBy(user.getId());
            }
            category = this.cashFlowCategoryRepository.save(category);
            return CashFlowCategoryDto.buildFromCashFlowCategory(category);
        }
    }

    @Override
    public CashFlowCategoryDto updateCashFlowCategory(String uuid, CashFlowCategoryDto cashFlowCategory) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        if (cashFlowCategory == null) {
            return null;
        } else {
            CashFlowCategory category = this.cashFlowCategoryRepository.findByUuid(uuid);
            if (category == null) {
                throw new RuntimeException("Category not found");
            }
            if (!category.getName().trim().equalsIgnoreCase(cashFlowCategory.getName().trim())) {
                this.isNewCategoryOrThrowException(cashFlowCategory.getName().trim());
            }
            category.setName(cashFlowCategory.getName().trim());
            category.setGain(cashFlowCategory.isGain());
            category.setExpense(cashFlowCategory.isExpense());
            category = this.cashFlowCategoryRepository.save(category);
            return CashFlowCategoryDto.buildFromCashFlowCategory(category);
        }
    }

    @Override
    public CashFlowCategoryDto deleteCashFlowCategory(String uuid) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        CashFlowCategory category = this.cashFlowCategoryRepository.findByUuid(uuid);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        category.setIsDeleted(true);
        category = this.cashFlowCategoryRepository.save(category);
        return CashFlowCategoryDto.buildFromCashFlowCategory(category);
    }

    @Override
    public CashFlowCategoryDto findCashFlowCategory(String uuid) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        CashFlowCategory category = this.cashFlowCategoryRepository.findByUuid(uuid);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return CashFlowCategoryDto.buildFromCashFlowCategory(category);
    }

    @Override
    public CashFlowCategoryDto updateCashFlowCategoryValidity(String uuid, boolean valid) {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        CashFlowCategory category = this.cashFlowCategoryRepository.findByUuid(uuid);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        category.setStatus(valid ? CashFlowCategoryStatus.VALIDATED : CashFlowCategoryStatus.REJECTED);
        category = this.cashFlowCategoryRepository.save(category);
        return CashFlowCategoryDto.buildFromCashFlowCategory(category);
    }

    @Override
    public List<CashFlowCategoryDto> findAll() {
        this.applicationSecurityContext.isSupperAdminOrThrowException();
        List<CashFlowCategory> allCategories = this.cashFlowCategoryRepository.findAllByIsDeletedIsFalseOrIsDeletedIsNullOrderByIdDesc();
        return allCategories.stream().map(category -> {
            CashFlowCategoryDto dto = CashFlowCategoryDto.buildFromCashFlowCategory(category);
            if (category.getCreatedBy() != null) {
                if (this.accountRepository.findById(category.getCreatedBy()).isPresent()) {
                    dto.setCreatedBy(this.accountRepository.findById(category.getCreatedBy()).get().getFullName());
                }
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
