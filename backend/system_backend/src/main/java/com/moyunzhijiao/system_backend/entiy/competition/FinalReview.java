package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName("final_review")
public class FinalReview {
    @MppMultiId
    private Integer teacherId;
    @MppMultiId
    private Integer submissionId;
    private Integer score;
}
