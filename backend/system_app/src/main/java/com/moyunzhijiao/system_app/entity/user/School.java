package com.moyunzhijiao.system_app.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school")
public class School {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id; // 学校id

    String name; // 学校名

    String type; // 学校类型

    String leader; // 校负责人

    @TableField("region_id")
    int regionId; // 区域id

    String address; // 地址

    String leaderPhone; // 联系方式

    int studentCount; // 学生人数

    boolean deleteFlag; // 逻辑删除（0 未删除、1 删除）

    String createdTime; // 创建时间

    int teacherCount; // 教师人数

    int maxRegionId; // 省份id

    int nextRegionId; // 市级id
}


