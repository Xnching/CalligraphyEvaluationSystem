package com.moyunzhijiao.system_frontend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ciep")
public class KlassController {

    @GetMapping("/class-detail")
    public Result findStudentPageByKlassId(@RequestParam Integer currentPage, @RequestParam Integer pageSize,
                                           @RequestParam(defaultValue = "")Integer classId){
        IPage
    }

    @Operation(summary = "查找已添加的班级")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页显示的记录数", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "year", description = "年份", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "grade", description = "年级", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "klass", description = "班级", required = false, in = ParameterIn.QUERY)
    })
    @GetMapping("/class")
    public Result findPageOwn(@RequestParam Integer currentPage, @RequestParam Integer pageSize, @RequestParam(defaultValue = "")String year,
                           @RequestParam(defaultValue = "")String grade,@RequestParam(defaultValue = "")String klass){

    }

    @GetMapping("/")
    public Result findPageNotOwn(){
        return Result.success();
    }

}
