package com.wenzhi.jsp_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.loader.launch.WarLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
//@Slf4j
public class JspDemoController {

    private static final Logger logger = LoggerFactory.getLogger(JspDemoController.class);


    @GetMapping("/jsp-demo")
    public String jspDemo(Model model) {
        // 模拟一些数据
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry", "Date");
        logger.info("jsp-demo fruits【##################】 {}", fruits);
        model.addAttribute("fruits", fruits);
        return "beauty-login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        logger.info("开始登陆！");
        return "login";
    }

    @PostMapping("/home")
    public String goHome(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        logger.info("开始验证登陆信息！");
        logger.info("username【{}】 password【{}】", username, password);
        // 简单的验证逻辑，实际应用中应从数据库或其他存储中验证
        if ("admin".equals(username) && "123456".equals(password)) {
            session.setAttribute("username", username);
            return "home"; // 返回 home.jsp 视图名称
        } else {
            return "404";
        }
    }
}