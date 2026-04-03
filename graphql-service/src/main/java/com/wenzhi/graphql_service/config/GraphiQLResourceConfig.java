package com.wenzhi.graphql_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GraphiQLResourceConfig implements WebMvcConfigurer {

    // 国内镜像地址 - 使用bootcdn加速
    private static final String GRAPHIQL_CDN = "https://cdn.bootcdn.net/ajax/libs/graphiql/3.0.0/";
    private static final String GRAPHIQL_PLUGIN_CDN = "https://cdn.bootcdn.net/ajax/libs/@graphiql/plugin-explorer/5.1.1/";
    private static final String REACT_CDN = "https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/";
    private static final String REACT_DOM_CDN = "https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 代理GraphiQL核心资源
        registry.addResourceHandler("/graphiql/**")
                .addResourceLocations(GRAPHIQL_CDN);

        // 代理GraphiQL插件资源
        registry.addResourceHandler("/graphiql-plugins/**")
                .addResourceLocations(GRAPHIQL_PLUGIN_CDN);

        // 代理React资源
        registry.addResourceHandler("/react/**")
                .addResourceLocations(REACT_CDN);

        // 代理ReactDOM资源
        registry.addResourceHandler("/react-dom/**")
                .addResourceLocations(REACT_DOM_CDN);
    }
}
