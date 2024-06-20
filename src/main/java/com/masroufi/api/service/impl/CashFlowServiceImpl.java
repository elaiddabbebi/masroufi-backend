package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CashFlowCategoryDto;
import com.masroufi.api.dto.CashFlowDto;
import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CashFlow;
import com.masroufi.api.entity.CashFlowCategory;
import com.masroufi.api.enums.CashFlowStatus;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.CashFlowRepository;
import com.masroufi.api.service.CashFlowCategoryService;
import com.masroufi.api.service.CashFlowService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public CashFlowDto createCashFlow(CashFlowDto cashFlow) {
        if (cashFlow == null || cashFlow.getCategory() == null) {
            return null;
        } else {
            this.applicationSecurityContext.isSupperAdminOrThrowException();
            this.isNewCashFlowOrThrowException(cashFlow.getName(), cashFlow.getCategory().getName());
            CashFlow newCashFlow = this.create(cashFlow);
            return CashFlowDto.buildFromCashFlow(newCashFlow);
        }
    }

    @Override
    public CashFlowDto updateCashFlow(String uuid, CashFlowDto cashFlow) {
        if (cashFlow == null || cashFlow.getCategory() == null) {
            return null;
        } else {
            this.applicationSecurityContext.isSupperAdminOrThrowException();
            CashFlow cashFlowToUpdate = this.cashFlowRepository.findByUuid(uuid);
            cashFlowToUpdate = this.update(cashFlowToUpdate, cashFlow);
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

    @Override
    public CashFlow findOrCreatedOrUpdateCashFlow(String name, String category, boolean gain, boolean expense) {
        CashFlowCategoryDto categoryDto = CashFlowCategoryDto.builder()
                .name(category.trim())
                .expense(expense)
                .gain(gain)
                .build();

        CashFlowDto cashFlowDto = CashFlowDto.builder()
                .name(name)
                .expense(expense)
                .gain(gain)
                .category(categoryDto)
                .build();
        if (this.isNewCashFlow(name, category)) {
            return this.create(cashFlowDto);
        } else {
            CashFlow cashFlowToUpdate = this.findCashFlowByNameAndCategory(name, category);
            return this.update(cashFlowToUpdate, cashFlowDto);
        }
    }

    @Override
    public List<String> getAllCashFlowNameList() {
        if (this.applicationSecurityContext.isSupperAdmin()) {
//            return this.cashFlowRepository.findAllCashFlowNamesForSupperAdmin();
            return new ArrayList<>();
        } else {
            Account customer = this.applicationSecurityContext.getCurrentUser();
//            return this.cashFlowRepository.findAllCashFlowNamesByCustomer(customer.getId());
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> searchByCategory(String category) {
        if (this.applicationSecurityContext.isSupperAdmin()) {
            if (category != null && !category.isEmpty()) {
//                return this.cashFlowRepository.findAllByCategoryForSupperAdmin(category);
                return new ArrayList<>();
            } else {
//                return this.cashFlowRepository.findAllCashFlowNamesForSupperAdmin();
                return new ArrayList<>();
            }
        } else {
            Account customer = this.applicationSecurityContext.getCurrentUser();
            if (category != null && !category.isEmpty()) {
//                return this.cashFlowRepository.findAllByCustomerAndCategory(customer.getId(), category);
                return new ArrayList<>();
            } else {
//                return this.cashFlowRepository.findAllCashFlowNamesByCustomer(customer.getId());
                return new ArrayList<>();
            }
        }
    }

    private void isNewCashFlowOrThrowException(String cashFlowName, String categoryName) {
        if (!this.isNewCashFlow(cashFlowName,categoryName)) {
            throw new RuntimeException("Cash flow already exist");
        }
    }

    private boolean isNewCashFlow(String cashFlowName, String categoryName) {
        return this.findCashFlowByNameAndCategory(cashFlowName, categoryName) == null;
    }

    private CashFlow findCashFlowByNameAndCategory(String cashFlowName, String categoryName) {
        List<CashFlow> cashFlowList = this.cashFlowRepository.findAllByNameIgnoreCase(cashFlowName.trim());
        if (cashFlowList != null && !cashFlowList.isEmpty()) {
            for (CashFlow cashFlow: cashFlowList) {
                CashFlowCategory category = cashFlow.getCategory();
                if (category != null && category.getName() != null && category.getName().equalsIgnoreCase(categoryName)) {
                    return cashFlow;
                }
            }
        }
        return null;
    }

    private CashFlow create(CashFlowDto dto) {
        CashFlow newCashFlow = new CashFlow();
        newCashFlow.setName(dto.getName().trim());
        newCashFlow.setCategory(this.cashFlowCategoryService.findOrCreateOrUpdateCashFlowCategory(dto.getCategory()));
        newCashFlow.setGain(dto.isGain());
        newCashFlow.setExpense(dto.isExpense());
        Account user = this.applicationSecurityContext.getCurrentUser();
        if (user != null) {
            newCashFlow.setCreatedBy(user.getId());
        }
        if (this.applicationSecurityContext.isSupperAdmin()) {
            newCashFlow.setStatus(CashFlowStatus.VALIDATED);
            newCashFlow.setPublished(dto.isPublished());
        } else {
            newCashFlow.setStatus(CashFlowStatus.PENDING);
            newCashFlow.setPublished(false);
        }
        return this.cashFlowRepository.save(newCashFlow);
    }

    private CashFlow update(CashFlow cashFlowToUpdate, CashFlowDto dto) {
        if (cashFlowToUpdate == null) {
            throw new RuntimeException("Cashflow not found");
        }
        if (
                (cashFlowToUpdate.getCategory() != null && this.applicationSecurityContext.isSupperAdmin() &&
                        !cashFlowToUpdate.getCategory().getName().equalsIgnoreCase(dto.getCategory().getName())) ||
                        !cashFlowToUpdate.getName().equalsIgnoreCase(dto.getName())
        ) {
            this.isNewCashFlowOrThrowException(dto.getName(), dto.getCategory().getName());
        }
        cashFlowToUpdate.setName(dto.getName().trim());
        cashFlowToUpdate.setCategory(this.cashFlowCategoryService.findOrCreateOrUpdateCashFlowCategory(dto.getCategory()));
        if (this.applicationSecurityContext.isSupperAdmin()) {
            cashFlowToUpdate.setPublished(dto.isPublished());
            cashFlowToUpdate.setGain(dto.isGain());
            cashFlowToUpdate.setExpense(dto.isExpense());
        } else {
            if (dto.isGain()) {
                cashFlowToUpdate.setGain(true);
            }
            if (dto.isExpense()) {
                cashFlowToUpdate.setExpense(true);
            }
        }
        return this.cashFlowRepository.save(cashFlowToUpdate);
    }
}
