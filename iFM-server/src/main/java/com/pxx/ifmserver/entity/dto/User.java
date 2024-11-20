package com.pxx.ifmserver.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String userName;
    @JsonIgnore//隐藏不应该对外公开的字段 告诉Jackson序列化器在序列化Java对象到JSON字符串或者从JSON字符串反序列化到Java对象时忽略被注解的字段 防止敏感信息（如密码）被意外地暴露在客户端或者通过网络传输
    private String userPassword;
    private String userEmail;
    private String userPicture;
    private String userProfile;

    // 反序列化时使用的构造器或方法 使前端向后端传递user数据时,其password不至于为null
    @JsonProperty("userPassword")
    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
