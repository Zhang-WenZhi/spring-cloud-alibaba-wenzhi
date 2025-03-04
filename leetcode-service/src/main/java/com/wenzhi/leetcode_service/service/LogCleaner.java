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
}