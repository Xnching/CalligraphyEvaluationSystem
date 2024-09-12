package com.moyunzhijiao.system_backend.service.word;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.word.SystemTemplateDTO;
import com.moyunzhijiao.system_backend.entiy.word.Copybook;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplateImage;
import com.moyunzhijiao.system_backend.entiy.word.TemplateWord;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.word.SystemTemplateMapper;
import com.moyunzhijiao.system_backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SystemTemplateService extends ServiceImpl<SystemTemplateMapper, SystemTemplate> {
    @Autowired
    SystemTemplateMapper systemTemplateMapper;
    @Autowired
    SystemTemplateImageService systemTemplateImageService;
    @Autowired
    TemplateWordService templateWordService;
    @Autowired
    CopybookService copybookService;
    //下面为专项练习的参数
    int targetWidth = 100;      //图片宽度
    int targetHeight = 100;     //图片高度
    int imagesPerRow = 2;       //每行有多少张图片
    int imagesPerColumn = 3;    //每张拼接的大图片一共有多少行

    // 计算拼接后的图片的宽度和高度
    int pageWidth = targetWidth * imagesPerRow;
    int pageHeight = targetHeight * imagesPerColumn;

    public IPage<SystemTemplate> selectPage(IPage<SystemTemplate> page, String str, String type) {
        QueryWrapper<SystemTemplate> queryWrapper = new QueryWrapper<>();
        if(!StrUtil.isEmpty(str)){
            queryWrapper.like("name",str);
        }
        if(!StrUtil.isEmpty(type)){
            queryWrapper.eq("type",type);
        }
        page = systemTemplateMapper.selectPage(page,queryWrapper);
        page.getRecords().forEach(systemTemplate -> {
            Integer templateId = systemTemplate.getId();
            systemTemplate.setImageList(systemTemplateImageService.getImages(templateId));
        });
        return page;
    }

    /*
    * 生成系统模板中的专项模板
    * */
    @Transactional
    public void addSpecialTemplate(SystemTemplateDTO systemTemplateDTO, Integer userId) {
        //先生成模板
        SystemTemplate systemTemplate = insertTemplate(systemTemplateDTO,userId,"专项");
        //接着拼接图片
        List<String> imagePaths = templateWordService.selectFilePathBatch(systemTemplateDTO.getWordIds());
        List<String> templateUrls = gatherImages(imagePaths);
        //批量保存图片
        List<SystemTemplateImage> images = templateUrls.stream().map(url->{
            SystemTemplateImage systemTemplateImage = new SystemTemplateImage();
            systemTemplateImage.setSystemTemplateId(systemTemplate.getId());
            systemTemplateImage.setPictureUrl(url);
            return systemTemplateImage;
        }).toList();
        systemTemplateImageService.saveBatch(images);
    }

    /*
    * 专项练习的拼接图片
    * */
    public List<String> gatherImages(List<String> imagePaths){

        int smallImageNumber = imagesPerColumn*imagesPerRow;    //每张大图片里的小图片数量
        try {
            // 读取所有图片并缩放到统一大小
            List<BufferedImage> images = getImages(imagePaths);


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
                String fileName = UUID.randomUUID() + ".jpg";
                String path = ConfigService.getSystemTemplateFilePath() + fileName;
                String url = ConfigService.getSystemTemplateUrl() + fileName;
                ImageIO.write(mergedImage, "jpg", new File(path));
                //加到列表中
                urlList.add(url);
            }

            return urlList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误，生成图片失败！");
        }
    }

    /*
    * 根据图片文件路径获取图片
    * */
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

    /*
    * 创建字帖类系统模板
    * */
    @Transactional
    public void addCopybookTemplate(SystemTemplateDTO systemTemplateDTO, Integer userId) {
        //获得字帖
        Copybook copybook = copybookService.getById(systemTemplateDTO.getCopybookId());
        //先生成模板
        SystemTemplate systemTemplate = insertTemplate(systemTemplateDTO,userId,"字帖");
        //接着保存图片
        SystemTemplateImage systemTemplateImage = new SystemTemplateImage();
        systemTemplateImage.setPictureUrl(copybook.getContent());
        systemTemplateImage.setSystemTemplateId(systemTemplate.getId());
        systemTemplateImageService.save(systemTemplateImage);
    }

    /*
    * 获取想要创建综合模板时看的图片合集
    * */
    public List<List<List<TemplateWord>>> getPictureOfComprehensive(Integer userId, String textContent, Integer fontId,String composing) {
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
        System.out.println("让我们看下有原始的结果"+textContent);
        //使用正则去掉没有内容的行
        String temp =  ReUtil.replaceAll(textContent, "(?m)^\\s*$[\n\r]{1,}", "");
        System.out.println("让我们看下正则的结果"+temp);
        //使用替换去掉所有空白
        String temp2 = StrUtil.replace(temp," ","");
        System.out.println("让我们看下去掉空白的结果"+temp2);
        //使用正则表达式去除所有标点符号和字母
        String result = ReUtil.replaceAll(temp2, "[\\p{Punct}\\p{Alpha}\\p{IsPunctuation}]", "");
        System.out.println("让我们看下去掉字母的结果"+result);
        //将结果最后分为有内容的字符串
        List<String> stringList = StrUtil.split(result,"\n");
        System.out.println("让我们看下有内容的结果"+stringList.toString());
        //限定下宽度或长度，使得一行或一列不超出边界
        List<String> finalStringList = splitStrings(stringList, width);
        System.out.println("让我们看下限定结果"+finalStringList.toString());
        // 移除空字符串
        finalStringList.removeIf(String::isEmpty);
        System.out.println("让我们看下最终第二个结果"+finalStringList.toString());
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
        System.out.println("让我们看下最终结果"+list1.toString());
        return list1;

    }


    /*
    * 用于综合模板图片在线预览，限定下宽度或长度，使得一行或一列不超出边界
    * */
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

    /*
    * 使用递归来找到有多少个字，统计得到字叔
    * */
    public static int countIntegers(List<?> list) {
        int count = 0;
        for (Object element : list) {
            if (element instanceof List) {
                count += countIntegers((List<?>) element);
            } else if (element instanceof Integer) {
                count++;
            }
        }
        return count;
    }
    /*
    * 生成综合系统模板
    * */
    @Transactional
    public void addComprehensiveTemplate(SystemTemplateDTO systemTemplateDTO, Integer userId) {
        Integer number = countIntegers(systemTemplateDTO.getIdArray());
        systemTemplateDTO.setWordCount(number);
        SystemTemplate systemTemplate = insertTemplate(systemTemplateDTO,userId,"综合");
        //存储大图片url的列表
        List<String> urlList = new ArrayList<>();
        //遍历每一张大图片的二维id矩阵
        systemTemplateDTO.getIdArray().forEach( smallImageIds->{        //为List<List<Integer>>
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
            if(systemTemplateDTO.getComposing().equals("横版")){
                for (List<BufferedImage> rowImageList:smallImageList){
                    // 将每张图片绘制到新的 BufferedImage 上
                    for (BufferedImage image : rowImageList) {
                        g2d.drawImage(image, currentX, currentY, null);
                        currentX += targetWidth;
                    }
                    currentX = 0;
                    currentY += targetHeight;
                }
            }else if(systemTemplateDTO.getComposing().equals("竖版")){
                for (List<BufferedImage> rowImageList:smallImageList){
                    // 将每张图片绘制到新的 BufferedImage 上
                    for (BufferedImage image : rowImageList) {
                        g2d.drawImage(image, currentX, currentY, null);
                        currentY += targetWidth;
                    }
                    currentY = 0;
                    currentX += targetWidth;
                }
            }else {
                throw new ServiceException(Constants.CODE_401,"未选择版式！");
            }
            g2d.dispose();

            // 保存拼接后的图片
            String fileName = UUID.randomUUID() + ".jpg";
            String path = ConfigService.getSystemTemplateFilePath() + fileName;
            String url = ConfigService.getSystemTemplateUrl() + fileName;
            try {
                ImageIO.write(mergedImage, "jpg", new File(path));
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(Constants.CODE_500,"系统错误！文件保存失败！");
            }
            //加到列表中
            urlList.add(url);
        });
        //批量保存图片
        List<SystemTemplateImage> imageList = urlList.stream().map(url->{
            SystemTemplateImage systemTemplateImage = new SystemTemplateImage();
            systemTemplateImage.setPictureUrl(url);
            systemTemplateImage.setSystemTemplateId(systemTemplate.getId());
            return systemTemplateImage;
        }).toList();
        systemTemplateImageService.saveBatch(imageList);
    }

    /*
    * 统一的先生成系统模板
    * */
    public SystemTemplate insertTemplate(SystemTemplateDTO systemTemplateDTO, Integer userId,String type){
        if(StrUtil.isEmpty(systemTemplateDTO.getName())){
            throw new ServiceException(Constants.CODE_401,"未填写模板名称");
        }
        if(systemTemplateDTO.getDifficulty()==0){
            throw new ServiceException(Constants.CODE_401,"未选择模板难度");
        }
        //先生成模板
        SystemTemplate systemTemplate = new SystemTemplate();
        systemTemplate.setType(type);
        systemTemplate.setCreatorId(userId);
        BeanUtil.copyProperties(systemTemplateDTO, systemTemplate);
        save(systemTemplate);
        return systemTemplate;
    }
}
