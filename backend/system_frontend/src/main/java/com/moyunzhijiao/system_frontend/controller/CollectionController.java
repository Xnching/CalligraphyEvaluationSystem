package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KnowledgeCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VideoCollectionDTO;
import com.moyunzhijiao.system_frontend.service.collection.StuCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CollectionController {
    @Autowired
    StuCollectionService stuCollectionService;

    @PostMapping("/cieps/knowledge-collection")
    public Result addKnowledgeCollection(@RequestHeader("authorization") String token,
                                         @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.addKnowledgeCollection(stuId,id)){
            return Result.success();
        }else{
            return Result.error();
        }
    }
    @DeleteMapping ("/cieps/knowledge-collection")
    public Result delKnowledgeCollection(@RequestHeader("authorization") String token,
                                         @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.delKnowledgeCollection(stuId,id)){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    @GetMapping("/cieps/knowledge-collection")
    public Result queryKnowledgeStatus(@RequestHeader("authorization") String token,
                              @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.queryKnowledgeCollection(stuId,id)){
            Boolean flag=true;
            return Result.success(flag);
        }else{
            Boolean flag=false;
            return Result.success(flag);
        }
    }

    @PostMapping("/cieps/video-collection")
    public Result addVideoCollection(@RequestHeader("authorization") String token,
                                         @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.addVideoCollection(stuId,id)){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    @DeleteMapping ("/cieps/video-collection")
    public Result delVideoCollection(@RequestHeader("authorization") String token,
                                         @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.delVideoCollection(stuId,id)){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    @GetMapping("/cieps/video-collection")
    public Result queryVideoStatus(@RequestHeader("authorization") String token,
                                       @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.queryVideoCollection(stuId,id)){
            Boolean flag=true;
            return Result.success(flag);
        }else{
            Boolean flag=false;
            return Result.success(flag);
        }
    }

    @PostMapping("/cieps/homework-collection")
    public Result addHomeworkCollection(@RequestHeader("authorization") String token,
                                        @RequestParam Integer id,
                                        @RequestParam String type){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.addHomeworkCollection(stuId,id,type)){
            return Result.success();
        }else{
            return Result.error();
        }
    }
    @DeleteMapping ("/cieps/homework-collection")
    public Result delHomeworkCollection(@RequestHeader("authorization") String token,
                                     @RequestParam Integer id){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.delHomeworkCollection(stuId,id)){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    @GetMapping("/cieps/homework-collection")
    public Result queryHomeworkStatus(@RequestHeader("authorization") String token,
                                      @RequestParam Integer id,
                                      @RequestParam String type){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if(stuCollectionService.queryHomeworkCollection(stuId,id,type)){
            Boolean flag=true;
            return Result.success(flag);
        }else{
            Boolean flag=false;
            return Result.success(flag);
        }
    }

    @GetMapping("/cieps/homework-collection-list")
    public Result getHomeworkCollection(@RequestHeader("authorization") String token,
                                        @RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<HomeworkCollectionDTO> page=stuCollectionService.getHomeworkCollection(stuId,currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/cieps/knowledge-collection-list")
    public Result getKnowledgeCollection(@RequestHeader("authorization") String token,
                                        @RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<KnowledgeCollectionDTO> page=stuCollectionService.getKnowledgeCollection(stuId,currentPage,pageSize);
        return Result.success(page);
    }
    @GetMapping("/cieps/video-collection-list")
    public Result getVideoCollection(@RequestHeader("authorization") String token,
                                         @RequestParam Integer currentPage,
                                         @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<VideoCollectionDTO> page=stuCollectionService.getVideoCollection(stuId,currentPage,pageSize);
        return Result.success(page);
    }

}
