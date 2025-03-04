package com.wenzhi.leetcode_service.dao;

import com.wenzhi.leetcode_service.entity.JobTaskLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JobTaskLogDao {
    @Insert("INSERT INTO job_task_log " +
            "(task_id, status, start_time, end_time, error_info, duration, trigger_type, trigger_conf) " +
            "VALUES (#{taskId}, #{status}, #{startTime}, #{endTime}, #{errorInfo}, #{duration}, " +
            "#{triggerType}, #{triggerConf})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLog(JobTaskLog log);

    @Select("SELECT * FROM job_task_log WHERE task_id = #{taskId} ORDER BY start_time DESC LIMIT #{limit}")
    List<JobTaskLog> selectRecentLogs(@Param("taskId") Long taskId, @Param("limit") int limit);
}
