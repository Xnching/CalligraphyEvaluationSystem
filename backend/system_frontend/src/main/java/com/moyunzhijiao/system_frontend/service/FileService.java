package com.moyunzhijiao.system_frontend.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Paths;

@Service
public class FileService {
    /*
    * 加载头像
    * */
    public Resource loadAvatarAsResource(String filename) {
        String imageFilePath = ConfigService.getAvatarFilePath() + filename;
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

    /*
    * 加载模板字
    * */
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

    /*
     * 加载字帖
     * */
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
    /*
    * 加载字的生成的视频
    * */
    public Resource loadWriteWordAsResource(String filename) {
        String videoFilePath = ConfigService.getVideoFilePath() + filename;
        try {
            Resource resource = new UrlResource(Paths.get(videoFilePath).toUri().toURL());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("找不到文件!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("错误: " + e.getMessage());
        }
    }

    /*
    * 字的分析的图片
    * */
    public Resource loadCharacterAsResource(String filename) {
        String imageFilePath = ConfigService.getCharacterFilePath() + filename;
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

    /*
    * 笔画分析的图片
    * */
    public Resource loadStrokeAsResource(String filename) {
        String imageFilePath = ConfigService.getStrokeFilePath() + filename;
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

    /*
    * 作业图片
    * */
    public Resource loadHomeworkAsResource(String filename) {
        String imageFilePath = ConfigService.getHomeworkFilePath() + filename;
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
