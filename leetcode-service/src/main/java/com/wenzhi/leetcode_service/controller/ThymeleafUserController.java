package com.wenzhi.leetcode_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class ThymeleafUserController {

    @GetMapping("/thymeleaf-users")
    public String showUsers(Model model) {
        // 模拟用户列表数据
        List<String> users = Arrays.asList("Alice", "Bob", "Charlie");
        log.info("thymeleaf-users: {}", users);
        // 将用户列表数据添加到模型中
        model.addAttribute("users", users);
        return "thymeleaf-users"; // 要重新编译，确保target目录下有该html文件,且这儿不能加文件后缀.html，否则报错找不到文件
    }
}
