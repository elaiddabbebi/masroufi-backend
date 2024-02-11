package com.masroufi.api.entity.embeddable;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionConfig {
    private Date startDate;
    private Date endDate;
}
