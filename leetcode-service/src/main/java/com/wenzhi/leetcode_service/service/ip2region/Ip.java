package com.wenzhi.leetcode_service.service.ip2region;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ip {
    // 这里可以添加注解的属性，如果不需要可以为空
    String value() default "";
}
