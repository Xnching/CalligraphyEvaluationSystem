package com.moyunzhijiao.system_backend.entiy.outstanding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "unreviewed_outstanding")
public class UnreviewedOutstanding {
    @TableId(value = "submissions_id",type = IdType.INPUT)
    Integer submissionId;
    @TableField("recommender_id")
    Integer recommenderId;
    @TableField("updated_time")
    String updatedTime;
}
