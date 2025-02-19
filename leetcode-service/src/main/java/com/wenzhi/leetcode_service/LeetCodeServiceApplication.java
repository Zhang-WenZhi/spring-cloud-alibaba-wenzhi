package com.wenzhi.leetcode_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.wenzhi.leetcode_service" // 添加额外的扫描路径
})
public class LeetCodeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeetCodeServiceApplication.class, args);
    }
}

