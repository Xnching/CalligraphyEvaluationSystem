package com.moyunzhijiao.system_backend.controller;

import cn.hutool.core.lang.UUID;
import com.moyunzhijiao.system_backend.common.FileResponse;
import com.moyunzhijiao.system_backend.service.ConfigService;
import com.moyunzhijiao.system_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/set/editor-image")
    public FileResponse serveImage(@RequestPart("file") MultipartFile file){
        try {
            if (file.isEmpty()) {
                return FileResponse.error("File is empty");
            }
            // 生成唯一文件名
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            // 构造文件的路径
            String filePath = ConfigService.getEditorImageFilePath() + fileName;
            String url = ConfigService.getEditorImageUrl()+"/"+fileName;
            File dest = new File(filePath);
            // 保存文件到本地
            file.transferTo(dest);
            // 返回文件信息
            return FileResponse.success(url, fileName, "");
        } catch (IOException e) {
            e.printStackTrace();
            return FileResponse.error("Failed to store file");
        }
    }

    @GetMapping("/get/editor-image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveEditorImage(@PathVariable String filename){
        Resource resource = fileService.loadEditorImageAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/images/collection-picture/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCollectionPicture(@PathVariable String filename) {
        File projectRoot = new File(System.getProperty("user.dir"));
        String imageFilePath = projectRoot.getParentFile().getParent()+ "/resources/image/collectionPicture/" + filename;
        Resource resource = null;
        try {
            resource = new UrlResource(Paths.get(imageFilePath).toUri().toURL());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                        .body(resource);
            } else {
                throw new RuntimeException("找不到文件!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("错误: " + e.getMessage());
        }
    }

    @GetMapping("/images/templateWord/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveTemplateWord(@PathVariable String filename){
        Resource resource = fileService.loadTemplateWordAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/images/sampleWord/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveSampleWord(@PathVariable String filename){
        Resource resource = fileService.loadSampleWordAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/images/copybook/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCopybook(@PathVariable String filename){
        Resource resource = fileService.loadCopybookAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/videos/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveWriteWord(@PathVariable String filename){
        Resource resource = fileService.loadVideoAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("video/mp4")) // 设置Content-Type为视频类型
                .body(resource);
    }

    @GetMapping("/images/character/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCharacter(@PathVariable String filename){
        Resource resource = fileService.loadCharacterAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/images/stroke/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveStroke(@PathVariable String filename){
        Resource resource = fileService.loadStrokeAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/images/homework/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveHomework(@PathVariable String filename){
        Resource resource = fileService.loadHomeworkAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

    @GetMapping("/images/system-template/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveSystemTemplate(@PathVariable String filename){
        Resource resource = fileService.loadSystemTemplateAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 设置Content-Type为图片类型
                .body(resource);
    }

}
