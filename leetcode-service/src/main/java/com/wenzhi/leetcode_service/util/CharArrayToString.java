package com.wenzhi.leetcode_service.util;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

public class CharArrayToString {
    public static String charArrayToString(char[] chars, int length) {
        if (chars == null) {
            return "null";
        }

        /*JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < n; i++) {
            jsonArray.add(String.valueOf(chars[i]));
        }
        return jsonArray.toString();*/

        // 创建一个 JsonArrayBuilder 实例
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        // 将字符数组中的元素添加到 JsonArrayBuilder 中
        for (int i = 0; i < length; i++) {
            arrayBuilder.add(String.valueOf(chars[i]));
        }

        // 构建最终的 JsonArray
        JsonArray jsonArray = arrayBuilder.build();
        return jsonArray.toString();
    }

    public static String charArrayToString(char[] chars) {
        if (chars == null) {
            return "null";
        }

        return charArrayToString(chars, chars.length);
    }
}
