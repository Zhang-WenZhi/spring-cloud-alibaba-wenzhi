package com.wenzhi.leetcode_service.controller;


import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.service.RiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        RiskEntity risk = new RiskEntity();
        risk.setId(1L);
        risk.setName("Test Risk");

        when(riskService.getRiskById(1L)).thenReturn(risk);

        RiskEntity result = riskController.getRiskById(1L);

        assertNotNull(result);
        assertEquals("Test Risk", result.getName());
        verify(riskService).getRiskById(1L);
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

        List<RiskEntity> result = riskController.getAllRisks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(riskService).getAllRisks();
    }

    @Test
    public void testCreateRisk() {
        RiskEntity risk = new RiskEntity();
        risk.setName("New Risk");

        riskController.createRisk(risk);

        verify(riskService).createRisk(risk);
    }

    @Test
    public void testUpdateRisk() {
        RiskEntity risk = new RiskEntity();
        risk.setId(1L);
        risk.setName("Updated Risk");

        riskController.updateRisk(risk);

        verify(riskService).updateRisk(risk);
    }

    @Test
    public void testDeleteRisk() {
        riskController.deleteRisk(1L);

        verify(riskService).deleteRisk(1L);
    }
}