package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="sample_word")
public class SampleWord {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    @TableField(value="structure_id")
    Integer structureId;
    @TableField(value="font_id")
    Integer fontId;
    @TableField(value="radical_id")
    Integer radicalId;
    String content;     //图片的url
    String importer;
    @TableField(value="grade_id")
    Integer gradeId;
    String source;
    @TableField(value="created_time")
    String createdTime;

}
