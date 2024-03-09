package com.masroufi.api.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericType {
    private String key;
    private Object value;
}
