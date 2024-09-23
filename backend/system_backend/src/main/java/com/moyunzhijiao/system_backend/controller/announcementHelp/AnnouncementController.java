package com.moyunzhijiao.system_backend.controller.announcementHelp;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.Announcement;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.AnnouncementContent;
import com.moyunzhijiao.system_backend.entiy.back.User;
import com.moyunzhijiao.system_backend.service.announcementHelp.AnnouncementContentService;
import com.moyunzhijiao.system_backend.service.announcementHelp.AnnouncementService;
import com.moyunzhijiao.system_backend.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/announcement")

public class AnnouncementController {
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    AnnouncementContentService announcementContentService;
    @Autowired
    UserService userService;
    @PreAuthorize("hasAuthority('发布公告')")
    @PostMapping("/release")
    public Result releaseAnnouncement(@RequestHeader("token") String token,@RequestPart("file") MultipartFile file,@RequestPart("form") String announcementstr) {
        Announcement announcement = JSONUtil.toBean(announcementstr, Announcement.class);
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        User user = userService.getById(userId);
        announcement.setPublisher(user.getName());
        if (announcement.getReleaseType().equals("立即发布")) {
            // 获取当前时间并格式化为 MySQL DATETIME 格式
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            announcement.setReleaseTime(formattedNow);
        }
        announcementService.saveAnnouncement(file,announcement);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('查看公告')")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<Announcement> page = new Page<>(pageNum,pageSize);
        page = announcementService.page(page);
        return Result.success(page);
    }

    @PreAuthorize("hasAuthority('查看公告')")
    @GetMapping("/detail")
    public Result getDetail(@RequestParam Integer id){
        AnnouncementContent announcementContent = announcementContentService.getById(id);
        return Result.success(announcementContent);
    }

    @PreAuthorize("hasAuthority('查看公告')")
    @DeleteMapping("/delete")
    public Result deleteAnnouncement(@RequestBody Map<String,String> r){
        Integer announcementId = Integer.valueOf(r.get("id"));
        announcementService.removeById(announcementId);
        announcementContentService.removeById(announcementId);
        return Result.success();
    }
}
