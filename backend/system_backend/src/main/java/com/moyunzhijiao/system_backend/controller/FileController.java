package com.moyunzhijiao.system_backend.controller;

import cn.hutool.core.lang.UUID;
import com.moyunzhijiao.system_backend.common.FileResponse;
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
    private final Path imageLocation = Paths.get("src/main/resources/static/images");
    private final Path videoLocation = Paths.get("src/main/resources/static/videos");



    @PostMapping("/editor-image")
    public FileResponse serveImage(@RequestPart("file") MultipartFile file){
        try {
            if (file.isEmpty()) {
                return FileResponse.error("File is empty");
            }
            // 生成唯一文件名
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            // 获取项目的根目录
            File projectRoot = new File(System.getProperty("user.dir"));
            // 构造文件的路径
            String filePath = projectRoot.getParentFile().getParent() + "/frontend/system_frontend/public/images/articleImage/" + fileName;
            String url = "/images/articleImage/"+fileName;
            File dest = new File(filePath);
            // 保存文件到本地
            file.transferTo(dest);
            // 返回文件信息
            return FileResponse.success(url, file.getOriginalFilename(), "");
        } catch (IOException e) {
            return FileResponse.error("Failed to store file");
        }
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

}
