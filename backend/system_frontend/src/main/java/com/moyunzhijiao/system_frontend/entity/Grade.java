package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="grade")
public class Grade {
    Integer id           ;
    String name         ;
    Integer parent_id    ;
    Integer level        ;
    String updated_time ;
}
