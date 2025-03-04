package com.wenzhi.leetcode_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyConfig {
    private Long id;
    private Long taskId;
    private NotifyType notifyType;
    private String receiver;
    private String template;

    public enum NotifyType {
        EMAIL,
        SMS,
        WEBHOOK,
        DINGTALK
    }

    // 校验接收者格式
    public boolean validateReceiver() {
        return switch (notifyType) {
            case EMAIL -> receiver.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
            case SMS -> receiver.matches("^1[3-9]\\d{9}$");
            case WEBHOOK -> receiver.startsWith("http://") || receiver.startsWith("https://");
            case DINGTALK -> receiver.startsWith("https://oapi.dingtalk.com");
            default -> false;
        };
    }
}