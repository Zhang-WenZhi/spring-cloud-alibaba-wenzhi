package com.wenzhi.leetcode_service.util;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;

import java.io.StringReader;

public class PrettyPrintLinkedList {
    public static void prettyPrintTree(TreeNode node, String prefix, boolean isLeft) {
        if (node == null) {
            System.out.println("Empty tree");
            return;
        }

        if (node.right != null) {
            prettyPrintTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }

        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.val);

        if (node.left != null) {
            prettyPrintTree(node.left, prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    public static void prettyPrintTree(TreeNode node) {
        prettyPrintTree(node,  "", true);
    }

    public static String stringToString(String input) {
        // JsonArray.readFrom() 方法确实 不属于 Jakarta JSON-P 的标准 API，而是特定 JSON 库（如 Eclipse Parsson 或旧版 GlassFish）的扩展方法
        // 可能是因为 readFrom() 是特定实现的扩展方法。建议改用 标准 JSON-P API 的解析方式
        // import jakarta.json.JsonArray;   // ✅ 正确
        // import javax.json.JsonArray; // ❌ 旧版 Java EE，不兼容

        // 没有.readFrom()方法
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


}
