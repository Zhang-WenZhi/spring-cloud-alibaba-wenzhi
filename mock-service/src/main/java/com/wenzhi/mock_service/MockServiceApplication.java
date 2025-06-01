package com.wenzhi.mock_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;

//import com.wenzhi.mock_service.config.MockServerConfig;

@SpringBootApplication
// @SpringBootApplication(scanBasePackages = "com.wenzhi.mock_service") // 明确扫描路径
//@EnableConfigurationProperties(MockServerConfig.class) // 和@Configuration注解冲突了，所以注释掉
public class MockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockServiceApplication.class, args);
    }
}
