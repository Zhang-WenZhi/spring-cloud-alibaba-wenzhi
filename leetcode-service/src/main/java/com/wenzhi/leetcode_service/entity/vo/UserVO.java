package com.wenzhi.leetcode_service.entity.vo;

import lombok.Data;

/**
 * 数据脱敏（Jackson 注解）
 * */
@Data
public class UserVO {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String phone;
}