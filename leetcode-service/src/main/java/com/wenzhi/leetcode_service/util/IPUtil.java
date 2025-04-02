package com.wenzhi.leetcode_service.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.regex.Pattern;

public class IPUtil {
    private static final String UNKNOWN = "unknown";
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_CLIENT_IP",
            "HTTP_CLIENT_IP"
    };
    private static final Pattern IP_PATTERN = Pattern.compile("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");

    public static String getIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
                int index = ip.indexOf(',');
                if (index != -1) {
                    ip = ip.substring(0, index);
                }
                if (IP_PATTERN.matcher(ip).matches()) {
                    return ip;
                }
            }
        }
        return request.getRemoteAddr();
    }
}