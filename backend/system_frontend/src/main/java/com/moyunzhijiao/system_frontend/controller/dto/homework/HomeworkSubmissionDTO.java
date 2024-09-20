package com.moyunzhijiao.system_frontend.controller.dto.homework;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Schema(description = "作业作品详情")
public class HomeworkSubmissionDTO {
    Integer id;
    String name;
    String type;
    Integer wordCount;
    String font;
    Integer difficulty;
    @Schema(description = "作业原图列表")
    List<String> imgList;
    Integer systemScore;
    String systemFeedback;
    Integer teacherScore;
    String teacherFeedback;
    String deadline;
    String target;
    BigDecimal score;
    String submitTime;
}
