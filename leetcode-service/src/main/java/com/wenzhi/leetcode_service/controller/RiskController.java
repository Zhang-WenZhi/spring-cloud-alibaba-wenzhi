package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.entity.dto.RiskByIdDto;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.entity.message.Response;
import com.wenzhi.leetcode_service.service.RiskService;
import jakarta.validation.Valid;
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

    @PostMapping("/getRiskById")
    public Response<RiskEntity> getRiskById(@RequestBody @Valid Request<RiskByIdDto> request) {
        log.info("入参:::{}", request.getBody());
        Long id = request.getBody().getId();
        return Response.success(riskService.getRiskById(id));
    }

    @GetMapping
    public Response<List<RiskEntity>> getAllRisks() {
        return Response.success(riskService.getAllRisks());
    }

    @PostMapping
    public Response<String> createRisk(@RequestBody Request<RiskEntity> request) {
        RiskEntity risk = request.getBody();
        riskService.createRisk(risk);
        return Response.success("创建成功");
    }

    @PutMapping
    public Response<String> updateRisk(@RequestBody Request<RiskEntity> request) {
        RiskEntity risk = request.getBody();
        riskService.updateRisk(risk);
        return Response.success("更新成功");
    }

    @DeleteMapping("/deleteRisk")
    public Response<String> deleteRisk(@RequestBody @Valid Request<RiskByIdDto> request) {
        log.info("deleteRisk 入参:::{}", request.getBody());
        Long id = request.getBody().getId();
        riskService.deleteRisk(id);
        return Response.success("删除成功");
    }
}