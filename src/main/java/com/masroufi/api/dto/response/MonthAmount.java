package com.masroufi.api.dto.response;

import com.masroufi.api.enums.Month;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthAmount {
    private Month month;
    private Double amount;
}
