package com.moyunzhijiao.system_backend.controller.word;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.word.SystemTemplateDTO;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import com.moyunzhijiao.system_backend.entiy.word.TemplateWord;
import com.moyunzhijiao.system_backend.service.word.SystemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backend/system-template")
public class TemplateController {

    @Autowired
    SystemTemplateService systemTemplateService;

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(required = false)String str, @RequestParam(required = false)String type){
        IPage<SystemTemplate> page = new Page<>(pageNum,pageSize);
        page = systemTemplateService.selectPage(page,str,type);
        return Result.success(page);
    }

    @PreAuthorize("hasAuthority('模板管理')")
    @PostMapping("/special-add")
    public Result addSpicialTemplate(@RequestHeader("token") String token,@RequestBody SystemTemplateDTO systemTemplateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        systemTemplateService.addSpecialTemplate(systemTemplateDTO,userId);
        return Result.success();
    }

    /*
    * 获取综合系统模板的在线预览
    * */
    @PreAuthorize("hasAuthority('模板管理')")
    @GetMapping("/get-picture")
    public Result getPicture(@RequestParam String textContent,@RequestParam(required = false) Integer fontId,@RequestParam(required = false) String composing){
        if(fontId==null){
            return Result.error(Constants.CODE_400,"未选择字体！");
        }
        if(StrUtil.isEmpty(composing)){
            return Result.error(Constants.CODE_400,"未选择版式！");
        }
        //获取用于给前端展示的三维数组模板字
        List<List<List<TemplateWord>>> list = systemTemplateService.getPictureOfComprehensive(textContent,fontId,composing);
        return Result.success(list);
    }

    @PreAuthorize("hasAuthority('模板管理')")
    @PostMapping("/copybook-add")
    public Result addCopybookTemplate(@RequestHeader("token") String token,@RequestBody SystemTemplateDTO systemTemplateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        systemTemplateService.addCopybookTemplate(systemTemplateDTO,userId);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('模板管理')")
    @PostMapping("/comprehensive-add")
    public Result addComprehensiveTemplate(@RequestHeader("token") String token,@RequestBody SystemTemplateDTO systemTemplateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        systemTemplateService.addComprehensiveTemplate(systemTemplateDTO,userId);
        return Result.success();
    }
}
