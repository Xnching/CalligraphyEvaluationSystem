package com.moyunzhijiao.system_frontend.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "班级详情内容")
@Data
public class KlassDetailDTO {
    List<Homework> deadlineList;
    IPage<Stu> page;
    @Data
    @TableName(value = "student")
    public static class Stu{
        String name;
        String gender;
        @TableId(value = "id",type = IdType.AUTO)
        Integer stuId;
        String stuno;
    }

}
