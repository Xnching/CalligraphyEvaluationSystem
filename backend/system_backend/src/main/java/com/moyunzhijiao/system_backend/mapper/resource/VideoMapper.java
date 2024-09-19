package com.moyunzhijiao.system_backend.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.controller.dto.AnalysisDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Video;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VideoMapper extends BaseMapper<Video> {
    @Select("select cf.name as type,count(v.second_type_id) as count " +
            "   from ${target} v" +
            "   left join calligraphy_facts cf on cf.id = v.second_type_id " +
            "group by v.second_type_id ")
    List<AnalysisDTO> selectTypeCount(String target);
}
