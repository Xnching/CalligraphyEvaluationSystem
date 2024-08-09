package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkImage;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkImageService extends ServiceImpl<HomeworkImageMapper, HomeworkImage> {
    /*
    * 给一个作业批量添加作业内容url
    * */
    public void addBatch(Integer homeworkId, List<String> images) {
        List<HomeworkImage> list = images.stream().map(image ->{
            HomeworkImage homeworkImage = new HomeworkImage();
            homeworkImage.setHomeworkId(homeworkId);
            homeworkImage.setPictureUrl(image);
            return homeworkImage;
        }).toList();
        saveBatch(list);
    }
}
