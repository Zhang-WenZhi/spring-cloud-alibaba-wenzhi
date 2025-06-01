package com.wenzhi.mock_service.config;

import com.wenzhi.mock_service.interceptor.EnhancedMockInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "mock")
// 1. MockServerConfig配置类（添加@Primary
// @Primary // 解决Bean冲突 告诉 Spring 优先使用该 Bean
public class MockServerConfig {
    final static Logger logger = LoggerFactory.getLogger(MockServerConfig.class);

    // 重要：初始化 Map，避免 null
    private Map<String, MockRule> rules = new HashMap<>();
    private final ResourceLoader resourceLoader;


    public MockServerConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Map<String, MockRule> getRules() {
        return rules;
    }

    public void setRules(Map<String, MockRule> rules) {
        this.rules = rules;
    }

    public static class MockRule {
        private String file;
        private Map<String, String> headers = new HashMap<>();
        private final ResourceLoader resourceLoader;
        private String content;

        // 必须提供默认构造器（供 Spring 反射创建）
        public MockRule(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        public String readFileContent() throws IOException {
            if (content == null) {
                Resource resource = resourceLoader.getResource("classpath:" + file);
                content = new String(Objects.requireNonNull(resource.getInputStream()).readAllBytes(), StandardCharsets.UTF_8);
            }
            logger.info("MockRule content: {}", content);
            return content;
        }

        // Getter and Setter
        public String getFile() { return file; }
        public void setFile(String file) { this.file = file; }
        public Map<String, String> getHeaders() { return headers; }
        public void setHeaders(Map<String, String> headers) { this.headers = headers; }
    }
}

/*import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mock")
public class MockServerConfig {
    private Map<String, MockRule> rules = new HashMap<>();

    public Map<String, MockRule> getRules() {
        return rules;
    }

    public void setRules(Map<String, MockRule> rules) {
        this.rules = rules;
    }

    public static class MockRule {
        private String method; // 对应请求中的method字段
        private String file;   // JSON文件路径
        private Map<String, String> headers = new HashMap<>(); // 响应头

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }
}*/

// 配置文件mapping:
/*import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mock")
public class MockServerConfig {
    private Map<String, MockMapping> mappings;

    public Map<String, MockMapping> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, MockMapping> mappings) {
        this.mappings = mappings;
    }

    public static class MockMapping {
        private String filePath;
        private Map<String, String> headers;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }
}*/
