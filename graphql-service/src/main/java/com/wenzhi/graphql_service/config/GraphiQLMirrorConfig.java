//package com.wenzhi.graphql_service.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.resource.ResourceUrlProvider;
//
///**
// * 配置GraphiQL使用国内镜像加速
// */
//@Configuration
//public class GraphiQLMirrorConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 国内CDN镜像配置 - 使用bootcdn加速GraphiQL相关资源
//        registry.addResourceHandler("/graphiql/**")
//                .addResourceLocations("https://cdn.bootcdn.net/ajax/libs/graphiql/3.0.0/")
//                .resourceChain(true);
//
//        // 代理GraphiQL插件资源
//        registry.addResourceHandler("/graphiql-plugins/**")
//                .addResourceLocations("https://cdn.bootcdn.net/ajax/libs/@graphiql/plugin-explorer/5.1.1/")
//                .resourceChain(true);
//
//        // 代理React资源
//        registry.addResourceHandler("/react/**")
//                .addResourceLocations("https://cdn.bootcdn.net/ajax/libs/react/18.2.0/umd/")
//                .resourceChain(true);
//
//        // 代理ReactDOM资源
//        registry.addResourceHandler("/react-dom/**")
//                .addResourceLocations("https://cdn.bootcdn.net/ajax/libs/react-dom/18.2.0/umd/")
//                .resourceChain(true);
//    }
//
//    @Bean
//    public ResourceUrlProvider resourceUrlProvider() {
//        return new ResourceUrlProvider();
//    }
//}
