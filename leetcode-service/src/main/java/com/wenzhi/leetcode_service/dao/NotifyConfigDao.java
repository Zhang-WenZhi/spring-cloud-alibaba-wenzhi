package com.wenzhi.leetcode_service.dao;

import com.wenzhi.leetcode_service.entity.NotifyConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotifyConfigDao {
    @Select("SELECT * FROM notify_config WHERE task_id = #{taskId}")
    List<NotifyConfig> selectByTaskId(Long taskId);

    @Insert("INSERT INTO notify_config " +
            "(task_id, notify_type, receiver, template) " +
            "VALUES (#{taskId}, #{notifyType}, #{receiver}, #{template})")
    int insert(NotifyConfig config);
}

