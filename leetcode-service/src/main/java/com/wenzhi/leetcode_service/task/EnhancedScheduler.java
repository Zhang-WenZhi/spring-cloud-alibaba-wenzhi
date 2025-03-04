package com.wenzhi.leetcode_service.task;

import com.wenzhi.leetcode_service.dao.JobTaskDao;
import com.wenzhi.leetcode_service.entity.JobTask;
import com.wenzhi.leetcode_service.entity.JobTaskLog;
import com.wenzhi.leetcode_service.service.NotificationService;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class EnhancedScheduler implements SchedulingConfigurer {

    private final JobTaskDao jobTaskMapper;
    private final ApplicationContext context;
    private final NotificationService notificationService;
    private final Map<String, ScheduledTask> taskMap = new ConcurrentHashMap<>();
    private ScheduledTaskRegistrar registrar;

    // 构造器注入
    public EnhancedScheduler(JobTaskDao jobTaskMapper, ApplicationContext context, NotificationService notificationService) {
        this.jobTaskMapper = jobTaskMapper;
        this.context = context;
        this.notificationService = notificationService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
        reloadAllTasks();
    }

    private void reloadAllTasks() {
        List<JobTask> tasks = jobTaskMapper.selectEnabledTasks();
        tasks.forEach(task -> {
            String key = buildTaskKey(task);
            if (needUpdate(task, key)) {
                registerNewTask(task, key);
            }
        });
        cleanInvalidTasks(tasks);
    }

    private String buildTaskKey(JobTask task) {
        return task.getTaskClass() + "#" + task.getMethodName();
    }

    private boolean needUpdate(JobTask task, String key) {
        // 这里简单判断如果任务不在任务映射中或者任务版本有更新则需要更新
        ScheduledTask existingTask = taskMap.get(key);
        if (existingTask == null) {
            return true;
        }
        // 假设 JobTask 有 getVersion 方法获取任务版本
        JobTask existingJobTask = getExistingJobTask(key);
        return existingJobTask != null && existingJobTask.getVersion() < task.getVersion();
    }

    private JobTask getExistingJobTask(String key) {
        // 这里可以根据 key 从数据库或者缓存中获取已存在的任务信息
        // 为了简化，这里假设不实现具体逻辑，直接返回 null
        return null;
    }

    private void registerNewTask(JobTask task, String key) {
        // 移除旧任务
        ScheduledTask existingTask = taskMap.remove(key);
        if (existingTask != null) {
            existingTask.cancel();
        }

        // 创建新任务
        Runnable taskRunnable = buildTaskRunnable(task);
        Trigger trigger = buildTrigger(task);
        TriggerTask triggerTask = new TriggerTask(taskRunnable, trigger);
        ScheduledTask scheduledTask = registrar.scheduleTriggerTask(triggerTask);
        taskMap.put(key, scheduledTask);
    }

    private void cleanInvalidTasks(List<JobTask> validTasks) {
        // 获取当前有效的任务键集合
        java.util.Set<String> validKeys = validTasks.stream()
                .map(this::buildTaskKey)
                .collect(Collectors.toSet());

        // 移除无效任务
        taskMap.keySet().removeIf(key -> !validKeys.contains(key));
    }

    private Trigger buildTrigger(JobTask task) {
        switch (task.getTriggerType()) {
            case CRON:
                return new CronTrigger(task.getCronExpression());
            case FIXED_RATE:
            case FIXED_DELAY:
                return new PeriodicTrigger(Duration.ofMillis(task.getIntervalMillis()));
            default:
                throw new IllegalArgumentException("Unsupported trigger type");
        }
    }

    private Runnable buildTaskRunnable(JobTask task) {
        return () -> {
            JobTaskLog log = new JobTaskLog(task);
            try {
                executeTaskMethod(task);
                log.markSuccess();
            } catch (Exception e) {
                log.markFailure(e);
                notificationService.notifyFailure(task, e);
            } finally {
                jobTaskMapper.insertLog(log);
            }
        };
    }

    private void executeTaskMethod(JobTask task) throws Exception {
        // 获取任务类的实例
        Object bean = context.getBean(Class.forName(task.getTaskClass()));
        // 获取任务方法
        Method method = bean.getClass().getMethod(task.getMethodName());
        // 执行任务方法
        method.invoke(bean);
    }

    // 新增方法：刷新单个任务
    public void refreshSingleTask(JobTask task) {
        String key = buildTaskKey(task);
        if (needUpdate(task, key)) {
            registerNewTask(task, key);
        }
    }
}