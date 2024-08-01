package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.ArticleContents;
import com.moyunzhijiao.system_backend.mapper.ArticleContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleContentsService extends ServiceImpl<ArticleContentsMapper, ArticleContents> {

    @Autowired
    ArticleContentsMapper articleContentsMapper;
    public void addArticleContents(ArticleContents articleContents){
        articleContentsMapper.insert(articleContents);
    }
}
