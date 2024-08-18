package com.moyunzhijiao.system_backend.service.announcementHelp;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.Announcement;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.AnnouncementContent;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.announcementHelp.AnnouncementMapper;
import com.moyunzhijiao.system_backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AnnouncementService extends ServiceImpl<AnnouncementMapper, Announcement> {
    @Autowired
    AnnouncementContentService announcementContentService;
    public void saveAnnouncement(MultipartFile file, Announcement announcement) {
        //保存文件
        String fileName = UUID.randomUUID() + "-" +file.getOriginalFilename();
        String filePath = ConfigService.getAnnouncementFilePath()+fileName;
        String url = ConfigService.getAnnouncementUrl()+"/"+fileName;
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
            announcement.setPictureUrl(url);
            save(announcement);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_401,"文件上传失败");
        }
        //保存数据到数据库
        AnnouncementContent announcementContent = new AnnouncementContent();
        announcementContent.setAnnouncementId(announcement.getId());
        announcementContent.setMessage(announcement.getContent());
        announcementContentService.save(announcementContent);
    }
}
