package com.moyunzhijiao.system_frontend.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    Integer id;
    String name;
    String createdTime;
    String pictureUrl;
    String content;
}
