package com.moyunzhijiao.system_backend.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.resource.ArticleDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select({
            "<script>",
            "SELECT article.*, article_contents.content ",
            "   FROM article ",
            "   LEFT JOIN article_contents ON article.id = article_contents.id ",
            "WHERE 1=1 ",
            "<if test='isRecommended'> ",
            "   AND article.is_recommended = #{isRecommended} ",
            "</if>",
            "<if test='str != null and str != \"\"'> ",
            "   AND (article.name LIKE CONCAT('%', #{str}, '%') OR article.tag LIKE CONCAT('%', #{str}, '%')) ",
            "</if>",
            "<if test='secondTypeId != null'> ",
            "   AND article.second_type_id = #{secondTypeId} ",
            "</if>",
            "</script>"
    })
    IPage<ArticleDTO> selectPage(IPage<ArticleDTO> page, @Param("str") String str, @Param("secondTypeId") Integer secondTypeId, @Param("isRecommended") boolean isRecommended);

    @Select({
            "<script>",
            "SELECT IFNULL(COUNT(*), 0) ",
            "   FROM article ",
            "   LEFT JOIN article_contents ON article.id = article_contents.id ",
            "WHERE 1=1 ",
            "<if test='isRecommended'> ",
            "   AND article.is_recommended = #{isRecommended} ",
            "</if>",
            "<if test='str != null and str != \"\"'> ",
            "   AND (article.name LIKE CONCAT('%', #{str}, '%') OR article.tag LIKE CONCAT('%', #{str}, '%')) ",
            "</if>",
            "<if test='secondTypeId != null'> ",
            "   AND article.second_type_id = #{secondTypeId} ",
            "</if>",
            "</script>"
    })
    Integer countPage(IPage<ArticleDTO> page, @Param("str") String str, @Param("secondTypeId") Integer secondTypeId, @Param("isRecommended") boolean isRecommended);
}
