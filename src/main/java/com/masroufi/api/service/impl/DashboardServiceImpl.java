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
import com.masroufi.api.repository.StatisticsRepository;
import com.masroufi.api.service.DashboardService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import com.masroufi.api.shared.utils.DateUtils;
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

    @Autowired
    private StatisticsRepository statisticsRepository;

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
            Date currentWeekStartDate = DateUtils.getCurrentWeekStartDate();
            Date currentWeekEndDate = DateUtils.getCurrentWeekEndDate();
            return this.statisticsRepository.getExpenseByCustomerAndDateBetween(
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
            Date lastWeekStartDate = DateUtils.getLastWeekStartDate();
            Date lastWeekEndDate = DateUtils.getLastWeekEndDate();
            return this.statisticsRepository.getExpenseByCustomerAndDateBetween(
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
            Date currentWeekStartDate = DateUtils.getCurrentWeekStartDate();
            Date currentWeekEndDate = DateUtils.getCurrentWeekEndDate();
            return this.statisticsRepository.getBalanceByCustomerAndDateBetween(
                    customer.getId(),
                    currentWeekStartDate,
                    currentWeekEndDate
            );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getLastWeekBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastWeekStartDate = DateUtils.getLastWeekStartDate();
            Date lastWeekEndDate = DateUtils.getLastWeekEndDate();
            return this.statisticsRepository.getBalanceByCustomerAndDateBetween(
                    customer.getId(),
                    lastWeekStartDate,
                    lastWeekEndDate);
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentMonthConsumption() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentMonthStartDate = DateUtils.getCurrentMonthStartDate();
            Date currentMonthEndDate = DateUtils.getCurrentMonthEndDate();
            return this.statisticsRepository.getExpenseByCustomerAndDateBetween(
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
            Date lastMonthStartDate = DateUtils.getLastMonthStartDate();
            Date lastMonthEndDate = DateUtils.getLastMonthEndDate();
            return this.statisticsRepository.getExpenseByCustomerAndDateBetween(
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
            Date currentMonthStartDate = DateUtils.getCurrentMonthStartDate();
            Date currentMonthEndDate = DateUtils.getCurrentMonthEndDate();
            return this.statisticsRepository.getBalanceByCustomerAndDateBetween(
                    customer.getId(),
                    currentMonthStartDate,
                    currentMonthEndDate
            );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getLastMonthBalance() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date lastMonthStartDate = DateUtils.getLastMonthStartDate();
            Date lastMonthEndDate = DateUtils.getLastMonthEndDate();
            return this.statisticsRepository.getBalanceByCustomerAndDateBetween(
                    customer.getId(),
                    lastMonthStartDate,
                    lastMonthEndDate
            );
        } else {
            return 0D;
        }
    }

    @Override
    public Double getCurrentYearRevenue() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            Date currentYearStartDate = DateUtils.getCurrentYearStartDate();
            Date currentYearEndDate = DateUtils.getCurrentYearEndDate();
            return this.statisticsRepository.getRevenueByCustomerAndDateBetween(
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
            Date currentYearStartDate = DateUtils.getCurrentYearStartDate();
            Date currentYearEndDate = DateUtils.getCurrentYearEndDate();
            return this.statisticsRepository.getBalanceByCustomerAndDateBetween(
                    customer.getId(),
                    currentYearStartDate,
                    currentYearEndDate
            );
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
            Date lastMonthStartDate = DateUtils.getLastMonthStartDate();
            Date lastMonthEndDate = DateUtils.getLastMonthEndDate();
            Date currentMonthStartDate = DateUtils.getCurrentMonthStartDate();
            Date currentMonthEndDate = DateUtils.getCurrentMonthEndDate();

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

            List<AggregatedCustomerCashFlow> lastMonthData = this.aggregatedCustomerCashFlowRepository
                    .findAllByCustomerIdAndDateBetweenOrderByDate(
                            customer.getId(),
                            lastMonthStartDate,
                            lastMonthEndDate
                    );

            List<AggregatedCustomerCashFlow> currentMonthData = this.aggregatedCustomerCashFlowRepository
                    .findAllByCustomerIdAndDateBetweenOrderByDate(
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
            int year = DateUtils.getCurrentYear();
            return this.statisticsRepository.getRevenuesPerMonthByCustomerAndYear(customer.getId(), year);
        } else {
            return null;
        }
    }

    @Override
    public List<MonthAmount> getCurrentYearExpenseEvolution() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            int year = DateUtils.getCurrentYear();
            return this.statisticsRepository.getExpensesPerMonthByCustomerAndYear(customer.getId(), year);
        } else {
            return null;
        }
    }

    @Override
    public ExpenseRevenueEvolutionData getCurrentYearExpenseRevenueEvolution() {
        this.applicationSecurityContext.isCustomerOrThrowException();
        Account customer = this.applicationSecurityContext.getCurrentUser();
        if (customer != null) {
            int year = DateUtils.getCurrentYear();
            List<MonthAmount> expenseEvolutionData = this.statisticsRepository.getExpensesPerMonthByCustomerAndYear(customer.getId(), year);
            List<MonthAmount> revenueEvolutionData = this.statisticsRepository.getRevenuesPerMonthByCustomerAndYear(customer.getId(), year);
            List<Month> months = DateUtils.getMonthsOfYear();
            return ExpenseRevenueEvolutionData.builder()
                    .months(months)
                    .expenseEvolution(expenseEvolutionData)
                    .revenueEvolution(revenueEvolutionData)
                    .build();
        } else {
            return null;
        }
    }
}
