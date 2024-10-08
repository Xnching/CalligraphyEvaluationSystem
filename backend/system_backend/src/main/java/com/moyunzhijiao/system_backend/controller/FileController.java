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
        return serveResource(fileService.loadEditorImageAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/collection-picture/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCollectionPicture(@PathVariable String filename) {
        return serveResource(fileService.loadCollectionAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/templateWord/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveTemplateWord(@PathVariable String filename) {
        return serveResource(fileService.loadTemplateWordAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/sampleWord/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveSampleWord(@PathVariable String filename) {
        return serveResource(fileService.loadSampleWordAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/copybook/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCopybook(@PathVariable String filename) {
        return serveResource(fileService.loadCopybookAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/videos/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveWriteWord(@PathVariable String filename) {
        return serveResource(fileService.loadVideoAsResource(filename), MediaType.parseMediaType("video/mp4"));
    }

    @GetMapping("/images/video/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveVideoImage(@PathVariable String filename) {
        return serveResource(fileService.loadVideoImageAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/character/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCharacter(@PathVariable String filename) {
        return serveResource(fileService.loadCharacterAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/custom-template/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCustomTemplate(@PathVariable String filename) {
        return serveResource(fileService.loadCustomTemplateAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/stroke/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveStroke(@PathVariable String filename) {
        return serveResource(fileService.loadStrokeAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/announcement/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveAnnouncement(@PathVariable String filename) {
        return serveResource(fileService.loadAnnouncementAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/competition/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCompetition(@PathVariable String filename) {
        return serveResource(fileService.loadCompetitionAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/homework/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveHomework(@PathVariable String filename) {
        return serveResource(fileService.loadHomeworkAsResource(filename), MediaType.IMAGE_JPEG);
    }
    @GetMapping("/images/system-template/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveSystemTemplate(@PathVariable String filename){
        return serveResource(fileService.loadSystemTemplateAsResource(filename), MediaType.IMAGE_JPEG);
    }

    @GetMapping("/images/article-picture/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveArticlePicture(@PathVariable String filename){
        return serveResource(fileService.loadArticlePictureAsResource(filename), MediaType.IMAGE_JPEG);
    }

    /*
    * 统一的处理方法
    * */
    private ResponseEntity<Resource> serveResource(Resource resource, MediaType mediaType) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(mediaType)
                .body(resource);
    }

}
