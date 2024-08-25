package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("division_requirements")
public class DivisionRequirements {
    @TableId(type = IdType.INPUT)
    private Integer divisionId;
    private String requirements;
}