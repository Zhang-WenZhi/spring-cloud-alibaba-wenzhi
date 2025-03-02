package com.wenzhi.leetcode_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 用 AOP 支持
 * 1) 注解方式: 在配置类上添加 @EnableAspectJAutoProxy：
 * 2) (2) XML 方式: 在 spring 配置文件中添加 <aop:aspectj-autoproxy/>
 * */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
}
