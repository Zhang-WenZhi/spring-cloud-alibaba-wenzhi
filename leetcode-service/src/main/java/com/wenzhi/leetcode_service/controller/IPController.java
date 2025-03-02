package com.wenzhi.leetcode_service.controller;

import jakarta.servlet.http.HttpServletRequest; // Spring Boot 3.x
// import javax.servlet.http.HttpServletRequest; // Spring Boot 2.x
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IPController {

    @GetMapping("/ip")
    public String getClientIp(HttpServletRequest request) {
        // 0:0:0:0:0:0:0:1 // ipv6
        // 127.0.0.1 // ipv4
        return "Client IP: " + request.getRemoteAddr();
    }
}
