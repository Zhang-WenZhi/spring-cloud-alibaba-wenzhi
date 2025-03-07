package com.wenzhi.leetcode_service.entity.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 请求DTO
@Data
public class LoginRequestDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在 3 到 20 个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在 6 到 20 个字符之间")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).+$",
            message = "密码必须包含大小写字母、数字和特殊字符")
    private String password;
}
