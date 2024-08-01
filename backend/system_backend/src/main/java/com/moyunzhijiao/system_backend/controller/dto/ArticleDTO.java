package com.moyunzhijiao.system_backend.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ArticleDTO {
    Integer id;
    String name ;
    Integer firstTypeId;
    Integer secondTypeId;
    String tag;
    boolean isRecommended;
    String pictureUrl;
    String importer;
    String content;
}
