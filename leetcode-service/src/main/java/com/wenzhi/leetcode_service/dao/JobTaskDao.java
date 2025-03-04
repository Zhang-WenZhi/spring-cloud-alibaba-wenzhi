package com.wenzhi.leetcode_service.dao;

import com.wenzhi.leetcode_service.entity.JobTask;
import com.wenzhi.leetcode_service.entity.JobTaskLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface JobTaskDao {
    List<JobTask> selectEnabledTasks();

    @Update("UPDATE job_task SET cron_expression=#{cron}, interval_millis=#{intervalMillis}, "
            + "enabled=#{enabled}, version=version+1 WHERE id=#{id} AND version=#{version}")
    int updateWithLock(JobTask task);

    @Insert("INSERT INTO job_task_log(task_id, status, start_time, trigger_type, trigger_conf) "
            + "VALUES(#{taskId}, #{status}, #{startTime}, #{triggerType}, #{triggerConf})")
    void insertLog(JobTaskLog log);

    List<JobTask> findAllEnabled();

    /**
     * 删除指定时间之前的日志
     * @param threshold 时间阈值
     * @return 删除的记录数
     */
    int deleteLogsBefore(LocalDateTime threshold);

    // 新增方法
    JobTask selectById(Long id);
}
