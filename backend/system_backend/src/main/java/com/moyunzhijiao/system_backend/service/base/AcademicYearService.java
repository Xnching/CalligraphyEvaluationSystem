package com.moyunzhijiao.system_backend.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.base.AcademicYearDTO;
import com.moyunzhijiao.system_backend.entiy.base.AcademicYear;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.base.AcademicYearMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AcademicYearService extends ServiceImpl<AcademicYearMapper, AcademicYear> {
    public AcademicYear convertToEntity(AcademicYearDTO academicYearDTO){
        AcademicYear academicYear = new AcademicYear();
        BeanUtil.copyProperties(academicYearDTO,academicYear);
        academicYear.setFirstStart(parseDate(academicYearDTO.getFirstStart()));
        academicYear.setFirstEnd(parseDate(academicYearDTO.getFirstEnd()));
        academicYear.setSecondStart(parseDate(academicYearDTO.getSecondStart()));
        academicYear.setSecondEnd(parseDate(academicYearDTO.getSecondEnd()));
        return academicYear;
    }


    private Date parseDate(String dateString) {
        try {
            return DateUtil.parse(dateString);
        } catch (Exception e) {
            // 处理解析错误
            throw new ServiceException(Constants.CODE_400,"日期数据格式错误");
        }
    }

}
