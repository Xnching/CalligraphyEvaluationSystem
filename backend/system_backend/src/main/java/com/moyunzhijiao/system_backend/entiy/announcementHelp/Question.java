package com.moyunzhijiao.system_backend.entiy.announcementHelp;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="question")
public class Question {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String q;
    String a;
    String editor;
}
