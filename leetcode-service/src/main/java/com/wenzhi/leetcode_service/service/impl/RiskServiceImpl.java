package com.wenzhi.leetcode_service.service.impl;


import com.wenzhi.leetcode_service.dao.RiskDao;
import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskServiceImpl implements RiskService {

    private final RiskDao riskDao;

    @Autowired
    public RiskServiceImpl(RiskDao riskDao) {
        this.riskDao = riskDao;
    }

    @Override
    public RiskEntity getRiskById(Long id) {
        return riskDao.findById(id);
    }

    @Override
    public List<RiskEntity> getAllRisks() {
        return riskDao.findAll();
    }

    @Override
    public void createRisk(RiskEntity risk) {
        riskDao.save(risk);
    }

    @Override
    public void updateRisk(RiskEntity risk) {
        riskDao.update(risk);
    }

    @Override
    public void deleteRisk(Long id) {
        riskDao.delete(id);
    }
}