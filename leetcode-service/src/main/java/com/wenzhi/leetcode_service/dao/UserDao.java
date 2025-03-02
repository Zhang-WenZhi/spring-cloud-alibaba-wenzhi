package com.wenzhi.leetcode_service.dao;

import com.wenzhi.leetcode_service.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper
public interface UserDao {
    Optional<UserEntity> findByUsername(String username);
}
