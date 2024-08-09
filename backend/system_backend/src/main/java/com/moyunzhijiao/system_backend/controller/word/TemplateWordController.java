package com.moyunzhijiao.system_backend.controller.word;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.TemplateWord;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.service.ConfigService;
import com.moyunzhijiao.system_backend.service.word.TemplateWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/template-word")
public class TemplateWordController {
    @Autowired
    TemplateWordService templateWordService;

    @GetMapping("/authors")
    public Result findAllAuthors(){
        List<String> list = templateWordService.listAuthors();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str, @RequestParam(required = false)Integer structureId,
                           @RequestParam(required = false)Integer radicalId, @RequestParam(required = false)Integer gradeId,
                           @RequestParam(required = false)Integer fontId, @RequestParam(defaultValue = "")String author){
        IPage<TemplateWord> page = new Page<>(pageNum,pageSize);
        page = templateWordService.selectPage(page,str,structureId,radicalId,gradeId,fontId,author);
        return Result.success(page);
    }
    @PutMapping("/delete")
    public Result deleteWord(@RequestBody Map<String, String> params){
        String id = params.get("id");
        templateWordService.deleteWord(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateWord(@RequestBody TemplateWord templateWord){
        templateWordService.updateById(templateWord);
        return Result.success();
    }

    @PostMapping("/single-add")
    public Result singleAddWord(@RequestPart("templateWord")String templateWordStr,@RequestPart("file") MultipartFile file)  {
        if(!file.isEmpty()){
            TemplateWord templateWord = JSONUtil.toBean(templateWordStr, TemplateWord.class);
            String fileName = UUID.randomUUID() + "-" +file.getOriginalFilename();
            // 构造文件的路径
            String filePath = ConfigService.getTemplateWordFilePath() + fileName;
            String url = ConfigService.getSampleWordUrl()+"/"+fileName;
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
                templateWord.setContent(url);
                templateWordService.addTemplateWord(templateWord);
                return Result.success();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(Constants.CODE_401,"文件上传失败");
            }
        }else {
            return Result.error(Constants.CODE_400,"文件为空");
        }
    }

    @PostMapping("/batch-add")
    public Result batchAddWord(@RequestPart("images") MultipartFile[] images, @RequestPart("excelFile") MultipartFile excelFile,@RequestPart("importer") String importer){
        if (excelFile.isEmpty()) {
            return Result.error(Constants.CODE_400,"Excel文件为空");
        }
        List<TemplateWord> templateWords = templateWordService.readExcelFile(excelFile);
        if(templateWords.isEmpty()){
            return Result.error(Constants.CODE_400,"Excel文件中没有数据");
        }

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID() + "-" +image.getOriginalFilename();
                // 构造文件的路径
                String filePath = ConfigService.getTemplateWordFilePath() + fileName;
                String url = ConfigService.getSampleWordUrl()+"/"+fileName;
                File dest = new File(filePath);
                try {
                    image.transferTo(dest);
                    System.out.println("让我们看看controller里的"+image.getOriginalFilename());
                    TemplateWord templateWord = templateWordService.findTemplateWordByFileName(templateWords, image.getOriginalFilename());

                    if (templateWord != null) {
                        templateWord.setContent(url);
                        templateWord.setImporter(importer);
                        templateWordService.addTemplateWord(templateWord);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return Result.error(Constants.CODE_401,"文件上传失败");
                }
            } else {
                return Result.error(Constants.CODE_400,"图片文件为空");
            }
        }
        return Result.success();
    }

}
