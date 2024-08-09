package com.moyunzhijiao.system_backend.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Paths;

@Service
public class FileService {
    public Resource loadTemplateWordAsResource(String filename) {
        String imageFilePath = ConfigService.getTemplateWordFilePath() + filename;
        try {
            Resource resource = new UrlResource(Paths.get(imageFilePath).toUri().toURL());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("找不到文件!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("错误: " + e.getMessage());
        }
    }

    public Resource loadSampleWordAsResource(String filename) {
        String imageFilePath = ConfigService.getSampleWordFilePath() + filename;
        try {
            Resource resource = new UrlResource(Paths.get(imageFilePath).toUri().toURL());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("找不到文件!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("错误: " + e.getMessage());
        }
    }

    public Resource loadCopybookAsResource(String filename) {
        String imageFilePath = ConfigService.getCopybookFilePath() + filename;
        try {
            Resource resource = new UrlResource(Paths.get(imageFilePath).toUri().toURL());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("找不到文件!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("错误: " + e.getMessage());
        }
    }
}
