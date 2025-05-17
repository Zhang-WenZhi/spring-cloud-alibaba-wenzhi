package com.wenzhi.leetcode_service.config;

import com.wenzhi.leetcode_service.interceptor.SqlPerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置类
 */
@Configuration
@MapperScan("com.wenzhi.leetcode_service.dao") // 指定Mapper接口所在的包
public class MyBatisConfig {

    /**
     * 注册 SQL 性能拦截器
     */
    @Bean
    public SqlPerformanceInterceptor sqlPerformanceInterceptor() {
        return new SqlPerformanceInterceptor();
    }
}