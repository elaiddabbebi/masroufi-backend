package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.masroufi.api.entity.embeddable.CashFlowConfig;
import com.masroufi.api.entity.embeddable.CustomerCashState;
import com.masroufi.api.entity.embeddable.SubscriptionConfig;
import com.masroufi.api.enums.AppLocale;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Date birthDate;

    @Column
    private String phoneNumber;

    @Column
    private String confirmationCode;

    @Column
    private Boolean isActivated;

    @OneToOne
    private Role role;

    @Embedded
    private CashFlowConfig cashFlowConfig;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "subscription_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "subscription_end_date")),
    })
    private SubscriptionConfig subscriptionConfig;

    @Embedded
    private CustomerCashState customerCashState;

    @Enumerated(EnumType.STRING)
    private AppLocale locale;

    public String getFullName() {
        String fullName = firstName;
        if (fullName == null) {
            fullName = lastName;
        } else {
            if (lastName != null) {
                fullName += " " + lastName;
            }
        }
        return fullName;
    }
}
