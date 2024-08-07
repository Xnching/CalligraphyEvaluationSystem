package com.moyunzhijiao.system_backend.entiy.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value="academic_year")
public class AcademicYear {
    Integer id;
    @TableField(value = "first_start")
    Date firstStart;
    @TableField(value = "first_end")
    Date firstEnd;
    @TableField(value = "second_start")
    Date secondStart;
    @TableField(value = "second_end")
    Date secondEnd;
}
