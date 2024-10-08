package com.moyunzhijiao.system_backend.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {
    /*
    * 统一的返回文件方法
    * */
    private Resource loadResource(String filePath) {
        try {
            Resource resource = new UrlResource(Paths.get(filePath).toUri().toURL());
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

    public Resource loadTemplateWordAsResource(String filename) {
        return loadResource(ConfigService.getTemplateWordFilePath() + filename);
    }

    public Resource loadSampleWordAsResource(String filename) {
        return loadResource(ConfigService.getSampleWordFilePath() + filename);
    }

    public Resource loadCopybookAsResource(String filename) {
        return loadResource(ConfigService.getCopybookFilePath() + filename);
    }

    public Resource loadCollectionAsResource(String filename) {
        return loadResource(ConfigService.getCollectionFilePath() + filename);
    }

    /*
     * 加载字的生成的视频
     * */
    public Resource loadVideoAsResource(String filename) {
        return loadResource(ConfigService.getVideoFilePath() + filename);
    }

    public Resource loadCustomTemplateAsResource(String filename) {
        return loadResource(ConfigService.getCustomTemplateFilePath() + filename);
    }

    public Resource loadCharacterAsResource(String filename) {
        return loadResource(ConfigService.getCharacterFilePath() + filename);
    }

    public Resource loadStrokeAsResource(String filename) {
        return loadResource(ConfigService.getStrokeFilePath() + filename);
    }

    /*
     * 竞赛的图片
     * */
    public Resource loadCompetitionAsResource(String filename) {
        return loadResource(ConfigService.getCompetitionFilePath() + filename);
    }

    /*
     * 公告的图片
     * */
    public Resource loadAnnouncementAsResource(String filename) {
        return loadResource(ConfigService.getAnnouncementFilePath() + filename);
    }

    /*
     * 视频封面的图片
     * */
    public Resource loadVideoImageAsResource(String filename) {
        return loadResource(ConfigService.getVideoImageFilePath() + filename);
    }

    public Resource loadHomeworkAsResource(String filename) {
        return loadResource(ConfigService.getHomeworkFilePath() + filename);
    }

    public Resource loadEditorImageAsResource(String filename) {
        return loadResource(ConfigService.getEditorImageFilePath() + filename);
    }

    public Resource loadSystemTemplateAsResource(String filename) {
        return loadResource(ConfigService.getSystemTemplateFilePath() + filename);
    }

    public Resource loadArticlePictureAsResource(String filename) {
        return loadResource(ConfigService.getArticlePictureFilePath() + filename);
    }
}
