package com.masroufi.api.dto;

import com.masroufi.api.entity.embeddable.SubscriptionConfig;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionConfigDto {
    private Date startDate;
    private Date endDate;

    public static SubscriptionConfig buildFromSubscriptionConfig(SubscriptionConfig config) {
        if (config != null) {
            return SubscriptionConfig.builder()
                    .startDate(config.getStartDate())
                    .endDate(config.getEndDate())
                    .build();
        } else {
            return null;
        }
    }
}
