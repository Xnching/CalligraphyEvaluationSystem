package com.moyunzhijiao.system_backend.controller.resource;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.resource.Video;
import com.moyunzhijiao.system_backend.service.resource.VideoCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/video-collection")
@PreAuthorize("hasAuthority('视频合集管理')")
public class VideoCollectionController {

    @Autowired
    VideoCollectionService videoCollectionService;
//    @GetMapping("/videos")
//    public Result findByCollection(@RequestParam Integer collectionId){
//        List<VideoCollection> list = videoCollectionService.getByCollection(collectionId);
//        return Result.success(list);
//    }

    @GetMapping("/other-videos")
    public Result findOtherVideos(@RequestParam Integer collectionId){
        List<Video> videos = videoCollectionService.getOtherVideos(collectionId);
        return Result.success(videos);
    }

}
