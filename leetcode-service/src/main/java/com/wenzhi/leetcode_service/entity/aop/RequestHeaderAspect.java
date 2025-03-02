package com.wenzhi.leetcode_service.entity.aop;

import com.wenzhi.leetcode_service.entity.exception.BusinessException;
import com.wenzhi.leetcode_service.entity.message.Request;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 请求头自动校验（AOP 实现）
 * */
@Aspect
@Component
public class RequestHeaderAspect {

    // 拦截所有 Controller 方法，自动校验请求头
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void validateHeader(Request<Object> request) {
        Request.Header header = request.getHeader();
        if (header == null || header.getVersion() == null) {
            throw new BusinessException(400, "请求头缺失版本号");
        }
    }
}
