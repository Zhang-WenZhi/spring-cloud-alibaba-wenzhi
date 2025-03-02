package com.wenzhi.leetcode_service.entity.handler;

import com.wenzhi.leetcode_service.entity.exception.BusinessException;
import com.wenzhi.leetcode_service.entity.message.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Response<?> handleBusinessException(BusinessException e) {
        return Response.error(e.getCode(), e.getMessage());
    }

    // 处理系统异常（如参数校验失败）
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e) {
        log.error("系统异常:{}", e.getMessage());
        return Response.error(500, "系统繁忙，请稍后重试");
    }
}