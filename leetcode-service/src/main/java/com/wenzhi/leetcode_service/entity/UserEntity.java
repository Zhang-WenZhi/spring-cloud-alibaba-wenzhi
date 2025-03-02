package com.wenzhi.leetcode_service.entity;

import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
}

