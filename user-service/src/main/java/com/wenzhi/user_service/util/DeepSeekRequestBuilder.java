package com.wenzhi.user_service.util;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class DeepSeekRequestBuilder {

    public String buildRequest(String inputText) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", inputText);
        // 添加其他必要的参数
        return jsonObject.toString();
    }
}