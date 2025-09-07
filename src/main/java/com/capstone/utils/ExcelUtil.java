package com.capstone.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelUtil {
    private Workbook workbook;

    /**
     * Load Excel from file path
     */
    public ExcelUtil(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + filePath, e);
        }
    }

    /**
     * Load Excel from InputStream (recommended for resources)
     */
    public ExcelUtil(InputStream inputStream) {
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel InputStream", e);
        }
    }

    /**
     * Reads all rows from a given sheet into a List of Maps
     */
    public List<Map<String, String>> getSheetData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new RuntimeException("Header row missing in " + sheetName);
        }

        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue().trim());
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Map<String, String> rowData = new LinkedHashMap<>();
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = row.getCell(j);
                String value = "";
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                    value = cell.getStringCellValue().trim();
                }
                rowData.put(headers.get(j), value);
            }
            data.add(rowData);
        }
        return data;
    }
}
