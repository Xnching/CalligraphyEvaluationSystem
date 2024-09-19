package com.moyunzhijiao.system_backend.mapper.word;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SystemTemplateMapper extends BaseMapper<SystemTemplate> {
    @Select("select type,detail_type ,count(*) as count " +
            "from system_template group by type,detail_type")
    List<SystemTemplate> selectRadio();
}
