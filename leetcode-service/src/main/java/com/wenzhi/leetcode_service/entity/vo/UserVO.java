package com.wenzhi.leetcode_service.entity.vo;

import lombok.Data;

/**
 * 数据脱敏（Jackson 注解）
 * */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
}