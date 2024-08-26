package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName("participant")
public class Participant {
    @MppMultiId
    private Integer divisionId;
    @MppMultiId
    private Integer studentId;
    private Integer competitionId;
    private Integer submissionId;
}
