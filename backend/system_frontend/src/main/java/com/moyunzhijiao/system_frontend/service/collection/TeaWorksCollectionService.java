package com.moyunzhijiao.system_frontend.service.collection;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.collection.TeaWorksCollection;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.mapper.collection.TeaWorksCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaWorksCollectionService extends ServiceImpl<TeaWorksCollectionMapper, TeaWorksCollection> {
    @Autowired
    TeaWorksCollectionMapper teaWorksCollectionMapper;
    public IPage<Homework> getHomeworkOfTeacher(IPage<Homework> page, Integer teacherId) {
        page = teaWorksCollectionMapper.selectHomeworkOfTeacher(page,teacherId);
        Integer total = teaWorksCollectionMapper.countHomeworkOfTeacher(teacherId);
        total=total == null ? 0:total;
        page.setTotal(total);
        return page;
    }
}
