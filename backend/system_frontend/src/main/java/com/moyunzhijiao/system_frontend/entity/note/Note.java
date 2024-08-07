package com.moyunzhijiao.system_frontend.entity.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "note")
public class Note {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String type;
    String sender;
    @TableField(value = "association_id")
    Integer associationId;
    @TableField(value = "created_time")
    String createdTime;
}
