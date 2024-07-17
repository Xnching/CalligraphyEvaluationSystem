package com.moyunzhijiao.system_backend.controller.dto;


import lombok.Data;
import java.util.List;
/*
 * StudentDTO用来接受前端学生登录时传递的用户名和密码
 * */
@Data
public class StudentDTO {
    private String name;
    private String password;
    private String loginId;
    private String token;
    //把当前登录用户的角色以及他的菜单项带出来
    private String userGroup;
}
