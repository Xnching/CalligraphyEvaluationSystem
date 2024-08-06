package com.moyunzhijiao.system_frontend.controller;

import com.moyunzhijiao.system_frontend.service.ConfigService;
import com.moyunzhijiao.system_frontend.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Paths;

@RestController
@Tag(name = "文件获取接口")
@RequestMapping("/upload")
public class FileController {

    @Autowired
    FileService fileService;

    @Operation(summary = "获取头像,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/images/avatar/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveAvatar(@PathVariable String filename){
        Resource resource = fileService.loadAvatarAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }
}
