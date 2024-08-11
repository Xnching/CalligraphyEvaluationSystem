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

    @Operation(summary = "获取模板字,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/images/templateWord/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveTemplateWord(@PathVariable String filename){
        Resource resource = fileService.loadTemplateWordAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @Operation(summary = "获取字帖,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/images/copybook/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCopybook(@PathVariable String filename){
        Resource resource = fileService.loadCopybookAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @Operation(summary = "获取字的生成视频,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/videos/writeWord/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveWriteWord(@PathVariable String filename){
        Resource resource = fileService.loadWriteWordAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("video/mp4")) // 设置Content-Type为视频类型
                .body(resource);
    }

    @Operation(summary = "获取字的分析的图片,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/images/character/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCharacter(@PathVariable String filename){
        Resource resource = fileService.loadCharacterAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @Operation(summary = "获取笔画分析,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/images/stroke/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveStroke(@PathVariable String filename){
        Resource resource = fileService.loadStrokeAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @Operation(summary = "获取作业图片,所有文件都存储在了与前后端文件同级的resources里了")
    @GetMapping("/images/homework/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveHomework(@PathVariable String filename){
        Resource resource = fileService.loadHomeworkAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }


}
