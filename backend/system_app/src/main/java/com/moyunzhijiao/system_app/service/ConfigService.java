package com.moyunzhijiao.system_app.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Getter
public class ConfigService {
    @Value("${server.protocol}")
    private String serverProtocol;      //协议，例如http

//    @Value("${server.ip}")
    @Value("47.120.39.45")
    private String serverIp;            //服务器ip地址
//47.120.39.45:5998
//    @Value("${server.port}")
    @Value("5998")
    private String serverPort;          //端口

    private static String baseUrl;

    //FileController里的也要改
    private static String avatarFilePath;
    private static String avatarUrl;
    //头像的API接口
    private static final String AVATAR_BASE_URL = "/upload/images/avatar";
    private static final String AVATAR_BASE_PATH = "/resources/image/avatar/";
    //模板字的API接口
    private static String templateWordFilePath;
    private static String templateWordUrl;
    private static final String TEMPLATE_WORD_BASE_URL = "/upload/images/templateWord";
    private static final String TEMPLATE_WORD_PATH = "\\resources\\image\\templateWord\\";

    //样本字的
    private static String sampleWordFilePath;
    private static String sampleWordUrl;
    private static final String SAMPLE_WORD_BASE_URL = "/upload/images/sampleWord";
    private static final String SAMPLE_WORD_PATH = "\\resources\\image\\sampleWord\\";


    //字帖的
    private static String copybookFilePath;
    private static String copybookUrl;
    private static final String COPYBOOK_BASE_URL = "/upload/images/copybook";
    private static final String COPYBOOK_PATH = "\\resources\\image\\copybook\\";

    //视频的
    private static String videoFilePath;
    private static String videoUrl;
    private static final String VIDEO_BASE_URL = "/upload/videos";
    private static final String VIDEO_PATH = "\\resources\\video\\";

    //视频封面的
    private static String videoImageFilePath;
    private static String videoImageUrl;
    private static final String VIDEO_IMAGE_BASE_URL = "/upload/images/video";
    private static final String VIDEO_IMAGE_PATH = "\\resources\\image\\video\\";
    //字的分析的
    private static String characterFilePath;
    private static String characterUrl;
    private static final String CHARACTER_BASE_URL = "/upload/images/character";
    private static final String CHARACTER_PATH = "\\resources\\image\\character\\";

    //笔画分析的
    private static String strokeFilePath;
    private static String strokeUrl;
    private static final String STROKE_BASE_URL = "/upload/images/stroke";
    private static final String STROKE_PATH = "\\resources\\image\\stroke\\";

    //作业图片的
    private static String homeworkFilePath;
    private static String homeworkUrl;
    private static final String HOMEWORK_BASE_URL = "/upload/images/homework";
    private static final String HOMEWORK_PATH = "\\resources\\image\\homework\\";

    //公告图片的
    private static String announcementFilePath;
    private static String announcementUrl;
    private static final String ANNOUNCEMENT_BASE_URL = "/upload/images/announcement";
    private static final String ANNOUNCEMENT_PATH = "\\resources\\image\\announcement\\";

    //竞赛图片的
    private static String competitionFilePath;
    private static String competitionUrl;
    private static final String COMPETITION_BASE_URL = "/upload/images/competition";
    private static final String COMPETITION_PATH = "\\resources\\image\\competition\\";

    //编辑器的图片
    private static String editorImageFilePath;
    private static String editorImageUrl;
    private static final String EDITOR_IMAGE_BASE_URL = "/upload/get/editor-image";
    private static final String EDITOR_IMAGE_PATH = "\\resources\\image\\editorImage\\";

    //系统模板的图片
    private static String systemTemplateFilePath;
    private static String systemTemplateUrl;
    private static final String SYSTEM_TEMPLATE_BASE_URL = "/upload/images/system-template/";
    private static final String SYSTEM_TEMPLATE_PATH = "\\resources\\image\\systemTemplate\\";

    //合集的图片
    private static String collectionFilePath;
    private static String collectionUrl;
    private static final String COLLECTION_BASE_URL = "/upload/images/collection-picture/";
    private static final String COLLECTION_PATH = "\\resources\\image\\collection-picture\\";

    //自定义模板的图片
    private static String customTemplateFilePath;
    private static String customTemplateUrl;
    private static final String CUSTOM_TEMPLATE_BASE_URL = "/upload/images/custom-template/";
    private static final String CUSTOM_TEMPLATE_PATH = "\\resources\\image\\customTemplate\\";

    @PostConstruct
    public void init() {
        baseUrl = serverProtocol + "://" + serverIp + ":" + serverPort;
        File projectRoot = new File(System.getProperty("user.dir"));
        //头像文件路径
        avatarFilePath = projectRoot.getParentFile().getParent()+AVATAR_BASE_PATH;
        //头像接口url
        avatarUrl = baseUrl+AVATAR_BASE_URL;

        //设置模板字
        templateWordFilePath = projectRoot.getParentFile().getParent()+TEMPLATE_WORD_PATH;
        templateWordUrl = baseUrl+TEMPLATE_WORD_BASE_URL;

        //设置样本字
        sampleWordFilePath = projectRoot.getParentFile().getParent()+SAMPLE_WORD_PATH;
        sampleWordUrl = baseUrl+SAMPLE_WORD_BASE_URL;

        //设置字帖
        copybookFilePath = projectRoot.getParentFile().getParent()+COPYBOOK_PATH;
        copybookUrl = baseUrl+COPYBOOK_BASE_URL;


        //视频的
        videoFilePath = projectRoot.getParentFile().getParent()+VIDEO_PATH;
        videoUrl = baseUrl+VIDEO_BASE_URL;
        //视频封面的
        videoImageFilePath = projectRoot.getParentFile().getParent()+VIDEO_IMAGE_PATH;
        videoImageUrl = baseUrl+VIDEO_IMAGE_BASE_URL;

        //字的分析的
        characterFilePath = projectRoot.getParentFile().getParent()+CHARACTER_PATH;
        characterUrl = baseUrl+CHARACTER_BASE_URL;

        //笔画分析的
        strokeFilePath = projectRoot.getParentFile().getParent()+STROKE_PATH;
        strokeUrl = baseUrl+STROKE_BASE_URL;

        //作业图片的
        homeworkFilePath = projectRoot.getParentFile().getParent()+HOMEWORK_PATH;
        homeworkUrl = baseUrl+HOMEWORK_BASE_URL;

        //公告图片
        announcementFilePath = projectRoot.getParentFile().getParent()+ANNOUNCEMENT_PATH;
        announcementUrl = baseUrl+ANNOUNCEMENT_BASE_URL;

        //竞赛图片
        competitionFilePath = projectRoot.getParentFile().getParent()+COMPETITION_PATH;
        competitionUrl = baseUrl+COMPETITION_BASE_URL;

        //编辑器图片
        editorImageFilePath = projectRoot.getParentFile().getParent()+EDITOR_IMAGE_PATH;
        editorImageUrl = baseUrl+EDITOR_IMAGE_BASE_URL;

        //系统模板的图片
        systemTemplateFilePath = projectRoot.getParentFile().getParent()+SYSTEM_TEMPLATE_PATH;
        systemTemplateUrl = baseUrl+SYSTEM_TEMPLATE_BASE_URL;

        //合集的图片
        collectionFilePath = projectRoot.getParentFile().getParent()+COLLECTION_PATH;
        collectionUrl = baseUrl+COLLECTION_BASE_URL;

        //作业图片的
        customTemplateFilePath = projectRoot.getParentFile().getParent()+CUSTOM_TEMPLATE_PATH;
        customTemplateUrl = baseUrl+CUSTOM_TEMPLATE_BASE_URL;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getAvatarFilePath(){
        return avatarFilePath;
    }
    public static String getAvatarUrl(){
        return avatarUrl;
    }
    public static String getTemplateWordFilePath() {
        return templateWordFilePath;
    }

    public static String getSampleWordFilePath(){
        return sampleWordFilePath;
    }

    public static String getCopybookFilePath(){
        return copybookFilePath;
    }

    public static String getCopybookUrl(){
        return copybookUrl;
    }

    public static String getSampleWordUrl(){
        return sampleWordUrl;
    }
    public static String getTemplateWordUrl(){
        return templateWordUrl;
    }

    public static String getVideoFilePath(){
        return videoFilePath;
    }
    public static String getVideoUrl(){
        return videoUrl;
    }
    public static String getVideoImageFilePath(){
        return videoImageFilePath;
    }
    public static String getVideoImageUrl(){
        return videoImageUrl;
    }
    public static String getCharacterFilePath(){
        return characterFilePath;
    }

    public static String getStrokeFilePath(){
        return strokeFilePath;
    }
    public static String getHomeworkFilePath(){
        return homeworkFilePath;
    }

    public static String getHomeworkUrl() {
        return  homeworkUrl;
    }

    public static String getAnnouncementFilePath(){
        return announcementFilePath;
    }
    public static String getAnnouncementUrl(){
        return announcementUrl;
    }
    public static String getCompetitionFilePath(){
        return competitionFilePath;
    }
    public static String getCompetitionUrl(){
        return competitionUrl;
    }
    public static String getCollectionFilePath(){return collectionFilePath;}
    public static String getCollectionUrl(){return collectionUrl;}
    public static String getEditorImageFilePath(){
        return editorImageFilePath;
    }
    public static String getEditorImageUrl(){
        return editorImageUrl;
    }
    public static String getSystemTemplateFilePath(){
        return systemTemplateFilePath;
    }
    public static String getSystemTemplateUrl(){
        return systemTemplateUrl;
    }

    public static String getCustomTemplateFilePath(){
        return customTemplateFilePath;
    }
    public static String getCustomTemplateUrl(){return customTemplateUrl;}
}
