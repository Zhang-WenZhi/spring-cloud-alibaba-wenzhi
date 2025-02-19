package com.wenzhi.leetcode_service.util;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonReader;

import java.io.StringReader;

public class Char2dArrayToString {
    public static String stringToString(String input) {
        // return JsonArray.readFrom("[" + input + "]").get(0).asString();
        JsonReader reader = Json.createReader(new StringReader("[" + input + "]"));
        JsonArray jsonArray = reader.readArray();
        return jsonArray.get(0).toString();
    }

    public static String charToString(char[] c) {
        return stringToString(String.valueOf(c));
    }

    public static String char2dArrayToString(char[][] array) {
        if (array == null) {
            return "null";
        }

        if (array.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (char[] row : array) {
            sb.append(charToString(row));
            sb.append(",");
        }

        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public static String charArrayToString(char[] chars, int length) {
        if (chars == null) {
            return "null";
        }

        /*JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < n; i++) {
            jsonArray.add(String.valueOf(chars[i]));
        }*/

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
