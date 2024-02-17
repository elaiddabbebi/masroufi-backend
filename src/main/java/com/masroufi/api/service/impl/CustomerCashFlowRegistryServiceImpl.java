package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.service.CashFlowService;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerCashFlowRegistryServiceImpl implements CustomerCashFlowRegistryService {

    @Autowired
    private CustomerCashFlowRegistryRepository customerCashFlowRegistryRepository;

    @Autowired
    private CashFlowService cashFlowService;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Override
    public List<CustomerCashFlowRegistryDto> findAll() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            List<CustomerCashFlowRegistry> customerCashFlowRegistryList = this.customerCashFlowRegistryRepository.findAllByCustomer(customer);
            if (customerCashFlowRegistryList != null && !customerCashFlowRegistryList.isEmpty()) {
                return customerCashFlowRegistryList.stream().map(CustomerCashFlowRegistryDto::buildFromCashFlowRegistry).collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public CustomerCashFlowRegistryDto create(CustomerCashFlowRegistryDto dto) {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            CustomerCashFlowRegistry cashFlowRegistry = new CustomerCashFlowRegistry();
            cashFlowRegistry.setCustomer(customer);
            cashFlowRegistry.setAmount(dto.getAmount());
            cashFlowRegistry.setDate(dto.getDate());
            cashFlowRegistry.setType(dto.getType());
            cashFlowRegistry.setCashFlow(this.cashFlowService.findOrCreatedOrUpdateCashFlow(
                    dto.getName(),
                    dto.getCategory(),
                    dto.getType().equals(CashFlowType.GAIN),
                    dto.getType().equals(CashFlowType.EXPENSE)
            ));

            cashFlowRegistry = this.customerCashFlowRegistryRepository.save(cashFlowRegistry);
            return CustomerCashFlowRegistryDto.buildFromCashFlowRegistry(cashFlowRegistry);
        }
        return null;
    }

    @Override
    public CustomerCashFlowRegistryDto delete(String uuid) {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        CustomerCashFlowRegistry cashFlowRegistry = this.customerCashFlowRegistryRepository.findByUuid(uuid);
        if (customer != null && cashFlowRegistry != null) {
            if (!customer.getId().equals(cashFlowRegistry.getCustomer().getId())) {
                throw new RuntimeException("FORBIDDEN");
            }
            this.customerCashFlowRegistryRepository.delete(cashFlowRegistry);
            return CustomerCashFlowRegistryDto.buildFromCashFlowRegistry(cashFlowRegistry);
        }
        return null;
    }

    @Override
    public CustomerCashFlowRegistryDto findByUuid(String uuid) {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        CustomerCashFlowRegistry cashFlowRegistry = this.customerCashFlowRegistryRepository.findByUuid(uuid);
        if (customer != null && cashFlowRegistry != null) {
            if (!customer.getId().equals(cashFlowRegistry.getCustomer().getId())) {
                throw new RuntimeException("FORBIDDEN");
            }
            return CustomerCashFlowRegistryDto.buildFromCashFlowRegistry(cashFlowRegistry);
        }
        return null;
    }

    @Override
    public CustomerCashFlowRegistryDto update(String uuid, CustomerCashFlowRegistryDto dto) {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        CustomerCashFlowRegistry cashFlowRegistry = this.customerCashFlowRegistryRepository.findByUuid(uuid);
        if (customer != null && cashFlowRegistry != null) {
            if (!customer.getId().equals(cashFlowRegistry.getCustomer().getId())) {
                throw new RuntimeException("FORBIDDEN");
            }
            cashFlowRegistry.setAmount(dto.getAmount());
            cashFlowRegistry.setDate(dto.getDate());
            cashFlowRegistry.setType(dto.getType());
            cashFlowRegistry.setCashFlow(this.cashFlowService.findOrCreatedOrUpdateCashFlow(
                    dto.getName(),
                    dto.getCategory(),
                    dto.getType().equals(CashFlowType.GAIN),
                    dto.getType().equals(CashFlowType.EXPENSE)
            ));
            cashFlowRegistry = this.customerCashFlowRegistryRepository.save(cashFlowRegistry);
            return CustomerCashFlowRegistryDto.buildFromCashFlowRegistry(cashFlowRegistry);
        }
        return null;
    }
}
