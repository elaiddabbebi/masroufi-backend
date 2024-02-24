package com.masroufi.api.service.impl;

import com.masroufi.api.entity.Account;
import com.masroufi.api.entity.embeddable.CustomerCashState;
import com.masroufi.api.repository.AggregatedCustomerCashFlowRepository;
import com.masroufi.api.service.DashboardService;
import com.masroufi.api.shared.context.ApplicationSecurityContext;
import com.masroufi.api.shared.helpers.MyDateHelper;
import com.masroufi.api.shared.types.MyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            MyDate currentWeekStartDate = MyDateHelper.getCurrentWeekStartDate();
            MyDate currentWeekEndDate = MyDateHelper.getCurrentWeekEndDate();
            return this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            currentWeekStartDate.getYear(),
                            currentWeekStartDate.getMonth(),
                            currentWeekStartDate.getDay(),
                            currentWeekEndDate.getYear(),
                            currentWeekEndDate.getMonth(),
                            currentWeekEndDate.getDay()
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
            MyDate lastWeekStartDate = MyDateHelper.getLastWeekStartDate();
            MyDate lastWeekEndDate = MyDateHelper.getLastWeekEndDate();
            return this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            lastWeekStartDate.getYear(),
                            lastWeekStartDate.getMonth(),
                            lastWeekStartDate.getDay(),
                            lastWeekEndDate.getYear(),
                            lastWeekEndDate.getMonth(),
                            lastWeekEndDate.getDay()
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
            MyDate lastMonthStartDate = MyDateHelper.getLastMonthStartDate();
            MyDate lastMonthEndDate = MyDateHelper.getLastMonthEndDate();
            Double expense = this.aggregatedCustomerCashFlowRepository
                    .calculateExpenseByCustomerBetween(
                            customer.getId(),
                            lastMonthStartDate.getYear(),
                            lastMonthStartDate.getMonth(),
                            lastMonthStartDate.getDay(),
                            lastMonthEndDate.getYear(),
                            lastMonthEndDate.getMonth(),
                            lastMonthEndDate.getDay()
                    );
            expense = expense != null ? expense : 0D;
            Double gain = this.aggregatedCustomerCashFlowRepository
                    .calculateGainByCustomerBetween(
                            customer.getId(),
                            lastMonthStartDate.getYear(),
                            lastMonthStartDate.getMonth(),
                            lastMonthStartDate.getDay(),
                            lastMonthEndDate.getYear(),
                            lastMonthEndDate.getMonth(),
                            lastMonthEndDate.getDay()
                    );
            gain = gain != null ? gain : 0D;
            return gain - expense;
        } else {
            return 0D;
        }
    }
}
