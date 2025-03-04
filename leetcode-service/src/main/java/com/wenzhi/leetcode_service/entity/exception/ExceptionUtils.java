package com.wenzhi.leetcode_service.entity.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    /**
     * 获取异常的堆栈跟踪信息并以字符串形式返回
     * @param throwable 异常对象
     * @return 异常的堆栈跟踪信息字符串
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}