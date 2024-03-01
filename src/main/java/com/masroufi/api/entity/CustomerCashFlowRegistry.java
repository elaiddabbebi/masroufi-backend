package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.masroufi.api.enums.CashFlowType;
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
@Table(name = "customer_cash_flow_registry", indexes = {
        @Index(name = "idx_customer_cash_flow_registry_customer_id",  columnList="customer_id"),
        @Index(name = "idx_customer_cash_flow_registry_date",   columnList="date"),
})
public class CustomerCashFlowRegistry extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @OneToOne
    private Account customer;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private CashFlowType type;

    @ManyToOne
    private CashFlow cashFlow;
}
