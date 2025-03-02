package com.wenzhi.leetcode_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * UserServiceImpl中使用了BCryptPasswordEncoder加密密码，这里配置BCryptPasswordEncoder
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 将Spring Boot版本回退至2.7.x或更早版本（如2.7.10），此时WebSecurityConfigurerAdapter仍有效
    // extends WebSecurityConfigurerAdapter @EnableWebSecurity
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // 关闭 CSRF 保护（生产环境需谨慎）
//                .authorizeRequests()
//                .anyRequest().permitAll() // 允许所有请求无需认证
//                .and()
//                .logout().permitAll(); // 允许注销
//    }


    // 在Spring Boot 3.x版本中，WebSecurityConfigurerAdapter类已被官方弃用。
    // 若你使用的是spring-boot-starter-security 3.3.3（对应Spring Security 6.x）
    /*
    * 不加这个，spring-boot-starter-security生成了一个密码，用户名user，都是登陆报错的
    * **/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .anyRequest().permitAll() // 允许所有请求无需认证
                // authenticated() 所有请求都需要认证
        );
        return http.build();
    }
}