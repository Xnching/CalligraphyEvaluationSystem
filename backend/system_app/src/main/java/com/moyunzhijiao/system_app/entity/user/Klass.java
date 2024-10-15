package com.moyunzhijiao.system_app.entity.user;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("klass")
public class Klass {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id; // 班级id

    String name; // 班级名

    int studentCount; // 班级人数

    int teacherId; // 所属教师id

    int gradeId; // 所属年级id

    int schoolId; // 所属学校id

    boolean deleteFlag; // 逻辑删除（0 未删除、1 删除）

    String year; // 入学年份

    String createdTime; // 创建时间
}
