package com.moyunzhijiao.system_backend.controller.dto.word;

import lombok.Data;

import java.util.List;

@Data
public class SystemTemplateDTO {
    String name;
    String detailType;
    List<Integer> wordIds;
    Integer wordCount;
    Integer fontId;
    String composing;
    Integer difficulty;
    Integer copybookId;
    List<List<List<Integer>>> idArray;
}
