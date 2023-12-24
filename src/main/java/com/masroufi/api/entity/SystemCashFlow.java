package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.enums.SystemCashFlowStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_cash_flow")
public class SystemCashFlow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    private String name;

    private CashFlowType type;

    @OneToOne
    private CashFlowCategory category;

    @Enumerated(EnumType.STRING)
    private SystemCashFlowStatus status;
}
