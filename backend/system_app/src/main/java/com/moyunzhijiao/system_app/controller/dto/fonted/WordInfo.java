package com.moyunzhijiao.system_app.controller.dto.fonted;

import com.moyunzhijiao.system_app.controller.dto.fonted.video.StrokeInfo;
import lombok.Data;

import java.util.List;

@Data
public class WordInfo {
//    public Integer id;   //字的Id
    public String name; //字的名称
    public String word; // 拆出的字的图
    public List<StrokeInfo> strokes; // 字拆出的笔画
    public int strokeNumber; // 字得分
    public String templateWord; // 样本字
    public String comment; // 字的评语

    public WordInfo(/*Integer id, */String name, String word, List<StrokeInfo> strokes, int strokeNumber, String templateWord, String comment) {
//        this.id = id;
        this.name = name;
        this.word = word;
        this.strokes = strokes;
        this.strokeNumber = strokeNumber;
        this.templateWord = templateWord;
        this.comment = comment;
    }

}
