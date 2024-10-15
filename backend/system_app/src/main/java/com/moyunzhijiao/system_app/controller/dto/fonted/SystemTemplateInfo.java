package com.moyunzhijiao.system_app.controller.dto.fonted;

import lombok.Data;

import java.util.List;

@Data
public class SystemTemplateInfo {
    private int id; // 模板ID
    private String type; // 类型
    private String title; // 标题
    private List<String> content; // 内容图片
    private String typeface; // 字体

    // 构造函数
    public SystemTemplateInfo(int id, String type, String title, List<String> content, String typeface) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.typeface = typeface;
    }
}
