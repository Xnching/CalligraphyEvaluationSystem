package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "competition_requirements")
public class CompetitionRequirements {
    @TableId(value = "competition_id",type = IdType.INPUT)
    Integer competitionId;
    String requirements;
}
