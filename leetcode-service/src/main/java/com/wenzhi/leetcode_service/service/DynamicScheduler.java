package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.entity.JobTask;
import com.wenzhi.leetcode_service.dao.JobTaskDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@Slf4j
public class DynamicScheduler implements SchedulingConfigurer {

    private final JobTaskDao jobTaskMapper;
    private final ApplicationContext context;
    private final Map<String, ScheduledTask> taskMap = new ConcurrentHashMap<>();
    private ScheduledTaskRegistrar registrar;

    @Autowired
    public DynamicScheduler(JobTaskDao jobTaskMapper,
                            ApplicationContext context) {
        this.jobTaskMapper = jobTaskMapper;
        this.context = context;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
        refreshTasks();
    }

    // 刷新任务入口
    @Scheduled(fixedDelay = 60000) // 每分钟检查一次更新
    public void autoRefreshTasks() {
        refreshTasks();
    }

    public synchronized void refreshTasks() {
        // 获取当前所有配置
        List<JobTask> configs = jobTaskMapper.findAllEnabled();

        // 移除无效任务
        Set<String> currentKeys = configs.stream()
                .map(this::getTaskKey)
                .collect(Collectors.toSet());
        taskMap.keySet().removeIf(key -> !currentKeys.contains(key));

        // 添加/更新任务
        configs.forEach(config -> {
            String key = getTaskKey(config);
            if (!taskMap.containsKey(key) || isConfigModified(config)) {
                updateTask(config, key);
            }
        });
    }

    private String getTaskKey(JobTask config) {
        return config.getTaskClass() + "#" + config.getMethodName();
    }

    private boolean isConfigModified(JobTask newConfig) {
        // 实现配置变更检查逻辑（比较数据库版本号或更新时间）
        return true; // 示例直接返回true
    }

    private void updateTask(JobTask config, String key) {
        // 移除旧任务
        Optional.ofNullable(taskMap.remove(key)).ifPresent(ScheduledTask::cancel);

        // 创建新任务
        if (config.getEnabled()) {
            String cronExpression = config.getCronExpression();
            // 对Cron表达式进行空值检查
            if (cronExpression != null &&!cronExpression.isEmpty()) {
                Runnable task = createTaskRunnable(config);
                TriggerTask triggerTask = new TriggerTask(task,
                        triggerContext -> new CronTrigger(cronExpression)
                                .nextExecution(triggerContext));

                ScheduledTask scheduledTask = registrar.scheduleTriggerTask(triggerTask);
                taskMap.put(key, scheduledTask);
            } else {
                // 处理空值情况，设置默认Cron表达式并记录日志
                String defaultCronExpression = "0 0/5 * * * ?";
                log.error("Cron expression is empty for task: {}. Using default cron expression: {}", key, defaultCronExpression);
                Runnable task = createTaskRunnable(config);
                TriggerTask triggerTask = new TriggerTask(task,
                        triggerContext -> new CronTrigger(defaultCronExpression)
                                .nextExecution(triggerContext));

                ScheduledTask scheduledTask = registrar.scheduleTriggerTask(triggerTask);
                taskMap.put(key, scheduledTask);
            }
        }
    }

    private Runnable createTaskRunnable(JobTask config) {
        return () -> {
            try {
                // Object bean = context.getBean(Class.forName("com.wenzhi.leetcode_service.tasks.TestTask2"));
                Object bean = context.getBean(Class.forName(config.getTaskClass()));
                Method method = bean.getClass().getMethod(config.getMethodName());
                method.invoke(bean);
            } catch (Exception e) {
                // 处理异常
                log.error("Failed to execute job task: {}#{}", config.getTaskClass(), config.getMethodName(), e);
            }
        };
    }
}