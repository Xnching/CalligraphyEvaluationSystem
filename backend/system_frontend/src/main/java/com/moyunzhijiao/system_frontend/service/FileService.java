package com.moyunzhijiao.system_frontend.service;

import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String extractFileName(String url) {
        String regex = ".*/([^/?]+).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        } else {
            //throw new ServiceException(Constants.CODE_500,"文件不存在！");
            return false;
        }
    }
}
