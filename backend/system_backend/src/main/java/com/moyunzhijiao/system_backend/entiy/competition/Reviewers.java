package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName("reviewers")
public class Reviewers {
    @MppMultiId
    private Integer divisionId;
    @MppMultiId
    private Integer teacherId;
    boolean isUrged;

    public Reviewers(){

    }
    public Reviewers(Integer teacherId, Integer divisionId) {
        this.divisionId = divisionId;
        this.teacherId = teacherId;
    }
}