package com.moyunzhijiao.system_frontend.entity.homework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "character_analysis")
public class CharacterAnalysis {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("submission_id")
    Integer submissionId;
    String name;
    Integer score;
    String evaluation;
    String picture;
    @TableField("created_time")
    String createdTime;
    @TableField(exist = false)
    List<StrokeAnalysis> strokeList;
    @TableField(exist = false)
    String videoUrl;
    @TableField(exist = false)
    String templateWordUrl;
}
