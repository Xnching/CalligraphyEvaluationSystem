package com.moyunzhijiao.system_frontend.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideoDTO {
    Integer id;
    String name;
    String pictureUrl;
    String createdTime;
    String content;
    String tag;
    Integer type;//1是合集;0不是合集
    List<VideoCollectionDTO> collection;
}
