package com.wenzhi.leetcode_service.entity;

import com.wenzhi.leetcode_service.entity.exception.ExceptionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTaskLog {
    private Long id;
    private Long taskId;
    private LogStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorInfo;
    private Long duration;
    private TriggerType triggerType;
    private String triggerConf;

    // 手动添加接受 JobTask 参数的构造函数
    public JobTaskLog(JobTask jobTask) {
        this.taskId = jobTask.getId();
        this.triggerType = jobTask.getTriggerType();
        if (jobTask.getCronExpression() != null) {
            this.triggerConf = jobTask.getCronExpression();
        } else {
            this.triggerConf = String.valueOf(jobTask.getIntervalMillis());
        }
        this.startTime = LocalDateTime.now();
        this.status = LogStatus.valueOf("RUNNING");
    }

    public enum LogStatus {
        STARTED, SUCCESS, FAILED, RUNNING
    }

    // 便捷方法：记录开始时间
    public JobTaskLog(Long taskId, TriggerType triggerType, String triggerConf) {
        this.taskId = taskId;
        this.triggerType = triggerType;
        this.triggerConf = triggerConf;
        this.status = LogStatus.STARTED;
        this.startTime = LocalDateTime.now();
    }

    // 标记成功
    public void markSuccess() {
        this.status = LogStatus.SUCCESS;
        this.endTime = LocalDateTime.now();
        this.duration = Duration.between(startTime, endTime).toMillis();
    }

    // 标记失败
    public void markFailure(Exception e) {
        this.status = LogStatus.FAILED;
        this.endTime = LocalDateTime.now();
        this.duration = Duration.between(startTime, endTime).toMillis();
        this.errorInfo = ExceptionUtils.getStackTrace(e);
    }
}