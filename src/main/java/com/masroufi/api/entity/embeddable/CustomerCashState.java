package com.masroufi.api.entity.embeddable;

import lombok.*;

import jakarta.persistence.*;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCashState {

    private Double currentCashAmount;
}
