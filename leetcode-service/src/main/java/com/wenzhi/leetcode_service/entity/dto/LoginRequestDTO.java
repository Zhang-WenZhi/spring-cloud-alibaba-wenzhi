package com.wenzhi.leetcode_service.entity.dto;


import lombok.Data;

// 请求DTO
@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
