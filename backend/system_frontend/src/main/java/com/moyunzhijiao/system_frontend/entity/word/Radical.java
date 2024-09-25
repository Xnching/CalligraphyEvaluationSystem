package com.moyunzhijiao.system_frontend.entity.word;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "radical")
public class Radical {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    Integer count;
    @TableField(value="updated_time")
    String updatedTime;
}
