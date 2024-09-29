package com.moyunzhijiao.system_frontend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Schema(description = "教师通过模板发布作业")
@Data
public class PublishByTemplateDTO {
    Integer templateId;
    String templateType;
    List<Integer> list;
    Description description;
    @Data
    public static class Description{
        String name;
        String require;
        String target;
        String deadline;
    }

}
