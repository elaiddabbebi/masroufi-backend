package com.masroufi.api.entity.embeddable;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCashState {

    private Double currentCashAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "consumption", column = @Column(name = "current_week_consumption")),
            @AttributeOverride( name = "weekStartDay", column = @Column(name = "current_week_start_day")),
    })
    private WeekConsumption currentWeekConsumption;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "consumption", column = @Column(name = "last_week_consumption")),
            @AttributeOverride( name = "weekStartDay", column = @Column(name = "last_week_start_day")),
    })
    private WeekConsumption lastWeekConsumption;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "balance", column = @Column(name = "last_month_balance")),
            @AttributeOverride( name = "year", column = @Column(name = "last_month_year")),
            @AttributeOverride( name = "month", column = @Column(name = "last_month_month")),
    })
    private MonthBalance lastMonthBalance;
}
