package com.masroufi.api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GenericObject {
    private String key;
    private Object value;
}
