//package com.wenzhi.leetcode_service.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JobScheduledTasks {
//
//    // 每隔5秒执行一次（fixedRate：固定频率）
//    @Scheduled(fixedRate = 5000)
//    public void taskWithFixedRate() {
//        System.out.println("Fixed rate task executed at: " + new Date());
//    }
//
//    // 上一次任务结束后间隔3秒执行（fixedDelay：固定延迟）
//    @Scheduled(fixedDelay = 3000)
//    public void taskWithFixedDelay() {
//        System.out.println("Fixed delay task executed at: " + new Date());
//    }
//
//    // 使用Cron表达式（每天12点执行）
//    @Scheduled(cron = "0 0 12 * * ?")
//    public void taskWithCronExpression() {
//        System.out.println("Cron task executed at noon.");
//    }
//
//    @Scheduled(fixedRateString = "${task.fixed.rate}")
//    public void dynamicFixedRateTask() {
//        // 任务逻辑
//        log.info("Dynamic fixed rate task executed at: {}", new Date());
//    }
//
//    @Scheduled(cron = "${task.cron}")
//    public void dynamicCronTask() {
//        // 任务逻辑
//        log.info("Dynamic cron task executed at: {}", new Date());
//    }
//
//    // 验证并发: 输出将显示任务由不同线程执行。
//    @Scheduled(fixedRate = 1000)
//    public void longRunningTask() throws InterruptedException {
//        System.out.println("Task started by thread: " + Thread.currentThread().getName());
//        Thread.sleep(5000); // 模拟耗时操作
//    }
//}
//
