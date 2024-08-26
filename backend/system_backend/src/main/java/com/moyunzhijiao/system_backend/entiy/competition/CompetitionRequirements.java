package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("competition_requirements")
public class CompetitionRequirements {
    @TableId(type = IdType.INPUT)
    private Integer competitionId;
    private String requirements;
}
