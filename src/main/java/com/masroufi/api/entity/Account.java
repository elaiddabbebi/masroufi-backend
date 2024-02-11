package com.masroufi.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.masroufi.api.entity.embeddable.CashFlowConfig;
import com.masroufi.api.entity.embeddable.SubscriptionConfig;
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
@Table(name = "account")
public class Account extends BaseEntity implements Serializable {

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
    private SubscriptionConfig subscriptionConfig;

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
