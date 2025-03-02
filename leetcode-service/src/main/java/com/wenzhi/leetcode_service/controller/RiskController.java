package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.service.RiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/risks")
public class RiskController {

    private final RiskService riskService;

    @Autowired
    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/{id}")
    public RiskEntity getRiskById(@PathVariable Long id) {
        log.info("入参:::{}", id);
        return riskService.getRiskById(id);
    }

    @GetMapping
    public List<RiskEntity> getAllRisks() {
        return riskService.getAllRisks();
    }

    @PostMapping
    public void createRisk(@RequestBody RiskEntity risk) {
        riskService.createRisk(risk);
    }

    @PutMapping
    public void updateRisk(@RequestBody RiskEntity risk) {
        riskService.updateRisk(risk);
    }

    @DeleteMapping("/{id}")
    public void deleteRisk(@PathVariable Long id) {
        riskService.deleteRisk(id);
    }
}