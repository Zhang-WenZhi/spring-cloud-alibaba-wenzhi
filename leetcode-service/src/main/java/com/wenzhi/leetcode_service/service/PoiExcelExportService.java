package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.entity.RiskEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

//import javax.servlet.http.HttpServletResponse; // 老版本spring boot集成使用
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PoiExcelExportService {
    public void exportRiskToExcel(List<RiskEntity> riskEntities, HttpServletResponse response) throws IOException {
        // 创建工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Risks");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Description"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 填充数据
        int rowNum = 1;
        for (RiskEntity risk : riskEntities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(risk.getId());
            row.createCell(1).setCellValue(risk.getName());
            row.createCell(2).setCellValue(risk.getDescription());
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=risks.xlsx");

        // 输出 Excel 文件
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();
    }
}

