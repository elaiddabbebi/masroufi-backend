package com.masroufi.api.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultSetResponse<T> {
    private int page;
    private int size;
    private int total;
    private List<T> result;
}
