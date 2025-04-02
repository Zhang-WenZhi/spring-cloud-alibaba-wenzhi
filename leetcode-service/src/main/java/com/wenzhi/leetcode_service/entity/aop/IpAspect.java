package com.wenzhi.leetcode_service.entity.aop;

import com.wenzhi.leetcode_service.util.AddressUtil;
import com.wenzhi.leetcode_service.util.HttpContextUtil;
import com.wenzhi.leetcode_service.util.IPUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;


public class IpAspect {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.wenzhi.leetcode_service.service.ip2region.Ip)")
    public void ipPointcut() {
        logger.info("ipPointcut");
    }

    @Around("ipPointcut()")
    public Object doIpAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("doIpAround");
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddress(request);
        logger.info(MessageFormat.format("当前IP为:【{0}】: 当前IP解析出来的地址为：【{1}】",
                ip, AddressUtil.getCityInfo(ip)));
        return joinPoint.proceed();
    }
}
