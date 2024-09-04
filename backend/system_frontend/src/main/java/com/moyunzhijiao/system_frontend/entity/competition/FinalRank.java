package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="final_rank")
public class FinalRank {
    Integer submissionId;
    Integer rk           ;
    Integer score        ;
    String updatedTime ;
    String level        ;
}
