package com.moyunzhijiao.system_frontend.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.entity.TemplateWord;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PictureService {
    @Autowired
    TemplateWordService templateWordService;
    int targetWidth = 100;      //每个模板字的图片宽度
    int targetHeight = 100;     //每个模板字的图片高度
    int imagesPerRow = 2;       //每行有多少张图片(一张大图片)
    int imagesPerColumn = 3;    //每张拼接起来的大图片一共有多少行

    int pageWidth = targetWidth * imagesPerRow;     // 计算拼接后的图片的宽度和高度
    int pageHeight = targetHeight * imagesPerColumn;

    /**
     * 综合作业的拼接图片，传入的是之间的三位模板字数组提取出来的id的三维数组
     * @param: idArray:三维整型数组，表示每个模板字所在的位置，用其id替代
     *         composing：排版：分为横版和竖版
     * @return: 返回拼接好的大图片的列表
     * */
    public List<BufferedImage> gatherImagesOfComprehensive(List<List<List<Integer>>> idArray, String composing) {
        //存储大图片url的列表
        List<BufferedImage> bigImageList = new ArrayList<>();
        //遍历每一张大图片的二维id矩阵
        idArray.forEach( smallImageIds->{        //为List<List<Integer>>
            //一张大图片的二维图片矩阵
            List<List<BufferedImage>> smallImageList = new ArrayList<>();
            smallImageIds.forEach(idList->{         //为List<Integer>即一串模板字的id
                List<String> pathList = templateWordService.selectFilePathBatch(idList);        //一串模板字的文件路径
                List<BufferedImage> imageList = getImages(pathList);
                smallImageList.add(imageList);
            });
            // 创建一个新的 BufferedImage 来存储拼接后的图片
            BufferedImage mergedImage = new BufferedImage(pageWidth, pageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = mergedImage.createGraphics();
            //不同的版式不同的拼法
            int currentX = 0;
            int currentY = 0;
            for (List<BufferedImage> rowImageList:smallImageList){
                if(composing.equals("横版")){
                    // 将每张图片绘制到新的 BufferedImage 上
                    for (BufferedImage image : rowImageList) {
                        g2d.drawImage(image, currentX, currentY, null);
                        currentX += targetWidth;
                    }
                    currentX = 0;
                    currentY += targetHeight;
                }else if(composing.equals("竖版")){
                    // 将每张图片绘制到新的 BufferedImage 上
                    for (BufferedImage image : rowImageList) {
                        g2d.drawImage(image, currentX, currentY, null);
                        currentY += targetHeight;
                    }
                    currentY = 0;
                    currentX += targetWidth;
                }else {
                    throw new ServiceException(Constants.CODE_401,"未选择版式！");
                }
            }
            g2d.dispose();
            //加到列表中
            bigImageList.add(mergedImage);
        });
        return bigImageList;
    }

    /**
     * 创建综合模板的时候最先使用的函数，用来解析用户输入的文本，转化成三维数组模板字，即二维数组模板字的列表
     * @param textContent :用户输入的文本
     * @param fontId :选择的模板字的字体id
     * @param composing :排版
     * @return java.util.List<java.util.List<java.util.List<com.moyunzhijiao.system_frontend.entity.TemplateWord>>>
     **/
    public List<List<List<TemplateWord>>> getPictureOfComprehensive(String textContent, Integer fontId,String composing) {
        //纸张的宽度和长度
        int width;
        int length;
        //不同的排版会导致一串字符串的最大字符数量不同，以及字符串的串数的不同
        if(composing.equals("横版")){
            width = imagesPerRow;
            length = imagesPerColumn;
        }else if(composing.equals("竖版")){
            length = imagesPerRow;
            width = imagesPerColumn;
        }else {
            throw new ServiceException(Constants.CODE_400,"未选择排版形式！");
        }
        //使用正则去掉没有内容的行
        String temp =  ReUtil.replaceAll(textContent, "(?m)^\\s*$[\n\r]{1,}", "");
        //使用替换去掉所有空白
        String temp2 = StrUtil.replace(temp," ","");
        //使用正则表达式去除所有标点符号和字母
        String result = ReUtil.replaceAll(temp2, "[\\p{Punct}\\p{Alpha}\\p{IsPunctuation}]", "");
        //将结果最后分为有内容的字符串
        List<String> stringList = StrUtil.split(result,"\n");
        //限定下宽度或长度，使得一行或一列不超出边界
        List<String> finalStringList = splitStrings(stringList, width);
        // 移除空字符串
        finalStringList.removeIf(String::isEmpty);
        //分成多个大图片
        List<List<String>> charListOfImages = new ArrayList<>();
        for(int i=0;i<finalStringList.size();i+=length){
            List<String> tempList = new ArrayList<>();
            for (int j=0;(j<length)&&(i+j<finalStringList.size());j++){
                tempList.add(finalStringList.get(i+j));
            }
            charListOfImages.add(tempList);
        }
        System.out.println("让我们看下分成大图片的结果"+charListOfImages);
        //三维数组，把每个汉字字符转换成对应的模板字故三层循环
        List<List<List<TemplateWord>>> list1 = new ArrayList<>();
        for (List<String> tempStringList : charListOfImages) {
            List<List<TemplateWord>> list2 = new ArrayList<>();
            for (String str : tempStringList) {
                List<TemplateWord> list3 = new ArrayList<>(str.length());
                for (int i = 0; i < str.length(); i++) {
                    list3.add(templateWordService.getPictureByWord(str.charAt(i), fontId));
                }
                list2.add(list3);
            }
            list1.add(list2);
        }
        return list1;
    }

    /**
     * 生成三位模板字数组所需要用到的方法，用于将内容限制在一张纸中
     * @param stringList :一行字
     * @param maxLength :纸宽
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> splitStrings(List<String> stringList, int maxLength) {
        List<String> result = new ArrayList<>();
        for (String str : stringList) {
            while (str.length() > maxLength) {
                result.add(str.substring(0, maxLength));
                str = str.substring(maxLength);
            }
            result.add(str);
        }
        return result;
    }

    /**
     * 用于获取模板字的图片
     * @param imagePaths :传入的是本地模板字的图片的路径
     * @return java.util.List<java.awt.image.BufferedImage>
     **/
    private List<BufferedImage> getImages(List<String> imagePaths) {
        // 读取所有图片并缩放到统一大小
        return imagePaths.stream()
                .map(path -> {
                    try {
                        //原图片
                        BufferedImage originalImage = ImageIO.read(new File(path));
                        //存储缩放结果的图片
                        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = scaledImage.createGraphics();
                        //开始缩放画图
                        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
                        g2d.dispose();
                        return scaledImage;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * 专项练习的拼接图片，返回图片的url
     * @param imagePaths :图片文件路径列表
     * @param type :只用于saveFile函数，可替换成固定路径
     * @return java.util.List<java.lang.String>
     **/
    public List<String> gatherImagesOfSpecial(List<String> imagePaths,String type){
        int smallImageNumber = imagesPerColumn*imagesPerRow;    //每张大图片里的小图片数量
        // 读取所有图片并缩放到统一大小
        List<BufferedImage> images = getImages(imagePaths);
//        //拼接好的大图片的列表
//        List<BufferedImage> bigImageList = new ArrayList<>();
        //把图片分成批
        List<List<BufferedImage>> imageList = new ArrayList<>();
        for (int i=0;i<images.size();i+=smallImageNumber) {
            int end = Math.min(i + smallImageNumber, images.size());
            List<BufferedImage> temp = images.subList(i, end);
            imageList.add(temp);
        }
        //存储大图片url的列表
        List<String> urlList = new ArrayList<>();
        //一批一批的生成大图片
        for (List<BufferedImage> bufferedImages : imageList) {
            // 创建一个新的 BufferedImage 来存储拼接后的图片
            BufferedImage mergedImage = new BufferedImage(pageWidth, pageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = mergedImage.createGraphics();
            // 将每张图片绘制到新的 BufferedImage 上
            int currentX = 0;
            int currentY = 0;
            int imageCount = 0;
            for (BufferedImage image : bufferedImages) {
                g2d.drawImage(image, currentX, currentY, null);
                currentX += targetWidth;
                imageCount++;
                if (imageCount % imagesPerRow == 0) {
                    currentX = 0;
                    currentY += targetHeight;
                }
            }
            g2d.dispose();
            // 保存拼接后的图片
            String url = saveFile(mergedImage,type);
            //加到列表中
            urlList.add(url);
            //想要在此方法外保存的话就用bigImageList
            //bigImageList.add(mergedImage);
        }
        return urlList;
        //return bigImageList;
    }

    /**
     * 统一的把图片保存在本地,返回图片的url
     * @param mergedImage :图片
     * @param type :是作业还是模板，
     * @return java.lang.String
     **/
    public String saveFile(BufferedImage mergedImage,String type) {
        String fileName = UUID.randomUUID() + ".jpg";
        String path;
        String url;
        /*
                          协议                       ip            端口
        String baseUrl = serverProtocol + "://" + serverIp + ":" + serverPort;
        File projectRoot = new File(System.getProperty("user.dir"));
        String HOMEWORK_BASE_URL = "/upload/images/homework/";
        String HOMEWORK_PATH = "/resources/image/homework/";
        //作业图片的
        String homeworkFilePath = projectRoot.getParentFile().getParent()+HOMEWORK_PATH;
        String homeworkUrl = baseUrl+HOMEWORK_BASE_URL;
        */
        if(type.equals("作业")){
            path = ConfigService.getHomeworkFilePath() + fileName;
            url = ConfigService.getHomeworkUrl() + fileName;
        }else {
            path = ConfigService.getCustomTemplateFilePath()+fileName;
            url = ConfigService.getCustomTemplateUrl()+fileName;
        }
        try {
            ImageIO.write(mergedImage, "jpg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误！文件保存失败！");
        }
        return url;
    }

    /**
     * 把图片转化为base64用于发给前端，想更改就在下面修改
     * @param imageList :图片列表
     * @return java.util.List<java.lang.String>
     **/
    public List<String> exchangeImageToBase64(List<BufferedImage> imageList) {
        //把图片准备好发给前端
        return imageList.stream()
                .map(image -> {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(image, "jpeg", os);
                        //前端要求加上base64的头否则无法识别
                        return "data:image/png;base64,"+ Base64.getEncoder().encodeToString(os.toByteArray());
                    } catch (IOException e) {
                        throw new RuntimeException("发送失败！", e);
                    }
                })
                .toList();
    }

    /**
     * 将base64转化为图片
     * @param contentList :base64列表
     * @return java.util.List<java.awt.image.BufferedImage>
     **/
    public List<BufferedImage> exchangeBase64ToImage(List<String> contentList) {
        return contentList.stream().map(content->{
            //把base64的头去掉
            String temp = content.replaceFirst("^data:image/[^;]+;base64,", "");;
            BufferedImage image;
            //解析
            byte[] imageBytes = Base64.getDecoder().decode(temp);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            try {
                image = ImageIO.read(bis);
            } catch (IOException e) {
                throw new ServiceException(Constants.CODE_500,"系统错误");
            }
            return image;
        }).toList();
    }

    /**
     * 根据模板字三维数组转化成id三维数组
     * @param list :模板字容器
     * @return java.util.List<java.util.List<java.util.List<java.lang.Integer>>>
     **/
    public List<List<List<Integer>>> getIdArray(List<List<List<TemplateWord>>> list) {
        return list.stream()
                .map(innerList -> innerList.stream()
                        .map(innerInnerList -> innerInnerList.stream()
                                .map(TemplateWord::getId)
                                .collect(Collectors.toList()))
                        .collect(Collectors.toList()))
                .toList();
    }
}
