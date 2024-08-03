package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="question")
public class Question {
    Integer id;
    String q;
    String a;
}
