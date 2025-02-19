package com.wenzhi.user_service.controller;

import com.wenzhi.user_service.service.DeepSeekService;
import com.wenzhi.user_service.util.DeepSeekRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeepSeekController {

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private DeepSeekRequestBuilder requestBuilder;

    @GetMapping("/deepseek")
    public String callDeepSeek(@RequestParam String input) {
        String jsonInput = requestBuilder.buildRequest(input);
        return deepSeekService.sendRequest(jsonInput);
    }
}