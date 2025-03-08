package com.wenzhi.leetcode_service.controller;
import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.entity.dto.RiskByIdDto;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.service.PoiExcelExportService;
import com.wenzhi.leetcode_service.service.RiskService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import com.wenzhi.leetcode_service.entity.message.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@Slf4j
public class PoiExcelExportControllerTest {
    @Test
    public void testExportRiskById() throws IOException {
        // 创建 Mock 对象
        RiskService mockRiskService = Mockito.mock(RiskService.class);
        PoiExcelExportService mockExcelExportService = Mockito.mock(PoiExcelExportService.class);
        PoiExcelExportController riskController = new PoiExcelExportController(mockRiskService, mockExcelExportService);

        // 准备测试数据
        RiskByIdDto dto = new RiskByIdDto();
        dto.setId(1L);
        Request<RiskByIdDto> request = new Request<>();
        request.setBody(dto);

        RiskEntity mockRisk = new RiskEntity(1L, "Test Risk", "Test Description");
        when(mockRiskService.getRiskById(1L)).thenReturn(mockRisk);

        HttpServletResponse mockResponse = new MockHttpServletResponse();

        // 调用控制器方法
        riskController.exportRiskById(request, mockResponse);

        // 验证服务方法是否被调用
        verify(mockRiskService, times(1)).getRiskById(1L);
        verify(mockExcelExportService, times(1)).exportRiskToExcel(Collections.singletonList(mockRisk), mockResponse);
    }

}
