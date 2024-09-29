package com.moyunzhijiao.system_frontend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "教师前端交互类")
public class TeacherDTO {
    Integer id;
    String name;
    String email;
    String phone;
    @Schema(description = "工号")
    String workno;
    String password;
    @Schema(description = "此为所在位置字符串")
    String area;
    @Schema(description = "所在学校，没有所在年级")
    String school;
    Integer schoolId;
    String grade;
    String avatar;  //头像url
    @Schema(description = "此为所在地区的id")
    Integer regionId;
    String token;
    //验证验证码用的
    String code;
    String uuid;
}
