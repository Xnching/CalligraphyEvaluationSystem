package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value="grade")
public class Grade {
    Integer id           ;
    String name         ;
    Integer parent_id    ;
    Integer level        ;
    String updated_time ;
    @TableField(exist = false)
    List<Klass> list;
}
