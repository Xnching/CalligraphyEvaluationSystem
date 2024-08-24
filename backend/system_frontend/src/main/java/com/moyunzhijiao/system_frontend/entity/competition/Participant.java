package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "participant")
public class Participant {
    Integer division_id   ;
    Integer student_id    ;
    Integer submission_id ;
    Integer competition_id;
}

