package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkImage;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkImageMapper;
import com.moyunzhijiao.system_frontend.service.ConfigService;
import com.moyunzhijiao.system_frontend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkImageService extends ServiceImpl<HomeworkImageMapper, HomeworkImage> {
    @Autowired
    HomeworkImageMapper homeworkImageMapper;
    /*
    * 给一个作业批量添加作业内容url
    * */
    @Transactional
    public void addBatch(Integer homeworkId, List<String> images) {
        List<HomeworkImage> list = images.stream().map(image ->{
            HomeworkImage homeworkImage = new HomeworkImage();
            homeworkImage.setHomeworkId(homeworkId);
            homeworkImage.setPictureUrl(image);
            return homeworkImage;
        }).toList();
        saveBatch(list);
    }

    /*
    * 获取一个作业的所有图片
    * */
    public List<String> getUrlBatch(Integer homeworkId){
        QueryWrapper<HomeworkImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        List<HomeworkImage> list = list(queryWrapper);
        return list.stream().map(HomeworkImage::getPictureUrl).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByHomework(Integer homeworkId) {
        QueryWrapper<HomeworkImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        List<HomeworkImage> imageList = homeworkImageMapper.selectList(queryWrapper);
        imageList.forEach(homeworkImage -> {
            String name = FileService.extractFileName(homeworkImage.getPictureUrl());
            String path = ConfigService.getHomeworkFilePath()+name;
            FileService.deleteFile(path);
        });
        homeworkImageMapper.delete(queryWrapper);
    }
}
