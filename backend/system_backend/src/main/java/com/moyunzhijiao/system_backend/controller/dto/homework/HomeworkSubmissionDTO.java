package com.moyunzhijiao.system_backend.controller.dto.homework;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class HomeworkSubmissionDTO {
    Integer id;
    Integer systemScore;
    String systemFeedback;
    Integer teacherScore;
    String teacherFeedback;
    List<String> imageSrcList;
}
