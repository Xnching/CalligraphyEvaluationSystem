package com.moyunzhijiao.system_frontend.mapper.StudentHomepage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.entity.StudentHomepage.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ArticleMapper extends BaseMapper<Article> {
    @Select("SELECT id, name, picture_url, created_time " +
            "FROM article " +
            "WHERE is_recommended = 1 ")
    public IPage<ArticleDTO> getRecommend(IPage<ArticleDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM article " +
            "WHERE is_recommended = 1 ")
    public Integer countRecommend();

    @Select("SELECT id, name, picture_url, created_time " +
            "FROM article " +
            "WHERE second_type_id = 7 ")
    public IPage<ArticleDTO> getBiography(IPage<ArticleDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM article " +
            "WHERE second_type_id = 7 ")
    public Integer countBiography();

    @Select("SELECT id, name, picture_url, created_time " +
            "FROM article " +
            "WHERE second_type_id = 5 ")
    public IPage<ArticleDTO> getMaster(IPage<ArticleDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM article " +
            "WHERE second_type_id = 5 ")
    public Integer countMaster();

    @Select("SELECT id, name, picture_url, created_time " +
            "FROM article " +
            "WHERE second_type_id = 6 ")
    public IPage<ArticleDTO> getCharacter(IPage<ArticleDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM article " +
            "WHERE second_type_id = 6 ")
    public Integer countCharacter();

    @Select("SELECT id, name, picture_url, created_time " +
            "FROM article " +
            "WHERE second_type_id = 8 ")
    public IPage<ArticleDTO> getCulture(IPage<ArticleDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM article " +
            "WHERE second_type_id = 8 ")
    public Integer countCulture();

    @Select("SELECT id, name, picture_url, created_time " +
            "FROM article " +
            "WHERE name LIKE CONCAT('%', #{name}, '%')")
    public IPage<ArticleDTO> getAllArticle(IPage<ArticleDTO> page, @Param("name") String name);

    @Select("SELECT COUNT(*) " +
            "FROM article " +
            "WHERE name LIKE CONCAT('%', #{name}, '%')")
    public Integer countAllArticle(@Param("name") String name);

    @Select("SELECT id, content " +
            "FROM article_contents " +
            "WHERE id=#{id} ")
    public ArticleDTO getDetail(String id);
}
