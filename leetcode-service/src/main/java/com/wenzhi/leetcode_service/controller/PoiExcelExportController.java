package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.entity.dto.RiskByIdDto;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.entity.message.Response;
import com.wenzhi.leetcode_service.service.PoiExcelExportService;
import com.wenzhi.leetcode_service.service.RiskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/excelExport")
public class PoiExcelExportController {
    private final RiskService riskService;

    private final PoiExcelExportService excelExportService;

    @Autowired
    public PoiExcelExportController(RiskService riskService, PoiExcelExportService excelExportService) {
        this.riskService = riskService;
        this.excelExportService = excelExportService;
    }

    @PostMapping("/exportRiskById")
    public void exportRiskById(@RequestBody Request<RiskByIdDto> request, HttpServletResponse response) throws IOException {
        Long id = request.getBody().getId();
        RiskEntity risk = riskService.getRiskById(id);
        // 将单个风险实体转换为列表
        excelExportService.exportRiskToExcel(Collections.singletonList(risk), response);
    }
}

