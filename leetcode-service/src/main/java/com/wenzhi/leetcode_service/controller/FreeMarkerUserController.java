package com.wenzhi.leetcode_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class FreeMarkerUserController {

    @GetMapping("/freemarker-users")
    public String showFreeMarkerUsers(Model model) {
        // 模拟用户列表数据
        List<String> users = Arrays.asList("David", "Eve", "Frank");
        log.info("freemarker-users: {}", users);
        // 将用户列表数据传递给前端页面
        model.addAttribute("users", users);
        return "freemarker-users";
    }
}