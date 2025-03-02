package com.wenzhi.leetcode_service.entity.message;

import lombok.Data;

@Data
public class Request<T> {
    // 公共请求头（可根据业务扩展）
    private Header header;
    // 业务请求体（泛型）
    private T body;

    @Data
    public static class Header {
        // 示例字段：客户端类型、令牌、请求时间戳
        private String clientType; // e.g., "WEB", "APP"
        private String token;
        private Long timestamp;
        private String version; // API版本号
    }
}