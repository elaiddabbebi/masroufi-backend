package com.masroufi.api.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelRow {
    private List<String> cells;
}
