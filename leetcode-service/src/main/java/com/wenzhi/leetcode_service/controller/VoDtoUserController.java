package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.entity.dto.LoginRequestDTO;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.entity.message.Response;
import com.wenzhi.leetcode_service.entity.vo.UserVO;
import com.wenzhi.leetcode_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class VoDtoUserController {
    // 注入 UserService
     private final UserService userService;

    @Autowired
    public VoDtoUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody @Validated Request<LoginRequestDTO> request) {
        // 1. 获取请求头信息（可根据需要校验 token、版本号等）
        Request.Header header = request.getHeader();
        log.info("Client type: {}", header.getClientType());

        // 2. 处理业务逻辑
        LoginRequestDTO loginRequest = request.getBody();
        log.info("Login request: {}", loginRequest);
        UserVO userVO = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

        // 3. 返回统一响应格式
        return Response.success(userVO);
    }
}

