package com.moyunzhijiao.system_backend.controller.word;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.word.SystemTemplateDTO;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import com.moyunzhijiao.system_backend.service.word.SystemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backend/system-template")
public class SystemTemplateController {

    @Autowired
    SystemTemplateService systemTemplateService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(required = false)String str, @RequestParam(required = false)String type){
        IPage<SystemTemplate> page = new Page<>(pageNum,pageSize);
        page = systemTemplateService.selectPage(page,str,type);
        System.out.println("让我看下");
        System.out.println(page.toString());
        return Result.success(page);
    }

    @PostMapping("/special-add")
    public Result addSpicialTemplate(@RequestHeader("token") String token,@RequestBody SystemTemplateDTO systemTemplateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        systemTemplateService.addSpecialTemplate(systemTemplateDTO,userId);
        return Result.success();
    }
}
