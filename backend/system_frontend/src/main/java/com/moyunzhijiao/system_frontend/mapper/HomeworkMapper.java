package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.Homework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface HomeworkMapper extends BaseMapper<Homework> {

}
