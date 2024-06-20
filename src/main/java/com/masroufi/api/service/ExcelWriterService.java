package com.masroufi.api.service;

import com.masroufi.api.dto.ExcelSheet;

public interface ExcelWriterService {
    byte[] writeIntoExcel(ExcelSheet sheet);
}
