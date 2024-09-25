package com.moyunzhijiao.system_backend.controller.word;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.Copybook;
import com.moyunzhijiao.system_backend.service.ConfigService;
import com.moyunzhijiao.system_backend.service.word.CopybookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/copybook")
@PreAuthorize("hasAuthority('字帖管理')")
public class CopybookController {

    @Autowired
    CopybookService copybookService;


    @GetMapping("/authors")
    public Result findAllAuthors(){
        List<String> list = copybookService.listAuthors();
        return Result.success(list);
    }
    @GetMapping("/importers")
    public Result findAllImporters(){
        List<String> list = copybookService.listImporter();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str, @RequestParam(required = false)Integer gradeId,
                           @RequestParam(required = false)Integer fontId,
                           @RequestParam(defaultValue = "")String author, @RequestParam(defaultValue = "")String importer){
        IPage<Copybook> page = new Page<>(pageNum,pageSize);
        page = copybookService.selectPage(page,str,fontId,gradeId,author,importer);
        return Result.success(page);
    }

    @PutMapping("/delete")
    public Result deleteWord(@RequestBody Map<String, String> params){
        String id = params.get("id");
        copybookService.deleteCopybook(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateWord(@RequestBody Copybook copybook){
        copybookService.updateById(copybook);
        return Result.success();
    }

    @PostMapping("/single-add")
    public Result singleAddWord(@RequestPart("copybook") String copybookStr,@RequestPart("file") MultipartFile file){
        if (!file.isEmpty()) {
            Copybook copybook = JSONUtil.toBean(copybookStr, Copybook.class);
            String fileName = UUID.randomUUID()+"-"+file.getOriginalFilename();

            // 构造文件的路径
            String filePath = ConfigService.getCopybookFilePath() + fileName;
            String url = ConfigService.getCopybookUrl()+"/"+fileName;
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
                copybook.setContent(url);
                copybookService.addCopybook(copybook);
                return Result.success();
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error(Constants.CODE_401,"文件上传失败");
            }
        } else {
            return Result.error(Constants.CODE_400,"文件为空");
        }
    }


    @PostMapping("/batch-add")
    public Result batchAddWord(@RequestPart("images") MultipartFile[] images, @RequestPart("excelFile") MultipartFile excelFile,@RequestPart("importer") String importer){
        if (excelFile.isEmpty()) {
            return Result.error(Constants.CODE_400,"Excel文件为空");
        }
        // 读取Excel文件中的数据
        List<Copybook> copybooks = copybookService.readExcelFile(excelFile);
        if (copybooks.isEmpty()) {
            return Result.error(Constants.CODE_400,"Excel文件中没有数据");
        }


        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID()+"-"+image.getOriginalFilename();
                // 构造文件的路径
                String filePath = ConfigService.getCopybookFilePath()+ fileName;
                String url = ConfigService.getCopybookUrl()+"/"+fileName;
                File dest = new File(filePath);
                try {
                    image.transferTo(dest);
                    System.out.println("文件图片的名字"+image.getOriginalFilename());
                    Copybook copybook = copybookService.findCopybookByFileName(copybooks, image.getOriginalFilename());
                    System.out.println("找到了吗");
                    if (copybook != null) {
                        System.out.println("找到了吗");
                        copybook.setContent(url);
                        copybook.setImporter(importer);
                        System.out.println("保存了吗");
                        copybookService.addCopybook(copybook);
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
