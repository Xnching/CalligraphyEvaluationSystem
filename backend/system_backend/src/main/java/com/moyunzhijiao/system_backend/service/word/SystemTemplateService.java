package com.moyunzhijiao.system_backend.service.word;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.word.SystemTemplateDTO;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplateImage;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.word.SystemTemplateMapper;
import com.moyunzhijiao.system_backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class SystemTemplateService extends ServiceImpl<SystemTemplateMapper, SystemTemplate> {
    @Autowired
    SystemTemplateMapper systemTemplateMapper;
    @Autowired
    SystemTemplateImageService systemTemplateImageService;
    @Autowired
    TemplateWordService templateWordService;
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
    * 生成系统模板
    * */
    public void addSpecialTemplate(SystemTemplateDTO systemTemplateDTO, Integer userId) {
        if(StrUtil.isEmpty(systemTemplateDTO.getName())){
            throw new ServiceException(Constants.CODE_401,"未填写模板名称");
        }
        if(systemTemplateDTO.getDifficulty()==0){
            throw new ServiceException(Constants.CODE_401,"未选择模板难度");
        }
        //先生成模板
        SystemTemplate systemTemplate = new SystemTemplate();
        systemTemplate.setType("专项");
        systemTemplate.setCreatorId(userId);
        BeanUtil.copyProperties(systemTemplateDTO, systemTemplate);
        save(systemTemplate);
        //接着拼接图片
        List<String> imagePaths = templateWordService.selectFilePathBatch(systemTemplateDTO.getWordIds());
        //List<String> templatePaths =
        String url = gatherImages(imagePaths);
        //保存图片
        SystemTemplateImage systemTemplateImage = new SystemTemplateImage();
        systemTemplateImage.setSystemTemplateId(systemTemplate.getId());
        systemTemplateImage.setPictureUrl(url);
        systemTemplateImageService.save(systemTemplateImage);
    }

    public String gatherImages(List<String> imagePaths){
        int targetWidth = 100;
        int targetHeight = 100;
        int imagesPerRow = 6;
        try {
            // 读取所有图片并缩放到统一大小
            List<BufferedImage> images = imagePaths.stream()
                    .map(path -> {
                        try {
                            BufferedImage originalImage = ImageIO.read(new File(path));
                            BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                            Graphics2D g2d = scaledImage.createGraphics();
                            g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
                            g2d.dispose();
                            return scaledImage;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(image -> image != null)
                    .toList();

            // 计算拼接后的图片的宽度和高度
            int rows = (int) Math.ceil((double) images.size() / imagesPerRow);
            int width = targetWidth * imagesPerRow;
            int height = targetHeight * rows;

            // 创建一个新的 BufferedImage 来存储拼接后的图片
            BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = mergedImage.createGraphics();

            // 将每张图片绘制到新的 BufferedImage 上
            int currentX = 0;
            int currentY = 0;
            int imageCount = 0;
            for (BufferedImage image : images) {
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
            String fileName = UUID.randomUUID()+".jpg";
            String path = ConfigService.getSystemTemplateFilePath()+fileName ;
            String url = ConfigService.getSystemTemplateUrl()+fileName;
            ImageIO.write(mergedImage, "jpg", new File(path));
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
