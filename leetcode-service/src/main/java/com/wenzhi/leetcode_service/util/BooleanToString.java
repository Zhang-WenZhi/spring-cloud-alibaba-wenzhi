package com.wenzhi.leetcode_service.util;

public class BooleanToString {
    public static String booleanToString(boolean input) {
        return input ? "True" : "False";
    }

    public static boolean stringToBool(String input) {
        return input.toLowerCase() == "true";
    }
}
