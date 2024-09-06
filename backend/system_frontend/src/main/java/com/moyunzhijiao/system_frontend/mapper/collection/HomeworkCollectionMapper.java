package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.entity.collection.HomeworkCollection;
import org.apache.ibatis.annotations.Insert;

public interface HomeworkCollectionMapper extends BaseMapper<HomeworkCollection> {
    @Insert("INSERT INTO stu_submission_collection (student_id, submission_id,type) VALUES (#{stuId}, #{kId},#{type})")
    public boolean insert_ (Integer stuId,Integer kId,String type);
}
