package com.masroufi.api.service.impl;

import com.masroufi.api.dto.CustomerCashFlowRegistryDto;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.AggregatedCustomerCashFlow;
import com.masroufi.api.entity.CustomerCashFlowRegistry;
import com.masroufi.api.entity.embeddable.CustomerCashState;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.enums.TransactionType;
import com.masroufi.api.repository.AccountRepository;
import com.masroufi.api.repository.AggregatedCustomerCashFlowRepository;
import com.masroufi.api.repository.CustomerCashFlowRegistryRepository;
import com.masroufi.api.service.AccountConfigurationService;
import com.masroufi.api.service.CashFlowService;
import com.masroufi.api.service.CustomerCashFlowRegistryService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerCashFlowRegistryServiceImpl implements CustomerCashFlowRegistryService {

    @Autowired
    private CustomerCashFlowRegistryRepository customerCashFlowRegistryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CashFlowService cashFlowService;

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private AccountConfigurationService accountConfigurationService;

    @Autowired
    private AggregatedCustomerCashFlowRepository aggregatedCustomerCashFlowRepository;

    @Override
    public List<CustomerCashFlowRegistryDto> findAll() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            List<CustomerCashFlowRegistry> customerCashFlowRegistryList = this.customerCashFlowRegistryRepository.findAllByCustomerOrderByDateDescIdDesc(customer);
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
            this.processCustomerCashFlowTransaction(cashFlowRegistry);
            this.processCustomerCashStateTransaction(cashFlowRegistry);
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
            this.reverseCustomerCashFlowTransaction(cashFlowRegistry);
            this.reverseCustomerCashStateTransaction(cashFlowRegistry);
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
            this.reverseCustomerCashFlowTransaction(cashFlowRegistry);
            this.reverseCustomerCashStateTransaction(cashFlowRegistry);
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
            this.processCustomerCashFlowTransaction(cashFlowRegistry);
            this.processCustomerCashStateTransaction(cashFlowRegistry);
            return CustomerCashFlowRegistryDto.buildFromCashFlowRegistry(cashFlowRegistry);
        }
        return null;
    }

    @Override
    public Double calculateCurrentCashAmountOfCustomer(Account customer) {
        Double initialCashAmount = this.accountConfigurationService.getInitialCashAmountOf(customer);
        if (initialCashAmount == null) {
            initialCashAmount = 0D;
        }
        Double totalIncome = this.customerCashFlowRegistryRepository.calculateCustomerIncome(customer);
        if (totalIncome == null) {
            totalIncome = 0D;
        }
        Double totalExpense = this.customerCashFlowRegistryRepository.calculateCustomerExpense(customer);
        if (totalExpense == null) {
            totalExpense = 0D;
        }
        return initialCashAmount + totalIncome - totalExpense;
    }

    private void updateAggregatedCashFlowFrom(CustomerCashFlowRegistry cashFlow, TransactionType transactionType) {
        if (cashFlow != null) {
            Date date = cashFlow.getDate();
            Account customer = cashFlow.getCustomer();
            if (date != null && customer != null) {
                AggregatedCustomerCashFlow customerCashFlow;
                List<AggregatedCustomerCashFlow> aggregatedcashFlowList =
                        this.aggregatedCustomerCashFlowRepository.findByCustomerIdAndYearAndMonthAndDay(
                                customer.getId(),
                                date.getYear(),
                                date.getMonth(),
                                date.getDate()
                        );
                if (aggregatedcashFlowList != null && !aggregatedcashFlowList.isEmpty()) {
                    customerCashFlow = aggregatedcashFlowList.get(0);
                } else {
                    customerCashFlow = new AggregatedCustomerCashFlow();
                    customerCashFlow.setCustomerId(customer.getId());
                    customerCashFlow.setDay(date.getDate());
                    customerCashFlow.setMonth(date.getMonth());
                    customerCashFlow.setYear(date.getYear());
                }

                if (cashFlow.getType().equals(CashFlowType.EXPENSE)) {
                    double newExpenseAmount = getNewAmount(customerCashFlow.getExpenseAmount(), cashFlow.getAmount(), transactionType);
                    customerCashFlow.setExpenseAmount(newExpenseAmount);
                } else if (cashFlow.getType().equals(CashFlowType.GAIN)) {
                    double newGainAmount = getNewAmount(customerCashFlow.getGainAmount(), cashFlow.getAmount(), transactionType);
                    customerCashFlow.setGainAmount(newGainAmount);
                }

                this.aggregatedCustomerCashFlowRepository.save(customerCashFlow);
            }
        }
    }

    private static double getNewAmount(double customerCashFlow, Double cashFlowAmount, TransactionType transactionType) {
        double safeCashFlowAmount = cashFlowAmount != null ? cashFlowAmount : 0;
        double newAmount = 0;
        if (transactionType.equals(TransactionType.PROCESS)) {
            newAmount = customerCashFlow + safeCashFlowAmount;
        } else if (transactionType.equals(TransactionType.REVERSE)) {
            newAmount = customerCashFlow - safeCashFlowAmount;
        }
        return newAmount;
    }

    @Override
    public void processCustomerCashFlowTransaction(CustomerCashFlowRegistry cashFlow) {
        this.updateAggregatedCashFlowFrom(cashFlow, TransactionType.PROCESS);
    }

    @Override
    public void reverseCustomerCashFlowTransaction(CustomerCashFlowRegistry cashFlow) {
        this.updateAggregatedCashFlowFrom(cashFlow, TransactionType.REVERSE);
    }

    private static double getNextCashAmount(Double currentCashAmount, Double cashFlowAmount, CashFlowType cashFlowType, TransactionType transactionType) {
        double safeCurrentCashAmount = currentCashAmount != null ? currentCashAmount : 0;
        double safeCashFlowAmount = cashFlowAmount != null ? cashFlowAmount : 0;
        double returnValue = 0;

        if (transactionType.equals(TransactionType.PROCESS)) {
            if (cashFlowType.equals(CashFlowType.EXPENSE)) {
                returnValue = safeCurrentCashAmount - safeCashFlowAmount;
            } else if (cashFlowType.equals(CashFlowType.GAIN)) {
                returnValue = safeCurrentCashAmount + safeCashFlowAmount;
            }
        } else if (transactionType.equals(TransactionType.REVERSE)) {
            if (cashFlowType.equals(CashFlowType.EXPENSE)) {
                returnValue = safeCurrentCashAmount + safeCashFlowAmount;
            } else if (cashFlowType.equals(CashFlowType.GAIN)) {
                returnValue = safeCurrentCashAmount - safeCashFlowAmount;
            }
        }

        return returnValue;
    }

    @Override
    public void processCustomerCashStateTransaction(CustomerCashFlowRegistry cashFlow) {
        if (cashFlow != null) {
            Date date = cashFlow.getDate();
            Account customer = cashFlow.getCustomer();
            if (date != null && customer != null) {
                CustomerCashState customerCashState = customer.getCustomerCashState();
                if (customerCashState == null) {
                    customerCashState = new CustomerCashState();
                }
                customerCashState.setCurrentCashAmount(
                        getNextCashAmount(
                                customerCashState.getCurrentCashAmount(),
                                cashFlow.getAmount(),
                                cashFlow.getType(),
                                TransactionType.PROCESS
                        )
                );
                customer.setCustomerCashState(customerCashState);
                this.accountRepository.save(customer);
            }
        }
    }

    @Override
    public void reverseCustomerCashStateTransaction(CustomerCashFlowRegistry cashFlow) {
        if (cashFlow != null) {
            Date date = cashFlow.getDate();
            Account customer = cashFlow.getCustomer();
            if (date != null && customer != null) {
                CustomerCashState customerCashState = customer.getCustomerCashState();
                if (customerCashState == null) {
                    customerCashState = new CustomerCashState();
                }
                customerCashState.setCurrentCashAmount(
                        getNextCashAmount(
                                customerCashState.getCurrentCashAmount(),
                                cashFlow.getAmount(),
                                cashFlow.getType(),
                                TransactionType.REVERSE
                        )
                );
                customer.setCustomerCashState(customerCashState);
                this.accountRepository.save(customer);
            }
        }
    }
}
