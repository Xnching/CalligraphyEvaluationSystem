package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="stroke")
public class Stroke {
    Integer id;
    String name;
    @TableField(value="updated_time")
    String updatedTime;
}
