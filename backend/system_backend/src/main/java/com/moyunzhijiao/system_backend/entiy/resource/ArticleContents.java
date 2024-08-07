package com.moyunzhijiao.system_backend.entiy.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="article_contents")
public class ArticleContents {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String content;
}
