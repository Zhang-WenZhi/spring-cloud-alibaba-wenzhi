package com.wenzhi.leetcode_service.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * MyBatis SQL 性能拦截器，用于记录 SQL 执行时间和参数
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SqlPerformanceInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(SqlPerformanceInterceptor.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        // 获取执行的 SQL
        String sql = statementHandler.getBoundSql().getSql();

        // 获取 SQL 参数
        Object parameterObject = statementHandler.getBoundSql().getParameterObject();
        String parameters = parameterObject != null ? parameterObject.toString() : "null";

        // 记录执行前时间
        long startTime = System.currentTimeMillis();
        try {
            // 执行原方法
            return invocation.proceed();
        } finally {
            // 记录执行后时间
            long endTime = System.currentTimeMillis();
            // 计算执行时间（毫秒）
            long executionTime = endTime - startTime;

            // 构建日志内容
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(LINE_SEPARATOR)
                    .append("===== SQL Performance Monitor =====").append(LINE_SEPARATOR)
                    .append("SQL: ").append(sql).append(LINE_SEPARATOR)
                    .append("Parameters: ").append(parameters).append(LINE_SEPARATOR)
                    .append("Execution Time: ").append(executionTime).append(" ms").append(LINE_SEPARATOR)
                    .append("===================================").append(LINE_SEPARATOR);

            // 根据执行时间设置不同的日志级别
            if (executionTime > 1000) {
                logger.error(logBuilder.toString());
            } else if (executionTime > 500) {
                logger.warn(logBuilder.toString());
            } else {
                logger.info(logBuilder.toString());
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // 可用于配置拦截器参数
    }
}