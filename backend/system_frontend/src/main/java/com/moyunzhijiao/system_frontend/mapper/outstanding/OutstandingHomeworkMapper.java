package com.moyunzhijiao.system_frontend.mapper.outstanding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.OutstandingHomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.outstanding.OutstandingHomework;
import org.apache.ibatis.annotations.Select;

public interface OutstandingHomeworkMapper extends BaseMapper<OutstandingHomework> {
    @Select("SELECT " +
            "  hs.id, " +
            "  h.name, " +
            "  hs.homework_id, " +
            "  oh.updated_time as submitTime " +
            "FROM homework_submission AS hs " +
            "JOIN outstanding_homework AS oh " +
            "  ON hs.id = oh.submissions_id "+
            "JOIN homework AS h " +
            "  ON hs.homework_id = h.id ")
    public IPage<OutstandingHomeworkDTO> getOutstandingHomework(IPage<OutstandingHomeworkDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM homework_submission AS hs " +
            "JOIN outstanding_homework AS oh " +
            "  ON hs.id = oh.submissions_id "+
            "JOIN homework AS h " +
            "  ON hs.homework_id = h.id ")
    public Integer countOutstandingHomework();
}
