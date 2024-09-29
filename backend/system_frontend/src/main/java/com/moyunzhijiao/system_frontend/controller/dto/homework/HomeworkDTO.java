package com.moyunzhijiao.system_frontend.controller.dto.homework;

import lombok.Data;

import java.util.List;

@Data
public class HomeworkDTO {
    Integer id;
    String name;
    String deadline;
    Integer unSubmit;
    String target;
    private List<Integer> list;
    //下面是用来创建专项作业所要用到的
    private List<Integer> wordId;
    private Description description;
    //字帖作业需要的
    Integer copybookId;
    //综合作业需要的
    List<String> contentList;
    Integer wordCount;
    String composing;
    @Data
    public static class Description {
        private String name;
        private String require;
        private String type;
        private String fontId;
        private String difficulty;
        private String target;
        private String deadline;
    }
}
