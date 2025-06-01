package com.wenzhi.mock_service.config;

import com.wenzhi.mock_service.interceptor.EnhancedMockInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final EnhancedMockInterceptor mockInterceptor;

    public WebMvcConfig(EnhancedMockInterceptor mockInterceptor) {
        this.mockInterceptor = mockInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mockInterceptor)
                .addPathPatterns("/api/service"); // 明确拦截该路径
    }
}
