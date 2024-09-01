package com.wenzhi.user_service.config;

import com.alibaba.cloud.commons.lang.StringUtils;
// import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

// 授权规则：白名单/黑明单 sentinel
@Component
public class HeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        String origin = httpServletRequest.getHeader("origin");
        if(StringUtils.isEmpty(origin)) {
            return "blank";
        }
        return origin;
    }
}