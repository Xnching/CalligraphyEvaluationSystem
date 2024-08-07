package com.moyunzhijiao.system_backend.service.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.resource.Video;
import com.moyunzhijiao.system_backend.entiy.resource.VideoCollection;
import com.moyunzhijiao.system_backend.mapper.resource.VideoCollectionMapper;
import com.moyunzhijiao.system_backend.mapper.resource.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class VideoCollectionService extends ServiceImpl<VideoCollectionMapper, VideoCollection> {

    @Autowired
    VideoCollectionMapper videoCollectionMapper;
    @Autowired
    VideoMapper videoMapper;
    public void deleteVideoByCollectionId(Integer id){
        QueryWrapper<VideoCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id",id);
        videoCollectionMapper.delete(queryWrapper);
    }

    @Transactional
    public void updateByCollection(Integer collectionId,List<Video> list) {
        deleteVideoByCollectionId(collectionId);
        addBatch(collectionId,list);
    }


//    public List<VideoCollection> getByCollection(Integer collectionId) {
//        QueryWrapper<VideoCollection> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("collection_id",collectionId);
//        return list(queryWrapper);
//    }

    public List<Video> getVideoByCollection(Integer collectionId){
        return videoCollectionMapper.getVideos(collectionId);
    }

    @Transactional
    public void addBatch(Integer id, List<Video> list) {
        List<VideoCollection> videoCollections = IntStream.range(0, list.size())
                .mapToObj(i -> {
                    VideoCollection vc = new VideoCollection();
                    vc.setCollectionId(id);
                    vc.setVideoId(list.get(i).getId());
                    vc.setSequence(i + 1); // 设置顺序
                    return vc;
                })
                .collect(Collectors.toList());
        this.saveBatch(videoCollections);
    }

    public List<Video> getOtherVideos(Integer collectionId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.notInSql("id", "SELECT video_id FROM video_collection WHERE collection_id = " + collectionId);
        return videoMapper.selectList(queryWrapper);
    }
}
