package com.wenzhi.leetcode_service.service.impl;


import com.wenzhi.leetcode_service.dao.RiskDao;
import com.wenzhi.leetcode_service.entity.RiskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RiskServiceImplTest {

    @Mock
    private RiskDao riskDao; // 模拟 RiskDao

    @InjectMocks
    private RiskServiceImpl riskService; // 注入 RiskServiceImpl

    @BeforeEach
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRiskById() {
        // 1. 准备测试数据
        RiskEntity risk = new RiskEntity();
        risk.setId(1L);
        risk.setName("Test Risk");
        risk.setDescription("This is a test risk");

        // 2. 定义 Mock 行为
        when(riskDao.findById(1L)).thenReturn(risk);

        // 3. 调用被测方法
        RiskEntity result = riskService.getRiskById(1L);

        // 4. 验证结果
        assertNotNull(result);
        assertEquals("Test Risk", result.getName());
        assertEquals("This is a test risk", result.getDescription());

        // 5. 验证依赖的交互行为
        verify(riskDao).findById(1L);
    }

    @Test
    public void testGetAllRisks() {
        // 1. 准备测试数据
        RiskEntity risk1 = new RiskEntity();
        risk1.setId(1L);
        risk1.setName("Risk 1");

        RiskEntity risk2 = new RiskEntity();
        risk2.setId(2L);
        risk2.setName("Risk 2");

        List<RiskEntity> risks = Arrays.asList(risk1, risk2);

        // 2. 定义 Mock 行为
        when(riskDao.findAll()).thenReturn(risks);

        // 3. 调用被测方法
        List<RiskEntity> result = riskService.getAllRisks();

        // 4. 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Risk 1", result.get(0).getName());
        assertEquals("Risk 2", result.get(1).getName());

        // 5. 验证依赖的交互行为
        verify(riskDao).findAll();
    }

    @Test
    public void testCreateRisk() {
        // 1. 准备测试数据
        RiskEntity risk = new RiskEntity();
        risk.setName("New Risk");
        risk.setDescription("This is a new risk");

        // 2. 调用被测方法
        riskService.createRisk(risk);

        // 3. 验证依赖的交互行为
        verify(riskDao).save(risk);
    }

    @Test
    public void testUpdateRisk() {
        // 1. 准备测试数据
        RiskEntity risk = new RiskEntity();
        risk.setId(1L);
        risk.setName("Updated Risk");
        risk.setDescription("This is an updated risk");

        // 2. 调用被测方法
        riskService.updateRisk(risk);

        // 3. 验证依赖的交互行为
        verify(riskDao).update(risk);
    }

    @Test
    public void testDeleteRisk() {
        // 1. 调用被测方法
        riskService.deleteRisk(1L);

        // 2. 验证依赖的交互行为
        verify(riskDao).delete(1L);
    }
}