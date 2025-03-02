package com.wenzhi.leetcode_service.entity.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.wenzhi.leetcode_service.service.*.*(..))")
    public void logBefore() {
        System.out.println("service方法执行前记录日志");
    }
}