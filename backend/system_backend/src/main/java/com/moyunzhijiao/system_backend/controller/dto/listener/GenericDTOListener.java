package com.moyunzhijiao.system_backend.controller.dto.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.service.GradeService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class GenericDTOListener<T> extends AnalysisEventListener<T> {

    private List<T> validData = new ArrayList<>();
    private static Set<String> validYears ;

    public GenericDTOListener(Set<String> validYears) {
        this.validYears = validYears;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(data);
        String grade = (String) beanWrapper.getPropertyValue("grade");
        if (!validYears.contains(grade)) {
            throw new ServiceException(Constants.CODE_400,"文件中年级格式未符合样例");
        }else {
            validData.add(data);
            System.out.println("让我们看看监听器里插入了数据了吗："+data);
            System.out.println("实时打印validData："+validData.toString());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 在所有数据都被分析后执行的操作...
    }

    public List<T> getValidData() {
        return validData;
    }
}

