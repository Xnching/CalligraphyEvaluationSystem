package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("final_rank")
public class FinalRank {
    @TableId(type = IdType.INPUT)
    private Integer submissionId;
    private Integer rk;
    private String level;
    private Byte score;
    private String updatedTime;
    private Integer divisionId;
    public FinalRank(){}
    public FinalRank(Integer submissionId){
        this.submissionId = submissionId;
    }
}
