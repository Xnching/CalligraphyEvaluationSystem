package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="stroke")
public class Stroke {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    @TableField(value="updated_time")
    String updatedTime;
}
