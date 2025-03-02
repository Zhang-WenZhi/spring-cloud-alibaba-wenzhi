package com.wenzhi.leetcode_service.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 数据脱敏（Jackson 注解）
 * */
@Data
public class UserPasswordVO {
    private String userId;

    @JsonIgnore // 敏感字段不返回
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // 仅序列化
    private String phone;

    // 自定义脱敏逻辑
    public String getPhone() {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
