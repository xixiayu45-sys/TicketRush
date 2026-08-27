package com.ticketrush.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2,max = 8,message = "用户名长度必须为2-8之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 11,message = "密码长度必须为6-11之间")
    private String password;

}
