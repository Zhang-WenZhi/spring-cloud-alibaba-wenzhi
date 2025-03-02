package com.wenzhi.leetcode_service.entity.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import javax.servlet.http.HttpServletRequest; // Spring Boot 2.x
import jakarta.servlet.http.HttpServletRequest; // Spring Boot 3.x+
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 定义切点：监控 controller 包及其子包下的所有方法
     */
    @Pointcut("execution(* com.wenzhi.leetcode_service.controller..*.*(..))")
    public void controllerPointcut() {}

    /**
     * 方法执行前打印请求信息
     */
    @Before("controllerPointcut()")
    public void logBeforeController(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return;

            HttpServletRequest request = attributes.getRequest();
            Map<String, Object> logData = new HashMap<>();

            // 基础信息
            logData.put("URI", request.getRequestURI());
            logData.put("HTTP Method", request.getMethod());
            logData.put("Client IP", request.getRemoteAddr());

            // 方法信息
            logData.put("Class.Method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

            // 过滤并序列化参数（排除HttpServletRequest/HttpServletResponse）
            Object[] args = joinPoint.getArgs();
            String parameters = Arrays.stream(args)
                    .filter(arg -> !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))
                    .map(this::toJsonString)
                    .collect(Collectors.joining(", "));

            logData.put("Parameters", parameters.isEmpty() ? "None" : parameters);

            // 输出日志
            log.info("Controller Request:\n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logData));
        } catch (Exception e) {
            log.warn("AOP logBeforeController error: {}", e.getMessage());
        }
    }

    /**
     * 对象转JSON字符串（安全处理）
     */
    private String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj); // 无法序列化时转为字符串
        }
    }
}