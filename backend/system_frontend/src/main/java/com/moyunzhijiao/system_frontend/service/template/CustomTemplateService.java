package com.moyunzhijiao.system_frontend.service.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.TemplateDTO;
import com.moyunzhijiao.system_frontend.entity.Copybook;
import com.moyunzhijiao.system_frontend.entity.TemplateWord;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplateImage;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.template.CustomTemplateMapper;
import com.moyunzhijiao.system_frontend.service.CopybookService;
import com.moyunzhijiao.system_frontend.service.FontService;
import com.moyunzhijiao.system_frontend.service.PictureService;
import com.moyunzhijiao.system_frontend.service.TemplateWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomTemplateService extends ServiceImpl<CustomTemplateMapper, CustomTemplate> {
    @Autowired
    CustomTemplateMapper customTemplateMapper;
    @Autowired
    CustomTemplateImageService customTemplateImageService;
    @Autowired
    FontService fontService;
    @Autowired
    TemplateWordService templateWordService;
    @Autowired
    PictureService pictureService;
    @Autowired
    CopybookService copybookService;
    /*
    * 2024/9/21,不知道下面一行在胡言乱语什么，只知道找自定义模板直接去自定义模板表里找
    * 下面是通过教师发布作业，里面找是否有自定义的模板，教师获取保存的模板换了，换成teacherTemplateService
    * */
    public IPage<CustomTemplate> getByType(Integer userId,String userType, Integer currentPage, Integer pageSize) {
        QueryWrapper<CustomTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator_id",userId).eq("creator_type",userType);
        IPage<CustomTemplate> page = new Page<>(currentPage,pageSize);
        return page(page,queryWrapper);
    }

    public CustomTemplate getDeatilBySingle(Integer templateId){
        CustomTemplate customTemplate = customTemplateMapper.selectById(templateId);
        customTemplate.setImgs(customTemplateImageService.getImages(templateId));
        customTemplate.setFont(fontService.getNameById(customTemplate.getFontId()));
        return customTemplate;
    }


    /*
     * 获取一个教师的已保存的自定义模板,下面是有teacher_template表的时候的获取
     * */
//    public IPage<CustomTemplate> getByTeacher(Integer teacherId, Integer currentPage, Integer pageSize) {
//        IPage<CustomTemplate> page = new Page<>(currentPage,pageSize);
//        page = teacherTemplateMapper.selectByTeacher(page,teacherId);
//        Integer total = teacherTemplateMapper.countByTeacher(teacherId);
//        total = total==null?0:total;
//        page.setTotal(total);
//        return page;
//    }

    /*
     * 把一个保存下来的模板删除，同时把对应自定义模板删除，同时还需要把模板图片也给删了
     * */
    @Transactional
    public void deleteTemplate(Integer templateId){
        //首先删除关系
        try {
            customTemplateMapper.deleteById(templateId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数或系统错误，删除关系失败");
        }
        //接着删除图片
        customTemplateImageService.deleteByTemplate(templateId);
    }

    /*
    * 创建专项模板
    * */
    public void addSpecialTemplate(TemplateDTO templateDTO, Integer userId, String userType) {
        //先生成模板
        CustomTemplate customTemplate = insertTemplate(templateDTO,userId,userType,"专项");
        //接着生成图片
        List<String> imagePaths = templateWordService.selectFilePathBatch(templateDTO.getWordIds());
        //保存图片
        List<String> templateUrls = pictureService.gatherImagesOfSpecial(imagePaths);
        customTemplateImageService.addBatch(templateUrls,customTemplate.getId());
    }

    /*
    * 字帖模板
    * */
    public void addCopybookTemplate(TemplateDTO templateDTO, Integer userId, String userType) {
        //获得字帖
        Copybook copybook = copybookService.getById(templateDTO.getCopybookId());
        //先生成模板
        CustomTemplate customTemplate = insertTemplate(templateDTO,userId,userType,"字帖");
        //接着保存图片
        CustomTemplateImage customTemplateImage = new CustomTemplateImage();
        customTemplateImage.setCustomTemplateId(customTemplate.getId());
        customTemplateImage.setPictureUrl(copybook.getContent());;
        customTemplateImageService.save(customTemplateImage);
    }

    /*
    * 综合模板
    * */
    public void addComprehensiveTemplate(TemplateDTO templateDTO, Integer userId, String userType) {
        //先生成模板
        Integer number = countIntegers(templateDTO.getIdArray());
        templateDTO.setWordCount(number);
        CustomTemplate customTemplate = insertTemplate(templateDTO,userId,userType,"综合");
        //接着生成图片
        List<String> urlList = pictureService.gatherImagesOfComprehensive(templateDTO.getIdArray(),templateDTO.getComposing());
        //批量保存图片
        customTemplateImageService.addBatch(urlList,customTemplate.getId());
    }
    /*
     * 使用递归来找到有多少个字，统计得到字叔
     * */
    public static int countIntegers(List<?> list) {
        int count = 0;
        for (Object element : list) {
            if (element instanceof List) {
                count += countIntegers((List<?>) element);
            } else {
                count++;
            }
        }
        return count;
    }

    /*
     * 统一的先生成系统模板
     * */
    public CustomTemplate insertTemplate(TemplateDTO templateDTO, Integer userId,String userType,String type){
        if(StrUtil.isEmpty(templateDTO.getName())){
            throw new ServiceException(Constants.CODE_401,"未填写模板名称");
        }
        if(templateDTO.getDifficulty()==0){
            throw new ServiceException(Constants.CODE_401,"未选择模板难度");
        }
        //先生成模板
        CustomTemplate customTemplate = new CustomTemplate();
        customTemplate.setType(type);
        customTemplate.setCreatorId(userId);
        customTemplate.setCreatorType(userType);
        BeanUtil.copyProperties(templateDTO, customTemplate);
        save(customTemplate);
        return customTemplate;
    }
}
