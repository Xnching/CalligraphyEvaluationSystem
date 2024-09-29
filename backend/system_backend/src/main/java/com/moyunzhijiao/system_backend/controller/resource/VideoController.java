package com.moyunzhijiao.system_backend.controller.resource;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.resource.Video;
import com.moyunzhijiao.system_backend.service.back.UserServiceImpl;
import com.moyunzhijiao.system_backend.service.resource.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/video")
public class VideoController {

    @Autowired
    VideoService videoService;
    @Autowired
    UserServiceImpl userServiceImpl;
    @PreAuthorize("hasAuthority('添加书法知识')")
    @PostMapping("/add")
    public Result addVideo(@RequestPart("image") MultipartFile image,@RequestHeader("token") String token,
                           @RequestPart("videoFile") MultipartFile videoFile, @RequestPart("videoData") String videoStr){
        if (videoFile.isEmpty()) {
            return Result.error(Constants.CODE_400,"视频文件为空");
        }
        if (image.isEmpty()) {
            return Result.error(Constants.CODE_400,"视频封面为空");
        }
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        String userId = jwt.getAudience().get(0);
        Video video = JSONUtil.toBean(videoStr, Video.class);
        video.setImporter(userServiceImpl.getNameById(userId));
        videoService.addVideo(image,videoFile,video);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('资源管理')")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str,@RequestParam(required = false)Integer secondTypeId,
                           @RequestParam(required = false)boolean isRecommended){
        IPage<Video> page = new Page<>(pageNum,pageSize);
        page = videoService.selectPage(page,str,secondTypeId,isRecommended);
        return Result.success(page);
    }

    @PreAuthorize("hasAuthority('资源管理')")
    @PutMapping("/delete")
    public Result deleteVideo(@RequestBody Map<String, String> params){
        String id = params.get("id");
        videoService.deleteVideo(id);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('资源管理')")
    @PutMapping("/update")
    public Result updateVideo(@RequestHeader("token") String token,@RequestBody Video video){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        String userId = jwt.getAudience().get(0);
        video.setImporter(userServiceImpl.getNameById(userId));
        videoService.updateById(video);
        return Result.success();
    }

    @GetMapping("/videos")
    public Result findAllVideos(){
        List<Video> list = videoService.getAllVideos();
        return Result.success(list);
    }
}
