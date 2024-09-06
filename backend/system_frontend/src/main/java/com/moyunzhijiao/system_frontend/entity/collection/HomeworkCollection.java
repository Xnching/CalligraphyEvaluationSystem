package com.moyunzhijiao.system_frontend.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "stu_submission_collection")
public class HomeworkCollection {
    Integer studentId;
    Integer submissionId;
    String type;
}
