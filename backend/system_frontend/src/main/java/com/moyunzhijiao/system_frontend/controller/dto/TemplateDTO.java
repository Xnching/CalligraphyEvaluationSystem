package com.moyunzhijiao.system_frontend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "接受模板信息")
public class TemplateDTO {
    String sonType;
    String name;
    Integer fontId;
    Integer difficulty;
    List<Integer> wordIds;
    String composing;
    String content;
    Integer copybookId;
    Integer wordCount;
    List<List<List<Integer>>> idArray;
}
