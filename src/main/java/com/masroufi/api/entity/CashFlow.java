package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.masroufi.api.enums.CashFlowStatus;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_flow")
public class CashFlow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    private String name;

    private boolean gain;

    private boolean expense;

    @ManyToOne
    private CashFlowCategory category;

    @Enumerated(EnumType.STRING)
    private CashFlowStatus status;

    private boolean published;
}
