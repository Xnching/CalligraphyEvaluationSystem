package com.moyunzhijiao.system_frontend.controller.dto;

import com.moyunzhijiao.system_frontend.entity.homework.CharacterAnalysis;
import lombok.Data;

import java.util.List;

@Data
public class HomeworkSubmissionDetailDTO {
    String name;
    String type;
    String target;
    Integer wordCount;
    String font;
    Integer fontId;
    Integer difficulty;
    List<String> imgList;
    Integer systemScore;
    String systemFeedback;
    Integer teacherScore;
    String teacherFeedback;
    List<CharacterAnalysis> characterAnalysisList;
}
