package com.masroufi.api.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultSetResponse<T> {
    private int count;
    private int page;
    private int total;
    private List<T> result;
}
