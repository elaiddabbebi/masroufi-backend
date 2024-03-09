package com.masroufi.api.service.impl;

import com.masroufi.api.dto.response.ConsumptionEvolutionData;
import com.masroufi.api.dto.response.ExpenseRevenueEvolutionData;
import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.dto.response.MonthConsumptionData;
import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.AggregatedCustomerCashFlow;
import com.masroufi.api.entity.embeddable.CustomerCashState;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.enums.Month;
import com.masroufi.api.repository.AggregatedCustomerCashFlowRepository;
import com.masroufi.api.service.DashboardService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import com.masroufi.api.shared.helpers.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ApplicationSecurityContext applicationSecurityContext;

    @Autowired
    private AggregatedCustomerCashFlowRepository aggregatedCustomerCashFlowRepository;

    @Override
    public Double getCurrentCashAmount() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            CustomerCashState customerCashState = customer.getCustomerCashState();
            if (customerCashState != null) {
                return customerCashState.getCurrentCashAmount();
            } else {
                return 0D;
            }
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentWeekConsumption() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentWeekStartDate = DateHelper.getCurrentWeekStartDate();
            Date currentWeekEndDate = DateHelper.getCurrentWeekEndDate();
            return this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            currentWeekStartDate,
                            currentWeekEndDate
                    );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getLastWeekConsumption() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastWeekStartDate = DateHelper.getLastWeekStartDate();
            Date lastWeekEndDate = DateHelper.getLastWeekEndDate();
            return this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            lastWeekStartDate,
                            lastWeekEndDate
                    );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentWeekBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentWeekStartDate = DateHelper.getCurrentWeekStartDate();
            Date currentWeekEndDate = DateHelper.getCurrentWeekEndDate();
            return calculateBalanceByCustomerBetween(customer, currentWeekStartDate, currentWeekEndDate);
        } else {
            return 0D;
        }
    }

    @Override
    public Double getLastWeekBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastWeekStartDate = DateHelper.getLastWeekStartDate();
            Date lastWeekEndDate = DateHelper.getLastWeekEndDate();
            return calculateBalanceByCustomerBetween(customer, lastWeekStartDate, lastWeekEndDate);
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentMonthConsumption() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentMonthStartDate = DateHelper.getCurrentMonthStartDate();
            Date currentMonthEndDate = DateHelper.getCurrentMonthEndDate();
            return this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            currentMonthStartDate,
                            currentMonthEndDate
                    );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getLastMonthConsumption() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastMonthStartDate = DateHelper.getLastMonthStartDate();
            Date lastMonthEndDate = DateHelper.getLastMonthEndDate();
            return this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            lastMonthStartDate,
                            lastMonthEndDate
                    );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentMonthBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentMonthStartDate = DateHelper.getCurrentMonthStartDate();
            Date currentMonthEndDate = DateHelper.getCurrentMonthEndDate();
            return calculateBalanceByCustomerBetween(customer, currentMonthStartDate, currentMonthEndDate);
        } else {
            return 0D;
        }
    }

    @Override
    public Double getLastMonthBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastMonthStartDate = DateHelper.getLastMonthStartDate();
            Date lastMonthEndDate = DateHelper.getLastMonthEndDate();
            return calculateBalanceByCustomerBetween(customer, lastMonthStartDate, lastMonthEndDate);
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentYearRevenue() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentYearStartDate = DateHelper.getCurrentYearStartDate();
            Date currentYearEndDate = DateHelper.getCurrentYearEndDate();
            return this.aggregatedCustomerCashFlowRepository.calculateGainByCustomerBetween(
                    customer.getId(),
                    currentYearStartDate,
                    currentYearEndDate
            );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentYearBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentYearStartDate = DateHelper.getCurrentYearStartDate();
            Date currentYearEndDate = DateHelper.getCurrentYearEndDate();
            return calculateBalanceByCustomerBetween(customer, currentYearStartDate, currentYearEndDate);
        } else {
            return 0D;
        }
    }

    @Override
    public ConsumptionEvolutionData getConsumptionEvolutionData() {
        ConsumptionEvolutionData returnValue = new ConsumptionEvolutionData();
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastMonthStartDate = DateHelper.getLastMonthStartDate();
            Date lastMonthEndDate = DateHelper.getLastMonthEndDate();
            Date currentMonthStartDate = DateHelper.getCurrentMonthStartDate();
            Date currentMonthEndDate = DateHelper.getCurrentMonthEndDate();

            List<String> monthDays = new ArrayList<>();
            List<Integer> monthDaysInteger = new ArrayList<>();
            for (int i = 1; i <= Math.max(lastMonthEndDate.getDate(), currentMonthEndDate.getDate()); i++) {
                monthDaysInteger.add(i);
                String label = String.valueOf(i);
                if (i < 10) {
                    label = "0" + label;
                }
                monthDays.add(label);
            }
            returnValue.setDaysOfMonth(monthDays);

            List< AggregatedCustomerCashFlow> lastMonthData = this.aggregatedCustomerCashFlowRepository
                    .findAllByCustomerIdAndDateBetween(
                            customer.getId(),
                            lastMonthStartDate,
                            lastMonthEndDate
                    );

            List< AggregatedCustomerCashFlow> currentMonthData = this.aggregatedCustomerCashFlowRepository
                    .findAllByCustomerIdAndDateBetween(
                            customer.getId(),
                            currentMonthStartDate,
                            currentMonthEndDate
                    );

            MonthConsumptionData lastMonthConsumptionData = new MonthConsumptionData();
            lastMonthConsumptionData.setMonth(Month.of(lastMonthStartDate.getMonth()));
            List<Double> lastMonthCleanedData = new ArrayList<>();
            for (Integer day: monthDaysInteger) {
                if (lastMonthData != null && !lastMonthData.isEmpty()) {
                    List<AggregatedCustomerCashFlow> todayDataList = lastMonthData
                            .stream()
                            .filter(elt -> elt.getDate() != null && day.equals(elt.getDate().getDate()))
                            .collect(Collectors.toList());
                    if (!todayDataList.isEmpty()) {
                        lastMonthCleanedData.add(todayDataList.get(0).getExpenseAmount());
                    } else {
                        lastMonthCleanedData.add(0D);
                    }
                } else {
                    lastMonthCleanedData.add(0D);
                }
            }
            lastMonthConsumptionData.setData(lastMonthCleanedData);

            MonthConsumptionData currentMonthConsumptionData = new MonthConsumptionData();
            currentMonthConsumptionData.setMonth(Month.of(currentMonthStartDate.getMonth()));
            List<Double> currentMonthCleanedData = new ArrayList<>();
            for (Integer day: monthDaysInteger) {
                if (currentMonthData != null && !currentMonthData.isEmpty()) {
                    List<AggregatedCustomerCashFlow> todayDataList = currentMonthData
                            .stream()
                            .filter(elt -> elt.getDate() != null && day.equals(elt.getDate().getDate()))
                            .collect(Collectors.toList());
                    if (!todayDataList.isEmpty()) {
                        currentMonthCleanedData.add(todayDataList.get(0).getExpenseAmount());
                    } else {
                        currentMonthCleanedData.add(0D);
                    }
                } else {
                    currentMonthCleanedData.add(0D);
                }
            }

            for (int i = currentMonthCleanedData.size() - 1; i >= 0; i--) {
                if (currentMonthCleanedData.get(i).equals(0D)) {
                    currentMonthCleanedData.remove(i);
                } else {
                    break;
                }
            }
            currentMonthConsumptionData.setData(currentMonthCleanedData);

            returnValue.setLastMonthData(lastMonthConsumptionData);
            returnValue.setCurrentMonthData(currentMonthConsumptionData);

        }
        return returnValue;
    }

    @Override
    public List<MonthAmount> getCurrentYearRevenueEvolution() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            int year = DateHelper.getCurrentYear();
            return this.calculateFlowByCustomerAndYear(customer.getId(), year, CashFlowType.GAIN);
        } else {
            return null;
        }
    }

    @Override
    public List<MonthAmount> getCurrentYearExpenseEvolution() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            int year = DateHelper.getCurrentYear();
            return this.calculateFlowByCustomerAndYear(customer.getId(), year, CashFlowType.EXPENSE);
        } else {
            return null;
        }
    }

    @Override
    public ExpenseRevenueEvolutionData getCurrentYearExpenseRevenueEvolution() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            int year = DateHelper.getCurrentYear();
            List<MonthAmount> expenseEvolutionData = this.calculateFlowByCustomerAndYear(customer.getId(), year, CashFlowType.EXPENSE);
            List<MonthAmount> revenueEvolutionData = this.calculateFlowByCustomerAndYear(customer.getId(), year, CashFlowType.GAIN);
            List<Month> months = DateHelper.getMonthsOfYear();
            return ExpenseRevenueEvolutionData.builder()
                    .months(months)
                    .expenseEvolution(expenseEvolutionData)
                    .revenueEvolution(revenueEvolutionData)
                    .build();
        } else {
            return null;
        }
    }

    private Double calculateBalanceByCustomerBetween(Account customer, Date startDate, Date endDate) {
        Double expense = this.aggregatedCustomerCashFlowRepository
                .calculateExpenseByCustomerBetween(
                        customer.getId(),
                        startDate,
                        endDate
                );
        expense = expense != null ? expense : 0D;
        Double gain = this.aggregatedCustomerCashFlowRepository
                .calculateGainByCustomerBetween(
                        customer.getId(),
                        startDate,
                        endDate
                );
        gain = gain != null ? gain : 0D;
        return gain - expense;
    }

    private List<MonthAmount> calculateFlowByCustomerAndYear(Long customerId, int year, CashFlowType cashFlowType) {
        List<Month> months = DateHelper.getMonthsOfYear();
        List<MonthAmount> returnValue = new ArrayList<>();
        for (Month month: months) {
            MonthAmount monthAmount = MonthAmount.builder()
                    .month(month)
                    .amount(0D)
                    .build();
            returnValue.add(monthAmount);
        }
        List<Map<String, Object>> result;
        if (cashFlowType.equals(CashFlowType.EXPENSE)) {
            result = this.aggregatedCustomerCashFlowRepository.calculateExpenseByCustomerAndMonthAndYear(customerId, year);
        } else {
            result = this.aggregatedCustomerCashFlowRepository.calculateGainByCustomerAndMonthAndYear(customerId, year);
        }
        if (result != null && !result.isEmpty()) {
            for (Map<String, Object> monthAmount: result) {
                if (monthAmount != null) {
                    Month month = Month.of((Integer) monthAmount.get("month") - 1);
                    Double amount = (Double) monthAmount.get("amount");
                    for (MonthAmount mAmount: returnValue) {
                        if (mAmount.getMonth().equals(month)) {
                            mAmount.setAmount(amount);
                            break;
                        }
                    }
                }
            }
        }
        return returnValue;
    }
}
