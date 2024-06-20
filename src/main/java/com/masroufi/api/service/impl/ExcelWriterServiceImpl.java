package com.masroufi.api.service.impl;

import com.masroufi.api.dto.ExcelRow;
import com.masroufi.api.dto.ExcelSheet;
import com.masroufi.api.service.ExcelWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExcelWriterServiceImpl implements ExcelWriterService {

    @Override
    public byte[] writeIntoExcel(ExcelSheet inputExcelSheet) {
        try (Workbook workbook = new XSSFWorkbook()) {
            int globalRowCounter = 0;
            // Create a sheet
            Sheet sheet = workbook.createSheet(inputExcelSheet.getSheetName());

            // Create a header row
            ExcelRow header = inputExcelSheet.getHeader();
            globalRowCounter = writeExcelRowIntoSheetAtIndex(header, sheet, globalRowCounter);

            // Create body rows
            List<ExcelRow> body = inputExcelSheet.getBody();
            if (body != null && !body.isEmpty()) {
                for (ExcelRow excelRow : body) {
                    globalRowCounter = writeExcelRowIntoSheetAtIndex(excelRow, sheet, globalRowCounter);
                }
            }

            // Create a footer row
            ExcelRow footer = inputExcelSheet.getFooter();
            writeExcelRowIntoSheetAtIndex(footer, sheet, globalRowCounter);

            // Write the data to a byte array output stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
            return new byte[0];
        }
    }

    private int writeExcelRowIntoSheetAtIndex(ExcelRow excelRow, Sheet sheet, int rowCounter) {
        if (excelRow != null) {
            Row headerRow = sheet.createRow(rowCounter);
            List<String> cells = excelRow.getCells();
            if (cells != null && !cells.isEmpty()) {
                for (int i = 0; i < cells.size(); i++) {
                    Cell headerCell = headerRow.createCell(i);
                    headerCell.setCellValue(cells.get(i));
                }
            }
            rowCounter++;
        }
        return rowCounter;
    }
}
