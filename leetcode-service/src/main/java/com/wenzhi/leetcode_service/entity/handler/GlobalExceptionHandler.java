package com.wenzhi.leetcode_service.entity.handler;

import com.wenzhi.leetcode_service.entity.exception.BusinessException;
import com.wenzhi.leetcode_service.entity.message.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Response<ErrorResponse> handleBusinessException(BusinessException e) {
        return Response.error(e.getCode(), e.getMessage());
    }

    // 全局异常处理【处理参数校验失败】
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return Response.error(400, "参数校验失败" + errors);
    }

    // 处理系统异常（如参数校验失败，兜底）
    @ExceptionHandler(Exception.class)
    public Response<ErrorResponse> handleException(Exception e) {
        log.error("系统异常:{}", e.getMessage());
        return Response.error(500, "系统繁忙，请稍后重试");
    }

}