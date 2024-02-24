package com.masroufi.api.entity.embeddable;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthBalance {

    private Double balance;
    private Integer year;
    private Integer month;
}
