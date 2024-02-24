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
}
