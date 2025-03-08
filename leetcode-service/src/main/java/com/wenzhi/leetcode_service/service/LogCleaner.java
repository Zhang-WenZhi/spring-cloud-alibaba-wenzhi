package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.dao.JobTaskDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class LogCleaner {

    @Autowired
    private JobTaskDao jobTaskMapper;

    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行
    public void cleanOldLogs() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        int count = jobTaskMapper.deleteLogsBefore(threshold);
        log.info("Deleted {} old logs", count);
    }

    // 每 30 分钟执行一次
    @Scheduled(cron = "0 0/30 * * * ?")
    public void cleanOldLogsPerThirtyMinutes() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(1);
        int count = jobTaskMapper.deleteLogsBefore(threshold);
        log.info("每 30 分钟 Deleted {} old logs", count);
    }

    // 每分钟执行一次
    @Scheduled(cron = "0 * * * * ?")
    public void cleanOldLogsPerMinute() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(1);
        int count = jobTaskMapper.deleteLogsBefore(threshold);
        log.info("每分钟执行 Deleted {} old logs", count);
    }
}