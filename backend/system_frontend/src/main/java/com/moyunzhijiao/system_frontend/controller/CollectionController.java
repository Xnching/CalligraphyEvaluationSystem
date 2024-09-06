package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_frontend.common.Result;
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
    public Result queryStatus(@RequestHeader("authorization") String token,
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
}
