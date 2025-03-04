package com.wenzhi.leetcode_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LocalWebhookController {
    private static final Logger logger = LoggerFactory.getLogger(LocalWebhookController.class);

    @PostMapping("/alert")
    public String handleAlert(@RequestBody String requestBody) {
        // 记录详细的告警信息到日志
        logger.error("Received alert: {}", requestBody);
        return "Alert received and processed successfully";
    }

}

