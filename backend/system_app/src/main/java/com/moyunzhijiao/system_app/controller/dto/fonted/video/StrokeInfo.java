package com.moyunzhijiao.system_app.controller.dto.fonted.video;

import lombok.Data;

@Data
public class StrokeInfo {
    String stroke;
    int number;

    public StrokeInfo(String stroke, int number) {
        this.stroke = stroke;
        this.number = number;
    }

}
