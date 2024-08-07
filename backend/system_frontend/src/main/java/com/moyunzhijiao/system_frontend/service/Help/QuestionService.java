package com.moyunzhijiao.system_frontend.service.Help;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.help.Question;
import com.moyunzhijiao.system_frontend.mapper.help.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
