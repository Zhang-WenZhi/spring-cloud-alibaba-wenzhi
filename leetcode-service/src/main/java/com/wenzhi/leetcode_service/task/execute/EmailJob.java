package com.wenzhi.leetcode_service.task.execute;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailJob {
    // 任务执行的方法
    public void run() {
        // 具体业务逻辑
        long startTime = System.currentTimeMillis();
        // 业务逻辑代码
        long endTime = System.currentTimeMillis();
        log.info("任务执行时间：{}ms", endTime - startTime);
    }
}
