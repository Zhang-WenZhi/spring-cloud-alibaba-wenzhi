package com.wenzhi.leetcode_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// @MapperScan("com.wenzhi.leetcode_service.dao") // 扫描 MyBatis Mapper
@SpringBootApplication
@EnableScheduling // 启用定时任务支持
public class LeetCodeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeetCodeServiceApplication.class, args);
    }
}

