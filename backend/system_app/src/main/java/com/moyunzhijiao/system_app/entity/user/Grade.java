package com.moyunzhijiao.system_app.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("grade")
public class Grade {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id; // 学制年级id

    String name; // 学制年级名

    int parentId; // 上一级id

    byte level; // 等级

    String updatedTime; // 更新时间
}

