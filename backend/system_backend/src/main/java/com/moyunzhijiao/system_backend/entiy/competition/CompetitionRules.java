package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("competition_rules")
public class CompetitionRules {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String question;
    private Integer answer;
    private String updatedTime;
}