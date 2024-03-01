package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aggregated_customer_cash_flow", indexes = {
        @Index(name = "idx_aggregated_customer_cash_flow_customer_id",  columnList="customerId"),
        @Index(name = "idx_aggregated_customer_cash_flow_date",   columnList="date"),
})
public class AggregatedCustomerCashFlow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Column
    private double gainAmount;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private double expenseAmount;

    @Column
    private Long customerId;
}
