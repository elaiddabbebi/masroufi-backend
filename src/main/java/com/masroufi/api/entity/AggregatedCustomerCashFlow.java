package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aggregated_customer_cash_flow", indexes = {
        @Index(name = "idx_aggregated_customer_cash_flow_customer_id",  columnList="customerId"),
        @Index(name = "idx_aggregated_customer_cash_flow_year",   columnList="year"),
        @Index(name = "idx_aggregated_customer_cash_flow_month",  columnList="month"),
        @Index(name = "idx_aggregated_customer_cash_flow_day",    columnList="day"),
})
public class AggregatedCustomerCashFlow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private int day;

    @Column
    private long gainAmount;

    @Column
    private long expenseAmount;

    @Column
    private Long customerId;
}
