package com.moyunzhijiao.system_frontend.service;

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

    @Value("${server.ip}")
    private String serverIp;            //服务器ip地址

    private final String serverPort = "8084";          //端口

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
    private static final String TEMPLATE_WORD_BASE_URL = "/upload/images/templateWord/";
    private static final String TEMPLATE_WORD_PATH = "/resources/image/templateWord/";


    //字帖的
    private static String copybookFilePath;
    private static String copybookUrl;
    private static final String COPYBOOK_BASE_URL = "/upload/images/copybook/";
    private static final String COPYBOOK_PATH = "/resources/image/copybook/";

    //视频的
    private static String videoFilePath;
    private static String videoUrl;
    private static final String VIDEO_BASE_URL = "/upload/videos/";
    private static final String VIDEO_PATH = "/resources/videos/";

    //字的分析的
    private static String characterFilePath;
    private static String characterUrl;
    private static final String CHARACTER_BASE_URL = "/upload/images/character/";
    private static final String CHARACTER_PATH = "/resources/image/character/";

    //笔画分析的
    private static String strokeFilePath;
    private static String strokeUrl;
    private static final String STROKE_BASE_URL = "/upload/images/stroke/";
    private static final String STROKE_PATH = "/resources/image/stroke/";

    //作业图片的
    private static String homeworkFilePath;
    private static String homeworkUrl;
    private static final String HOMEWORK_BASE_URL = "/upload/images/homework/";
    private static final String HOMEWORK_PATH = "/resources/image/homework/";

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

        //设置字帖
        copybookFilePath = projectRoot.getParentFile().getParent()+COPYBOOK_PATH;
        copybookUrl = baseUrl+COPYBOOK_BASE_URL;

        //视频的
        videoFilePath = projectRoot.getParentFile().getParent()+VIDEO_PATH;
        videoUrl = baseUrl+VIDEO_BASE_URL;

        //字的分析的
        characterFilePath = projectRoot.getParentFile().getParent()+CHARACTER_PATH;
        characterUrl = baseUrl+CHARACTER_BASE_URL;

        //笔画分析的
        strokeFilePath = projectRoot.getParentFile().getParent()+STROKE_PATH;
        strokeUrl = baseUrl+STROKE_BASE_URL;

        //作业图片的
        homeworkFilePath = projectRoot.getParentFile().getParent()+HOMEWORK_PATH;
        homeworkUrl = baseUrl+HOMEWORK_BASE_URL;

        //模板图片的
        customTemplateFilePath = projectRoot.getParentFile().getParent()+CUSTOM_TEMPLATE_PATH;
        customTemplateUrl = baseUrl+CUSTOM_TEMPLATE_BASE_URL;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getAvatarFilePath(){
        return avatarFilePath;
    }
    public static String getTemplateWordFilePath() {
        return templateWordFilePath;
    }
    public static String getAvatarUrl(){
        return avatarUrl;
    }

    public static String getCopybookFilePath(){
        return copybookFilePath;
    }

    public static String getVideoFilePath(){
        return videoFilePath;
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
    public static String getHomeworkUrl(){return homeworkUrl;}

    public static String getCustomTemplateFilePath(){
        return customTemplateFilePath;
    }
    public static String getCustomTemplateUrl(){return customTemplateUrl;}

}
