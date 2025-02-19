package com.wenzhi.leetcode_service.dao;

import com.wenzhi.leetcode_service.entity.RiskEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RiskDao {
    RiskEntity findById(Long id);
    List<RiskEntity> findAll();
    void save(RiskEntity risk);
    void update(RiskEntity risk);
    void delete(Long id);
}