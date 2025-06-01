package com.wenzhi.mock_service.interceptor;

import com.wenzhi.mock_service.config.MockServerConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
// 通过 ContentCachingRequestWrapper 缓存请求体，避免拦截器读取后下游处理器无法获取
import org.springframework.web.util.ContentCachingRequestWrapper;

//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest; // Spring Boot 2.x
import jakarta.servlet.http.HttpServletRequest; // Spring Boot 3.x+
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class EnhancedMockInterceptor implements HandlerInterceptor {
    final Logger logger = LoggerFactory.getLogger(EnhancedMockInterceptor.class);

    private final MockServerConfig mockServerConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 通过@Qualifier指定Bean名称
    //     public EnhancedMockInterceptor(@Qualifier("mockServerConfig") MockServerConfig mockServerConfig) {
    public EnhancedMockInterceptor(MockServerConfig mockServerConfig) {
        this.mockServerConfig = mockServerConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截到请求：{}", request.getRequestURI()); // 添加日志

        // 打印 rules 是否为空
        logger.info("rules 是否为空：" + (mockServerConfig.getRules() == null));
        // 打印 rules 中的键
        logger.info("完整配置规则：{}", mockServerConfig.getRules());

        logger.info("请求路径：{}", request.getRequestURI());
        logger.info("HTTP方法：{}", request.getMethod());
        logger.info("请求体中的 method：{}", extractMethodFromRequestBody(request));
        logger.info("可用规则：{}", mockServerConfig.getRules().keySet()); // 打印所有加载的规则键


        // 统一请求路径为 /api/service，仅通过请求方法和请求体中的method字段匹配
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        // 提取请求体中的method字段（假设请求体为JSON格式）
        String methodParam = extractMethodFromRequestBody(request);

        // 构建匹配键：路径#HTTP方法#method参数
        String matchKey = requestURI + "#" + httpMethod + "#" + methodParam;

        // 查找配置规则
        MockServerConfig.MockRule mockRule = mockServerConfig.getRules().get(matchKey);

        if (mockRule != null) {
            // 返回模拟响应
            handleMockResponse(response, mockRule);
            return false; // 拦截请求
        }

        return true; // 继续正常处理
    }

    private String extractMethodFromRequestBody(HttpServletRequest request) throws IOException {
        // 处理请求体，支持重复读取（避免后续处理器无法获取请求体）
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        // 仅处理JSON格式的请求体
        if (isJsonRequest(wrappedRequest)) {
            String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
            try {
                JsonNode rootNode = objectMapper.readTree(requestBody);
                if (rootNode.has("method")) {
                    return rootNode.get("method").asText();
                }
            } catch (IOException e) {
                // 忽略解析异常，返回默认值
                logger.error("解析请求体异常：{}", e.getMessage());
            }
        }

        // 默认值（可自定义）
        return "default";
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE);
    }

    private void handleMockResponse(HttpServletResponse response, MockServerConfig.MockRule mockRule) throws IOException {
        // 设置响应头
        mockRule.getHeaders().forEach(response::setHeader);

        // 读取JSON文件
        String jsonContent = mockRule.readFileContent();
        response.setContentType(mockRule.getHeaders().getOrDefault("Content-Type", MediaType.APPLICATION_JSON_VALUE));
        response.getWriter().write(jsonContent);
    }
}

/*import com.wenzhi.mock_service.config.MockServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest; // Spring Boot 2.x
import jakarta.servlet.http.HttpServletRequest; // Spring Boot 3.x+
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EnhancedMockInterceptor implements HandlerInterceptor {

    @Autowired
    private MockServerConfig mockConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        // 获取请求中的method参数
        String methodParam = extractMethodParam(request);

        // 构建请求标识: URI#HTTP_METHOD#methodParam
        String requestKey = requestURI + "#" + httpMethod + "#" + methodParam;

        // 查找匹配的规则
        MockServerConfig.MockRule mockRule = findMatchingRule(requestKey);

        if (mockRule != null) {
            handleMockResponse(response, mockRule);
            return false;
        }

        return true;
    }

    private String extractMethodParam(HttpServletRequest request) {
        // 从请求参数中获取method
        String methodParam = request.getParameter("method");
        if (methodParam != null && !methodParam.isEmpty()) {
            return methodParam;
        }

        // 尝试从请求体中获取method
        try {
            String requestBody = extractRequestBody(request);
            if (requestBody != null && requestBody.contains("\"method\"")) {
                // 简单解析JSON获取method字段
                int startIndex = requestBody.indexOf("\"method\"") + 9;
                int endIndex = requestBody.indexOf("\"", startIndex);
                if (startIndex > 9 && endIndex > startIndex) {
                    return requestBody.substring(startIndex, endIndex);
                }
            }
        } catch (IOException e) {
            // 忽略异常，返回null
        }

        return "default"; // 默认值
    }

    private String extractRequestBody(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = request.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private MockServerConfig.MockRule findMatchingRule(String requestKey) {
        // 尝试精确匹配
        MockServerConfig.MockRule rule = mockConfig.getRules().get(requestKey);
        if (rule != null) {
            return rule;
        }

        // 尝试宽松匹配 (不带method参数)
        int lastHashIndex = requestKey.lastIndexOf('#');
        if (lastHashIndex > 0) {
            String partialKey = requestKey.substring(0, lastHashIndex);
            return mockConfig.getRules().get(partialKey);
        }

        return null;
    }

    private void handleMockResponse(HttpServletResponse response, MockServerConfig.MockRule mockRule) throws IOException {
        // 设置响应头
        for (Map.Entry<String, String> entry : mockRule.getHeaders().entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }

        if (!mockRule.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }

        // 读取JSON文件内容
        String jsonContent = readResourceFile(mockRule.getFile());

        // 返回响应体
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonContent);
            writer.flush();
        }
    }

    private String readResourceFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}*/

// 配置文件mapping:
/*
import com.wenzhi.mock_service.config.MockServerConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest; // Spring Boot 2.x
import jakarta.servlet.http.HttpServletRequest; // Spring Boot 3.x+
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class EnhancedMockInterceptor implements HandlerInterceptor {

    private final MockServerConfig mockServerConfig;

    public EnhancedMockInterceptor(MockServerConfig mockServerConfig) {
        this.mockServerConfig = mockServerConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 获取请求中的method参数
        String methodParam = request.getParameter("method");
        if (methodParam == null) {
            methodParam = "default"; // 默认值
        }

        // 构建请求标识：路径#HTTP方法#method参数
        String requestKey = request.getRequestURI() + "#" +
                request.getMethod() + "#" +
                methodParam;

        // 查找映射配置
        Map<String, MockServerConfig.MockMapping> mappings = mockServerConfig.getMappings();
        MockServerConfig.MockMapping mockMapping = mappings.get(requestKey);

        if (mockMapping != null) {
            // 读取JSON文件内容
            String jsonContent = Files.readString(Paths.get(mockMapping.getFilePath()));

            // 设置响应头
            for (Map.Entry<String, String> entry : mockMapping.getHeaders().entrySet()) {
                response.setHeader(entry.getKey(), entry.getValue());
            }

            // 返回JSON内容
            response.getWriter().write(jsonContent);
            return false; // 拦截请求，不再继续处理
        }

        return true; // 继续正常处理流程
    }
}*/
