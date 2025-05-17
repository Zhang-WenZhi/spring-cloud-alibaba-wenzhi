//package com.wenzhi.leetcode_service.interceptor;
//
//
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
//@Component
//@Intercepts({
//        @Signature(
//                type = Executor.class,
//                method = "query",
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
//        ),
//        @Signature(
//                type = Executor.class,
//                method = "query",
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
//        ),
//        @Signature(
//                type = Executor.class,
//                method = "update",
//                args = {MappedStatement.class, Object.class}
//        )
//})
//public class SqlExecMoniInterceptor implements Interceptor {
//    private static final Logger logger = LoggerFactory.getLogger(SqlExecMoniInterceptor.class);
//    private static final String LOG_PREFIX = "[SQL-MONITOR]"; // 统一日志前缀
//    private static long SLOW_SQL_THRESHOLD = 1000; // 单位：毫秒
//    private final AtomicInteger interceptCount = new AtomicInteger(0); // 拦截计数器
//    private boolean initialized = false; // 初始化状态标记
//    // 新增慢SQL计数器
//    private final AtomicInteger slowSqlCount = new AtomicInteger(0);
//
//    // 新增状态检查方法
//    public String getInterceptorStatus() {
//        return String.format("[%s] 运行状态：%s | 已拦截次数：%d | 慢SQL数量：%d",
//                LOG_PREFIX,
//                initialized ? "已激活" : "未初始化",
//                interceptCount.get(),
//                slowSqlCount.get());
//    }
//
////    @Override
////    public Object intercept(Invocation invocation) throws Throwable {
////        Object[] args = invocation.getArgs();
////        MappedStatement ms = (MappedStatement) args[0];
////        Object parameter = args.length > 1 ? args[1] : null;
////
////        // 获取BoundSql和CacheKey
////        BoundSql boundSql = null;
////        CacheKey cacheKey = null;
////
////        if (args.length == 6) { // 带CacheKey的查询
////            boundSql = (BoundSql) args[5];
////            cacheKey = (CacheKey) args[4];
////        } else if (args.length == 4) { // 普通查询
////            boundSql = ms.getBoundSql(parameter);
////        } else if ("update".equals(invocation.getMethod().getName())) { // 更新操作
////            boundSql = ms.getBoundSql(parameter);
////        }
////
////        long start = System.currentTimeMillis();
////        try {
////            return invocation.proceed();
////        } finally {
////            long cost = System.currentTimeMillis() - start;
////            logExecutionDetails(ms, boundSql, cacheKey, parameter, cost);
////        }
////    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        interceptCount.incrementAndGet(); // 增加拦截计数
//
//        // 添加拦截点标识
//        if (logger.isTraceEnabled()) {
//            logger.trace("{} 进入拦截点，方法：{} 参数：{}",
//                    LOG_PREFIX,
//                    invocation.getMethod().getName(),
//                    Arrays.toString(invocation.getArgs()));
//        }
//
//        Object[] args = invocation.getArgs();
//        MappedStatement ms = (MappedStatement) args[0];
//
//        // 添加方法拦截标识
//        if (logger.isDebugEnabled()) {
//            logger.debug("{} 拦截到SQL执行 - Mapper方法：{}", LOG_PREFIX, ms.getId());
//        }
//
//        long start = System.currentTimeMillis();
//        try {
//            return invocation.proceed();
//        } finally {
//            long cost = System.currentTimeMillis() - start;
//            // 在性能日志中添加特征标记
//            if (cost > SLOW_SQL_THRESHOLD) {
//                slowSqlCount.incrementAndGet(); // 慢SQL计数
//                logger.warn("{} 🚨慢SQL标记", LOG_PREFIX);
//            }
//        }
//    }
//
//    private void logExecutionDetails(MappedStatement ms,
//                                     BoundSql boundSql,
//                                     CacheKey cacheKey,
//                                     Object parameter,
//                                     long costTime) {
//        // 参数解析
//        Map<String, Object> paramMap = getRunParameterValue(ms, boundSql, parameter);
//
//        String logContent = String.format("\n%s=== SQL监控 ===\n" +
//                        "执行耗时：%dms\n" +
//                        "操作类型：%s\n" +
//                        "Mapper方法：%s\n" +
//                        "CacheKey：%s\n" +
//                        "SQL语句：%s\n" +
//                        "参数明细：%s\n" +
//                        "================",
//                LOG_PREFIX,  // 添加前缀
//                costTime,
//                ms.getSqlCommandType(),
//                ms.getId(),
//                (cacheKey != null ? cacheKey.toString() : "N/A"),
//                formatSql(boundSql.getSql()),
//                formatParams(paramMap));
//
//        if (costTime > SLOW_SQL_THRESHOLD) {
//            logger.warn("🚨 慢SQL告警 ({}) {}", costTime + "ms", logContent);
//        } else if (logger.isDebugEnabled()) {
//            logger.debug("SQL监控 {}", logContent);
//        }
//    }
//
//    /**
//     * 获取运行时参数键值对
//     */
//    private Map<String, Object> getRunParameterValue(MappedStatement ms,
//                                                     BoundSql boundSql,
//                                                     Object parameter) {
//        Map<String, Object> paramMap = new LinkedHashMap<>();
//        try {
//            MetaObject metaObject = parameter == null ? null : SystemMetaObject.forObject(parameter);
//
//            // 处理多个参数（@Param注解方式）
//            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//            for (ParameterMapping mapping : parameterMappings) {
//                String property = mapping.getProperty();
//                if (metaObject != null && metaObject.hasGetter(property)) {
//                    paramMap.put(property, metaObject.getValue(property));
//                } else {
//                    paramMap.put(property, boundSql.getAdditionalParameter(property));
//                }
//            }
//
//            // 处理动态参数（foreach等）
//            Object additionalParams = boundSql.getAdditionalParameters();
//            if (additionalParams instanceof Map) {
//                ((Map<?, ?>) additionalParams).forEach((k, v) -> {
//                    if (!paramMap.containsKey(k.toString())) {
//                        paramMap.put(k.toString(), v);
//                    }
//                });
//            }
//        } catch (Exception e) {
//            logger.error("参数解析失败", e);
//        }
//        return paramMap;
//    }
//
//    private String formatSql(String sql) {
//        return sql.replaceAll("\\s+", " ")
//                .replaceAll("\n", " ")
//                .trim();
//    }
//
//    private String formatParams(Map<String, Object> params) {
//        return params.entrySet().stream()
//                .map(entry -> String.format("%s=%s",
//                        entry.getKey(),
//                        formatParamValue(entry.getValue())))
//                .collect(Collectors.joining(", "));
//    }
//
//    private String formatParamValue(Object value) {
//        if (value == null) return "null";
//        if (value.getClass().isArray()) {
//            return Arrays.toString((Object[]) value);
//        }
//        if (value instanceof Collection) {
//            return ((Collection<?>) value).stream()
//                    .map(Object::toString)
//                    .collect(Collectors.joining(",", "[", "]"));
//        }
//        return value.toString();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        String threshold = properties.getProperty("slowSqlThreshold");
//        if (threshold != null) {
//            SLOW_SQL_THRESHOLD = Long.parseLong(threshold);
//        }
//        logger.info("{} 拦截器初始化完成 (慢SQL阈值={}ms)", LOG_PREFIX, SLOW_SQL_THRESHOLD);
//        logger.debug("{} 已加载配置参数：{}", LOG_PREFIX, properties);
//        initialized = true;
//    }
//
//    // 添加初始化状态检查方法
//    public boolean isInitialized() {
//        return initialized;
//    }
//}
