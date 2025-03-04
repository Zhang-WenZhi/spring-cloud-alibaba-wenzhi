package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.dao.JobTaskDao;
import com.wenzhi.leetcode_service.entity.JobTask;
import com.wenzhi.leetcode_service.entity.exception.VersionConflictException;
import com.wenzhi.leetcode_service.task.EnhancedScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private EnhancedScheduler scheduler;
    @Autowired
    private JobTaskDao jobTaskMapper;

    @GetMapping
    public List<JobTask> listJobs() {
        return jobTaskMapper.selectEnabledTasks();
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<?> refreshJob(@PathVariable Long id) {
        JobTask task = jobTaskMapper.selectById(id);
        scheduler.refreshSingleTask(task);
        // return ResponseEntity.ok().build();
        return ResponseEntity.ok("Job refreshed successfully");
    }

    // 检查是否有 @PreAuthorize 等权限注解
    // 例如：@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id,
                                       @RequestBody JobTask updatedTask) {
        int affected = jobTaskMapper.updateWithLock(updatedTask);
        if (affected == 0) {
            throw new VersionConflictException("数据版本冲突");
        }
        scheduler.refreshSingleTask(updatedTask);
        // return ResponseEntity.ok().build();
        return ResponseEntity.ok("Job list retrieved successfully");
    }
}