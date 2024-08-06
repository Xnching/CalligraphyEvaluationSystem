package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.Question;
import com.moyunzhijiao.system_frontend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {

    public QAFDTO getQuestions(){
        List<Question> list = list();
        List<QAFDTO.Item> items = list.stream().map(question -> {
            QAFDTO.Item item = new QAFDTO.Item();
            item.setTitle(question.getQ());
            item.setContent(question.getA());
            item.setType("问题类型");
            return item;
        }).toList();
        QAFDTO qafdto = new QAFDTO();
        qafdto.setType("1");
        qafdto.setList(items);
        return qafdto;
    }


}
