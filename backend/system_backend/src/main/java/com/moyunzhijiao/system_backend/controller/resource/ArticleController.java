package com.moyunzhijiao.system_backend.controller.resource;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.resource.ArticleDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Article;
import com.moyunzhijiao.system_backend.service.resource.ArticleService;
import com.moyunzhijiao.system_backend.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('添加书法知识')")
    @PostMapping("/add")
    public Result addArticle(@RequestPart("image") MultipartFile image,@RequestHeader("token") String token,
                             @RequestPart("content") String htmlContent, @RequestPart("article") String articleStr){
        if(image.isEmpty()){
            return Result.error(Constants.CODE_400,"封面文件为空");
        }
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        String userId = jwt.getAudience().get(0);
        Article article = JSONUtil.toBean(articleStr, Article.class);
        article.setImporter(userService.getNameById(userId));
        articleService.addArticle(image,htmlContent,article);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('资源管理')")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str,@RequestParam(required = false)Integer secondTypeId,
                           @RequestParam(required = false)boolean isRecommended){
        IPage<ArticleDTO> page = new Page<>(pageNum,pageSize);
        page = articleService.selectPage(page,str,secondTypeId,isRecommended);
        return Result.success(page);
    }

    @PreAuthorize("hasAuthority('资源管理')")
    @PutMapping("/delete")
    public Result deleteArticle(@RequestBody Map<String, String> params){
        String id = params.get("id");
        articleService.deleteArticle(id);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('资源管理')")
    @PutMapping("/update")
    public Result updateArticle(@RequestHeader("token") String token,@RequestBody ArticleDTO articleDTO){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        String userId = jwt.getAudience().get(0);
        articleDTO.setImporter(userService.getNameById(userId));
        articleService.updateArticle(articleDTO);
        return Result.success();
    }
}
