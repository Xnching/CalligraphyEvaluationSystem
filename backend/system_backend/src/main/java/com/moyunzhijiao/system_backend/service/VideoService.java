package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.Video;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {
    @Autowired
    VideoMapper videoMapper;
    @Transactional
    public void addVideo(MultipartFile image, MultipartFile videoFile, Video video) {
        File projectRoot = new File(System.getProperty("user.dir"));
        String videoFileName = UUID.randomUUID() + "-" +videoFile.getOriginalFilename();
        String imageFileName = UUID.randomUUID() + "-" +image.getOriginalFilename();
        String videoFilePath = projectRoot.getParentFile().getParent()+ "/frontend/system_frontend/public/videos/" + videoFileName;
        String imageFilePath = projectRoot.getParentFile().getParent()+ "/frontend/system_frontend/public/images/videoPicture/" + imageFileName;
        String tempVideoFilePath = "/videos/" + videoFileName;
        String tempImageFilePath = "/images/videoPicture/" + imageFileName;
        File videoDest = new File(videoFilePath);
        File imageDest = new File(imageFilePath);
        try {
            videoFile.transferTo(videoDest);
            image.transferTo(imageDest);
            if(video!=null){
                video.setContent(tempVideoFilePath);
                video.setPictureUrl(tempImageFilePath);
                videoMapper.insert(video);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统保存文件失败");
        }
    }

    public IPage<Video> selectPage(IPage<Video> page, String str, Integer secondTypeId, boolean isRecommended) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        queryWrapper.like("tag",str);
        if(ObjectUtil.isNotEmpty(secondTypeId)){
            queryWrapper.eq("second_type_id",secondTypeId);
        }
        if(ObjectUtil.isNotEmpty(isRecommended)){
            queryWrapper.eq("is_recommended",isRecommended);
        }
        return videoMapper.selectPage(page,queryWrapper);
    }

    public void deleteVideo(String id) {
        videoMapper.deleteById(id);
    }

    public List<Video> getAllVideos() {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        return list(queryWrapper);
    }
}
