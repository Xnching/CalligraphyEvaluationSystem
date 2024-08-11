package com.moyunzhijiao.system_frontend.service.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.resource.Video;
import com.moyunzhijiao.system_frontend.mapper.resource.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    CalligraphyFactsService calligraphyFactsService;
    /*
    * 根据字的名字找到对应的书写字的视频,返回其url
    * */
    public String getVideoUrlByWriteWord(String wordName){
        //找到字的书写的id
        Integer writeWordId = calligraphyFactsService.getWriteWordId();
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",wordName);
        queryWrapper.eq("second_type_id",writeWordId);
        Video video = videoMapper.selectOne(queryWrapper);
        if(video!=null){
            return video.getContent();
        }
        return null;
    }
}
