//package com.wenzhi.leetcode_service.config;
//
//import com.wenzhi.leetcode_service.interceptor.SqlExecMoniInterceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
////import javax.annotation.PostConstruct;
//import jakarta.annotation.PostConstruct;
//import java.util.Properties;
//
//@Configuration
//public class MyBatisInterceptorConfig {
//
//    private final SqlSessionFactory sqlSessionFactory;
//
//    public MyBatisInterceptorConfig(SqlSessionFactory sqlSessionFactory) {
//        this.sqlSessionFactory = sqlSessionFactory;
//    }
//
//    @PostConstruct
//    public void addInterceptor() {
//        SqlExecMoniInterceptor interceptor = new SqlExecMoniInterceptor();
//
//        // 通过Properties配置参数（可选）
//        Properties properties = new Properties();
//        properties.setProperty("slowSqlThreshold", "500");
//        interceptor.setProperties(properties);
//
//        sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
//    }
//}