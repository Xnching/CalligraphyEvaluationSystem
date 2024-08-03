package com.moyunzhijiao.system_backend.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moyunzhijiao.system_backend.entiy.Video;
import com.moyunzhijiao.system_backend.entiy.VideoCollection;
import lombok.Data;

import java.util.List;

@Data
public class CollectionDTO {
    Integer id;
    String name ;
    Integer firstTypeId;
    Integer secondTypeId;
    String tag;
    boolean isRecommended;
    String summary;
    String importer;
    String pictureUrl;
    List<Video> list;
}
