package com.moyunzhijiao.system_frontend.controller.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "班级里的学生详情")
@Data
public class StuInKlassDTO {
    IPage<HomeworkSubmissionDTO> submitedHomeworkList;
    IPage<HomeworkSubmissionDTO> unSubmitedHomeworkList;
}
