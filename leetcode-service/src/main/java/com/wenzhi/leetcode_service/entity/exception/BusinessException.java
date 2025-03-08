package com.wenzhi.leetcode_service.entity.exception;


import lombok.Getter;

// 自定义业务异常类
@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

}