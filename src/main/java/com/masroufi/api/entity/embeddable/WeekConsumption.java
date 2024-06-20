package com.masroufi.api.entity.embeddable;

import lombok.*;

import jakarta.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeekConsumption {
    private Double consumption;
    private Date weekStartDay;
}
