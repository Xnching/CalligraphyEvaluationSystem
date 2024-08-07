package com.moyunzhijiao.system_backend.controller.word;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.Stroke;
import com.moyunzhijiao.system_backend.service.word.StrokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/stroke")
public class StrokeController {

    @Autowired
    StrokeService strokeService;
    @GetMapping("/strokes")
    public Result findAllStrokes(){
        List<Stroke> list = strokeService.list();
        return Result.success(list);
    }
}
