package com.wenzhi.leetcode_service.controller;


import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.entity.dto.RiskByIdDto;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.entity.message.Response;
import com.wenzhi.leetcode_service.service.RiskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class RiskControllerTest {

    @Mock
    private RiskService riskService;

    @InjectMocks
    private RiskController riskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRiskById() {
        // 创建 Mock 对象
        RiskService mockRiskService = Mockito.mock(RiskService.class);
        RiskController riskController = new RiskController(mockRiskService);

        // 准备测试数据
        RiskByIdDto dto = new RiskByIdDto();
        dto.setId(1L);
        Request<RiskByIdDto> request = new Request<>();
        request.setBody(dto);

        // 准备模拟的返回数据
        RiskEntity mockRiskEntity = new RiskEntity();
        mockRiskEntity.setId(1L);
        mockRiskEntity.setName("Test Risk");

        // 设置 Mock 方法的返回值
        when(mockRiskService.getRiskById(1L)).thenReturn(mockRiskEntity);

        // 调用控制器方法
        Response<RiskEntity> response = riskController.getRiskById(request);
        log.info("getRiskById response: {}", response.getData());

        // 验证结果
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(mockRiskEntity.getId(), response.getData().getId());
        assertEquals(mockRiskEntity.getName(), response.getData().getName());

        // 验证服务方法是否被调用
        verify(mockRiskService, times(1)).getRiskById(request.getBody().getId());

    }

    @Test
    public void testGetAllRisks() {
        RiskEntity risk1 = new RiskEntity();
        risk1.setId(1L);
        risk1.setName("Risk 1");

        RiskEntity risk2 = new RiskEntity();
        risk2.setId(2L);
        risk2.setName("Risk 2");

        List<RiskEntity> risks = Arrays.asList(risk1, risk2);

        when(riskService.getAllRisks()).thenReturn(risks);

        Response<List<RiskEntity>> result = riskController.getAllRisks();

        assertNotNull(result);
        assertEquals(2, result.getData().size());
        verify(riskService).getAllRisks();
    }

    @Test
    public void testCreateRisk() {
        // 创建 Mock 对象
        RiskService mockRiskService = Mockito.mock(RiskService.class);
        RiskController riskController = new RiskController(mockRiskService);

        // 准备测试数据
        RiskEntity risk = new RiskEntity();
        risk.setName("New Risk");
        Request<RiskEntity> request = new Request<>();
        request.setBody(risk);

        // 调用控制器方法
        Response<String> response = riskController.createRisk(request);

        // 验证结果
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("success", response.getMessage());

        // 验证服务方法是否被调用
        verify(mockRiskService, times(1)).createRisk(risk);
    }

    @Test
    public void testUpdateRisk() {
        // 创建 Mock 对象
        RiskService mockRiskService = Mockito.mock(RiskService.class);
        RiskController riskController = new RiskController(mockRiskService);
        // 准备测试数据
        RiskEntity risk = new RiskEntity();
        risk.setId(1L);
        risk.setName("Updated Risk");
        Request<RiskEntity> request = new Request<>();
        request.setBody(risk);

        Response<String> response = riskController.updateRisk(request);
        log.info("response: {}", response);
        // 验证结果
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals("success", response.getMessage());

        verify(mockRiskService, times(1)).updateRisk(risk);
    }

    @Test
    public void testDeleteRisk() {
        // 创建 Mock 对象
        RiskService mockRiskService = Mockito.mock(RiskService.class);
        RiskController riskController = new RiskController(mockRiskService);

        // 准备测试数据
        RiskByIdDto dto = new RiskByIdDto();
        dto.setId(1L);
        Request<RiskByIdDto> request = new Request<>();
        request.setBody(dto);

        // 调用控制器方法
        Response<String> response = riskController.deleteRisk(request);
        log.info("deleteRisk response: {}", response.getData());

        // 验证响应结果
        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals("删除成功", response.getData());

        // 验证服务方法是否被调用
        verify(mockRiskService, times(1)).deleteRisk(request.getBody().getId());
    }
}