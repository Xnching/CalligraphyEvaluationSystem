package com.moyunzhijiao.system_backend.controller.word;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.SampleWord;
import com.moyunzhijiao.system_backend.service.ConfigService;
import com.moyunzhijiao.system_backend.service.word.SampleWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/sample-word")
public class SampleWordController {

    @Autowired
    SampleWordService sampleWordService;
    @GetMapping("/importers")
    public Result findAllImporters(){
        List<String> list = sampleWordService.listImporter();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str,@RequestParam(required = false)Integer structureId,
                           @RequestParam(required = false)Integer radicalId,@RequestParam(required = false)Integer gradeId,
                           @RequestParam(defaultValue = "")String source,@RequestParam(defaultValue = "")String importer){
        IPage<SampleWord> page = new Page<>(pageNum,pageSize);
        page = sampleWordService.selectPage(page,str,structureId,radicalId,gradeId,source,importer);
        return Result.success(page);
    }

    @PreAuthorize("hasAuthority('样本字管理')")
    @PutMapping("/delete")
    public Result deleteWord(@RequestBody Map<String, String> params){
        String id = params.get("id");
        sampleWordService.deleteWord(id);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('样本字管理')")
    @PutMapping("/update")
    public Result updateWord(@RequestBody SampleWord sampleWord){
        sampleWordService.updateById(sampleWord);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('样本字管理')")
    @PostMapping("/single-add")
    public Result singleAddWord(@RequestPart("sampleWord") String sampleWordStr,@RequestPart("file") MultipartFile file){
        if (!file.isEmpty()) {
            SampleWord sampleWord = JSONUtil.toBean(sampleWordStr, SampleWord.class);
            String fileName = UUID.randomUUID() + "-" +file.getOriginalFilename();
            // 构造文件的路径
            String filePath = ConfigService.getSampleWordFilePath() + fileName;
            String url = ConfigService.getSampleWordUrl()+"/"+fileName;
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
                sampleWord.setContent(url);
                sampleWordService.addSampleWord(sampleWord);
                return Result.success();
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error(Constants.CODE_401,"文件上传失败");
            }
        } else {
            return Result.error(Constants.CODE_400,"文件为空");
        }
    }

    @PreAuthorize("hasAuthority('样本字管理')")
    @PostMapping("/batch-add")
    public Result batchAddWord(@RequestPart("images") MultipartFile[] images, @RequestPart("excelFile") MultipartFile excelFile,@RequestPart("importer") String importer){
        if (excelFile.isEmpty()) {
            return Result.error(Constants.CODE_400,"Excel文件为空");
        }
        // 读取Excel文件中的数据
        List<SampleWord> sampleWords = sampleWordService.readExcelFile(excelFile);
        if (sampleWords.isEmpty()) {
            return Result.error(Constants.CODE_400,"Excel文件中没有数据");
        }

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID() + "-" +image.getOriginalFilename();
                // 构造文件的路径
                String filePath = ConfigService.getSampleWordFilePath() + fileName;
                String url = ConfigService.getSampleWordUrl()+"/"+fileName;
                File dest = new File(filePath);
                try {
                    image.transferTo(dest);
                    SampleWord sampleWord = sampleWordService.findSampleWordByFileName(sampleWords, image.getOriginalFilename());

                    if (sampleWord != null) {
                        sampleWord.setContent(url);
                        sampleWord.setImporter(importer);
                        sampleWordService.addSampleWord(sampleWord);
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
