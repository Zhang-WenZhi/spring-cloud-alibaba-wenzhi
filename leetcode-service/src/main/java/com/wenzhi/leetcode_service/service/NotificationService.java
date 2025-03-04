package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.entity.JobTask;
import com.wenzhi.leetcode_service.entity.exception.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.annotation.Retryable;

import java.util.Map;

@Component
@EnableRetry
@Slf4j
public class NotificationService {

    @Value("${notification.webhook-url}")
    private String webhookUrl;

    private final RestTemplate restTemplate;

    // 通过构造函数注入 RestTemplate
    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(retryFor = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void notifyFailure(JobTask task, Exception e) {
        String message = String.format("""
            【任务异常告警】
            任务名称：%s
            异常类型：%s
            异常信息：%s
            堆栈跟踪：%s
            """, task.getTaskName(), e.getClass().getName(),
                e.getMessage(), ExceptionUtils.getStackTrace(e));

        // 发送到日志
        log.error(message);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 创建请求体
        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("content", message), headers);

        // 发送HTTP通知并处理异常
        try {
            restTemplate.postForEntity(webhookUrl, request, Void.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            log.error("HTTP client error while sending alert to webhook. Status code: {}", httpClientErrorException.getStatusCode(), httpClientErrorException);
        } catch (ResourceAccessException resourceAccessException) {
            log.error("Resource access error while sending alert to webhook. Message: {}", resourceAccessException.getMessage(), resourceAccessException);
        } catch (Exception otherException) {
            log.error("An unexpected error occurred while sending alert to webhook.", otherException);
        }
    }
}