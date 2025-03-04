package com.wenzhi.leetcode_service.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobTask {
    private Long id;
    private String taskName;
    private String taskClass;
    private String methodName;
    private TriggerType triggerType;
    private String cronExpression;
    private Long intervalMillis;
    private Boolean enabled;
    private Integer version;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}



