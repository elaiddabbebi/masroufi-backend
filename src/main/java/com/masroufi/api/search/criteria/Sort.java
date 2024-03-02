package com.masroufi.api.search.criteria;

import com.masroufi.api.enums.SortOrder;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sort {
    private String field;
    private SortOrder order;
}
