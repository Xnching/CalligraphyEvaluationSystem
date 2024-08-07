package com.moyunzhijiao.system_backend.service.front;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.base.GradeDTO;
import com.moyunzhijiao.system_backend.controller.dto.front.KlassDTO;
import com.moyunzhijiao.system_backend.controller.dto.listener.GenericDTOListener;
import com.moyunzhijiao.system_backend.entiy.front.Klass;
import com.moyunzhijiao.system_backend.mapper.front.KlassMapper;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KlassService extends ServiceImpl<KlassMapper,Klass> {
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    GradeService gradeService;

    public IPage<KlassDTO> selectPage(IPage<KlassDTO> page,Integer schoolId,String str){
        page = klassMapper.selectPage(page,schoolId,str);
        Integer count = klassMapper.countPage(schoolId,str);
        page.setTotal(count);
        return page;
    }

    public void deleteKlass(String id){
        Klass klass = klassMapper.selectById(id);
        klass.setDeleteFlag(true);
        klassMapper.updateById(klass);
    }

    public void addKlassSingle(KlassDTO klassDTO){
        Klass klass = convertToEntity(klassDTO);
        klassMapper.insert(klass);
    }

    public Klass convertToEntity(KlassDTO klassDTO){
        Klass klass = new Klass();
        BeanUtil.copyProperties(klassDTO,klass);
        return klass;
    }

    /*
    * @Transactional注解，这样整个方法就会在一个事务中执行。
    * */
    @Transactional
    public void addKlassBatch(Integer id, InputStream inputStream) {
        //获取所有年级的姓名
        List<GradeDTO> grades = gradeService.getAllGrades();
        Set<String> gradesName = grades.stream().map(GradeDTO::getName).collect(Collectors.toSet());
        //把要用来做参照的年级发给监听器
        GenericDTOListener<KlassDTO> genericDTOListener = new GenericDTOListener(gradesName);
        //监听器一行一行校验
        EasyExcel.read(inputStream, KlassDTO.class, genericDTOListener).sheet().doRead();
        //获取excel文件内容
        List<KlassDTO> dtoList = genericDTOListener.getValidData();
        // 设置每个KlassDTO的gradeId和schoolId，并转换为Klass对象
        List<Klass> klassList = dtoList.stream().map(klassDTO -> {
            Integer gradeId = gradeService.getIdByField(klassDTO.getGrade());
            klassDTO.setGradeId(gradeId);
            klassDTO.setSchoolId(id);
            return convertToEntity(klassDTO);
        }).collect(Collectors.toList());
        // 保存到数据库
        saveBatch(klassList);
    }

    public List<Klass> getBySchoolAndGrade(Integer schoolId, Integer gradeId) {
        QueryWrapper<Klass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        queryWrapper.eq("grade_id",gradeId);
        queryWrapper.select("id","name");
        List<Klass> list = list(queryWrapper);
        return list;
    }

    public Integer getIdByField(String name,Integer gradeId, Integer id) {
        QueryWrapper<Klass> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("name",name).eq("grade_id",gradeId).eq("school_id",id);
        Klass klass = klassMapper.selectOne(queryWrapper);
        return klass != null ? gradeId : null;
    }
}
