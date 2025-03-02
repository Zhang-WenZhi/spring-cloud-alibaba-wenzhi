//package com.wenzhi.leetcode_service.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * 动态配置 + 线程池
// * */
//@Component
//@Slf4j
//public class AdvancedScheduledTasks {
//
//    @Scheduled(cron = "${task.cleanup.cron}")
//    public void cleanupTask() {
//        // 清理过期数据的逻辑
//        log.info("清理过期数据");
//    }
//
//    @Scheduled(fixedRateString = "${task.report.interval}", initialDelay = 5000)
//    public void generateReport() {
//        // 生成报表的逻辑
//        log.info("生成报表");
//    }
//}