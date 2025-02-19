package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.entity.RiskEntity;

import java.util.List;

public interface RiskService {
    RiskEntity getRiskById(Long id);
    List<RiskEntity> getAllRisks();
    void createRisk(RiskEntity risk);
    void updateRisk(RiskEntity risk);
    void deleteRisk(Long id);
}
