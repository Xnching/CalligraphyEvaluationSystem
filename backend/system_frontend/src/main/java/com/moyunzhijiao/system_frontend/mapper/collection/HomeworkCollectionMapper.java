package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KnowledgeCollectionDTO;
import com.moyunzhijiao.system_frontend.entity.collection.HomeworkCollection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface HomeworkCollectionMapper extends BaseMapper<HomeworkCollection> {
    @Insert("INSERT INTO stu_submission_collection (student_id, submission_id,type) VALUES (#{stuId}, #{kId},#{type})")
    public boolean insert_ (Integer stuId,Integer kId,String type);

    @Select("SELECT " +
            "    ssc.submission_id AS id, " +
            "    h.name AS name, " +
            "    ssc.type AS type " +
            "FROM " +
            "    stu_submission_collection ssc " +
            "JOIN " +
            "    homework_submission hs ON ssc.submission_id = hs.id " +
            "JOIN " +
            "    homework h ON hs.homework_id = h.id " +
            "WHERE " +
            "    ssc.student_id = #{stuId} AND ssc.type != '优秀竞赛作品' ")
    public IPage<HomeworkCollectionDTO> getHomeworkCollection(IPage<HomeworkCollectionDTO> page,@Param("stuId") Integer stuId);

    @Select("select count(*) "+
            "FROM " +
            "    stu_submission_collection ssc " +
            "WHERE " +
            "    ssc.student_id = #{stuId} AND ssc.type != '优秀竞赛作品' ")
    public Integer countHomeworkCollection(@Param("stuId") Integer stuId);

    @Select("SELECT " +
            "    ssc.submission_id AS id, " +
            "    c.name AS name, " +
            "    ssc.type AS type " +
            "FROM " +
            "    stu_submission_collection ssc " +
            "JOIN " +
            "    competition_submissions cs ON ssc.submission_id = cs.id " +
            "JOIN " +
            "    competition c ON cs.competition_id = c.id " +
            "WHERE " +
            "    ssc.student_id = #{stuId} AND ssc.type = '优秀竞赛作品' ")
    public IPage<HomeworkCollectionDTO> getHomeworkCollection_(IPage<HomeworkCollectionDTO> page,@Param("stuId") Integer stuId);

    @Select("select count(*) "+
            "FROM " +
            "    stu_submission_collection ssc " +
            "WHERE " +
            "    ssc.student_id = #{stuId} AND ssc.type = '优秀竞赛作品' ")
    public Integer countHomeworkCollection_(@Param("stuId") Integer stuId);


}
