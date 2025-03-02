package com.wenzhi.leetcode_service.entity.message;

import lombok.Data;

@Data
public class Response<T> {
    // 响应状态码（约定：200=成功，其他=异常）
    private int code;
    // 响应消息（成功时为"success"，异常时为错误描述）
    private String message;
    // 业务数据体（泛型）
    private T data;

    // 快速创建成功响应
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    // 快速创建失败响应
    public static <T> Response<T> error(int code, String message) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}