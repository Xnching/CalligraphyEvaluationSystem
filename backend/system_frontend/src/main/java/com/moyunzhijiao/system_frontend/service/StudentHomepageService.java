package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VedioCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VideoDTO;
import com.moyunzhijiao.system_frontend.mapper.StudentHomepage.ArticleMapper;
import com.moyunzhijiao.system_frontend.mapper.Competition.CompetitionMapper;
import com.moyunzhijiao.system_frontend.mapper.resource.VideoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentHomepageService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    VideoMapper videoMapper;

    public IPage<ArticleDTO> getRecommend(Integer currentPage,Integer pageSize){
        IPage<ArticleDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= articleMapper.getRecommend(page);
        total= articleMapper.countRecommend();
        page.setTotal(total);
        return page;
    }

    public IPage<ArticleDTO> getBiography(Integer currentPage,Integer pageSize){
        IPage<ArticleDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= articleMapper.getBiography(page);
        total= articleMapper.countBiography();
        page.setTotal(total);
        return page;
    }

    public IPage<ArticleDTO> getMaster(Integer currentPage,Integer pageSize){
        IPage<ArticleDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= articleMapper.getMaster(page);
        total= articleMapper.countMaster();
        page.setTotal(total);
        return page;
    }

    public IPage<ArticleDTO> getCharacter(Integer currentPage,Integer pageSize){
        IPage<ArticleDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= articleMapper.getCharacter(page);
        total= articleMapper.countCharacter();
        page.setTotal(total);
        return page;
    }

    public IPage<ArticleDTO> getCulture(Integer currentPage,Integer pageSize){
        IPage<ArticleDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= articleMapper.getCulture(page);
        total= articleMapper.countCulture();
        page.setTotal(total);
        return page;
    }

    public IPage<ArticleDTO> getAllArticle(Integer currentPage,Integer pageSize,String name){
        IPage<ArticleDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= articleMapper.getAllArticle(page,name);
        total= articleMapper.countAllArticle(name);
        page.setTotal(total);
        return page;
    }

    public ArticleDTO getArticleDetail(String id){
        ArticleDTO articleDTO=articleMapper.getDetail(id);
        return articleDTO;
    }

    public IPage<VideoDTO> getVideo(Integer currentPage, Integer pageSize, String name){
        IPage<VideoDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= videoMapper.getVideo(page,name);
        total= videoMapper.countVideo(name);
        page.setTotal(total);
        return page;
    }

    public VideoDTO getVideoDetail(String id){
        // 1. 获取视频信息
        VideoDTO video = videoMapper.getVideoDetail(id);
        if (video == null) {
            return null; // 或抛出异常，取决于你的业务逻辑
        }

        VideoDTO videoDTO = new VideoDTO();
        BeanUtils.copyProperties(video, videoDTO);

        // 2. 查询该视频所属的合集
        List<String> collectionIds = videoMapper.selectCollectionIdsByVideoId(id);

        // 3. 如果视频属于合集，则查找同属该合集的其他视频
        if (!collectionIds.isEmpty()) {
            videoDTO.setType(1); // 设置类型为合集
            List<VedioCollectionDTO> collectionNames = new ArrayList<>();
            for (String collectionId : collectionIds) {
                List<VedioCollectionDTO> videoNames = videoMapper.selectVideoNamesByCollectionId(collectionId);
                collectionNames.addAll(videoNames);
            }
            videoDTO.setCollection(collectionNames);
        } else {
            videoDTO.setType(0); // 设置类型为非合集
        }

        return videoDTO;
    }

    public IPage<CompetitionDTO> getSelfCompetition(Integer currentPage, Integer pageSize){
        IPage<CompetitionDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= competitionMapper.getSelfCompetition(page);
        total= competitionMapper.countSelfCompetition();
        page.setTotal(total);
        return page;
    }

    public CompetitionDTO getCompetitionDetail(String id){
        CompetitionDTO competitionDTO=competitionMapper.getDetail(id);
        return competitionDTO;
    }
}
