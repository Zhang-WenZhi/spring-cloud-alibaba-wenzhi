package com.wenzhi.leetcode_service.entity.exception;


// 自定义业务异常类
public class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}