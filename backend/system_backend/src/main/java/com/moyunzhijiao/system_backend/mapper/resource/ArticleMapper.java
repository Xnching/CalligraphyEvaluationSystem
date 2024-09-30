package com.moyunzhijiao.system_backend.mapper.resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.resource.ArticleDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select({
            "<script>",
            "SELECT article.*, article_contents.content ",
            "   FROM article ",
            "   LEFT JOIN article_contents ON article.id = article_contents.id ",
            "WHERE 1=1 " ,
            "   ${ew.customSqlSegment}",
            "<if test='str != null and str != \"\"'> ",
            "   AND (article.name LIKE CONCAT('%', #{str}, '%') OR article.tag LIKE CONCAT('%', #{str}, '%')) ",
            "</if>",
            "</script>"
    })
    IPage<ArticleDTO> selectPage(IPage<ArticleDTO> page, @Param("str") String str, @Param("ew") LambdaQueryWrapper<Article> queryWrapper);

    @Select({
            "<script>",
            "SELECT IFNULL(COUNT(*), 0) ",
            "   FROM article ",
            "   LEFT JOIN article_contents ON article.id = article_contents.id ",
            "WHERE 1=1 ",
            "   ${ew.customSqlSegment}",
            "<if test='str != null and str != \"\"'> ",
            "   AND (article.name LIKE CONCAT('%', #{str}, '%') OR article.tag LIKE CONCAT('%', #{str}, '%')) ",
            "</if>",
            "</script>"
    })
    Integer countPage(IPage<ArticleDTO> page, @Param("str") String str, @Param("ew") LambdaQueryWrapper<Article> queryWrapper);

}
