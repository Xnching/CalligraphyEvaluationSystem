package com.moyunzhijiao.system_app.controller.dto.fonted.video;

import lombok.Data;

@Data
public class LabelInfo {
    String name;

    // 构造函数
    public LabelInfo(String name) {
        this.name = name;
    }
}
