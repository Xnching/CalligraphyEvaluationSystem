package com.moyunzhijiao.system_frontend.entity.homework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "stroke_analysis")
public class StrokeAnalysis {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("character_analysis_id")
    Integer characterAnalysisId;
    Integer score;
    String picture;
    @TableField("created_time")
    String createdTime;
}
