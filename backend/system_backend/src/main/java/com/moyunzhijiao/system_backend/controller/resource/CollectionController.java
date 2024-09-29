package com.moyunzhijiao.system_backend.controller.resource;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.resource.CollectionDTO;
import com.moyunzhijiao.system_backend.service.resource.CollectionService;
import com.moyunzhijiao.system_backend.service.back.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/collection")
@PreAuthorize("hasAuthority('视频合集管理')")
public class CollectionController {
    @Autowired
    CollectionService collectionService;
    @Autowired
    UserServiceImpl userServiceImpl;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str, @RequestParam(required = false)boolean isRecommended){
        IPage<CollectionDTO> page ;
        page = collectionService.selectCollection(new Page<>(pageNum,pageSize),str,isRecommended);
        return Result.success(page);
    }

    @PutMapping("/delete")
    public Result deleteCollection(@RequestBody Map<String, String> params){
        Integer id = Integer.valueOf(params.get("id"));
        collectionService.deleteCollection(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateCollection(@RequestHeader("token") String token,@RequestBody CollectionDTO collectionDTO){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        String userId = jwt.getAudience().get(0);
        collectionDTO.setImporter(userServiceImpl.getNameById(userId));
        collectionService.updateCollectionAndRelationship(collectionDTO);
        return Result.success();
    }

    @PostMapping("/add")
    public Result addCollection(@RequestHeader("token") String token,@RequestPart("image") MultipartFile image, @RequestPart("collection") String collectionDTOStr){
        if(!image.isEmpty()){
            CollectionDTO collectionDTO = JSONUtil.toBean(collectionDTOStr, CollectionDTO.class);
            // 解码 token
            DecodedJWT jwt = JWT.decode(token);
            // 从载荷中获取用户 ID
            String userId = jwt.getAudience().get(0);
            collectionDTO.setImporter(userServiceImpl.getNameById(userId));
            collectionService.addCollection(image,collectionDTO);
            return Result.success();
        }else {
            return Result.error(Constants.CODE_400,"文件为空");
        }
    }
}
