package com.masroufi.api.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSheet {
    private String sheetName;
    private ExcelRow header;
    private List<ExcelRow> body;
    private ExcelRow footer;
}
