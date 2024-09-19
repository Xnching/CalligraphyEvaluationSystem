package com.moyunzhijiao.system_backend.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.base.SchoolDTO;
import com.moyunzhijiao.system_backend.entiy.base.School;
import com.moyunzhijiao.system_backend.mapper.base.SchoolMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;




@Service
public class SchoolService extends ServiceImpl<SchoolMapper,School> {
    @Resource
    RegionService regionService;

    @Resource
    SchoolMapper schoolMapper;

    public IPage<SchoolDTO> selectSchools(IPage<School> page, String str) {
        QueryWrapper<School> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(str)){
            //模糊查询
            queryWrapper.like("name",str);
            queryWrapper.like("address",str);
            queryWrapper.like("type",str);
            queryWrapper.like("leader",str);
        }
        queryWrapper.eq("delete_flag",0);
        IPage<School> pagedResult = page(page, queryWrapper);
        List<School> list = pagedResult.getRecords();
        // 转换为 DTO
        List<SchoolDTO> dtoList = list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        //给每个学校赋值其所拥有的三级地区id
        dtoList = dtoList.stream()
                .map(regionService::setThreeRegion)
                .collect(Collectors.toList());
        // 创建新的 IPage 对象并设置数据
        IPage<SchoolDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), pagedResult.getTotal());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }


    private SchoolDTO convertToDTO(School school) {
        SchoolDTO schoolDTO = new SchoolDTO();
        BeanUtil.copyProperties(school,schoolDTO);
        return schoolDTO;
    }

    /*
    * 修改一个学校
    * */
    public void updateSchool(SchoolDTO schoolDTO) {
        fullShoolAddress(schoolDTO);
        School school = convertToEntity(schoolDTO);
        schoolMapper.updateById(school);
    }

    private School convertToEntity(SchoolDTO schoolDTO) {
        School school = new School();
        BeanUtil.copyProperties(schoolDTO,school);
        return school;
    }

    /*
    * 逻辑删除一个学校
    * */
    public void deleteSchool(String id) {
        School school = schoolMapper.selectById(id);
        school.setDeleteFlag(true);
        schoolMapper.updateById(school);
    }

    /*
    * 新增一个学校
    * */
    public void addSchool(SchoolDTO schoolDTO) {
        School school = convertToEntity(schoolDTO);
        //三级地区id也一起复制过来
        school.setMaxRegionId(schoolDTO.getRegionId2());
        school.setNextRegionId(schoolDTO.getRegionId1());
        schoolMapper.insert(school);
    }

    /*
    * 修改学校人数
    * */
    public void updateStudentCount(Integer schoolId,Integer number){
        School school = getById(schoolId);
        school.setStudentCount(school.getStudentCount()+number);
        updateById(school);
    }
    /*
     * 修改学校人数
     * */
    public void updateTeacherCount(Integer schoolId,Integer number){
        School school = getById(schoolId);
        school.setTeacherCount(school.getTeacherCount()+number);
        updateById(school);
    }

    public void fullShoolAddress(SchoolDTO schoolDTO){
        String region = regionService.selectAllNameById(schoolDTO.getRegionId());
        schoolDTO.setAddress(region+schoolDTO.getAddress());
    }

    /*
    * @param 县级地区id以及学校类型
    * @return 只查找id和name用来渲染学校下拉框
    * */
    public List<School> findSchoolsByRegion(Integer countyId,String type){
        QueryWrapper<School> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("region_id", countyId);
        queryWrapper.eq("type", type);
        queryWrapper.select("id", "name");  // 只查询 id 和 name 字段
        return list(queryWrapper);
    }

    /*
     * 获取本日新增的学校数量
     * */
    public Integer getDaySchool() {
        Integer number = null;
        return number;
    }

    /*
     * 获取本月新增的学校数量
     * */
    public Integer getMonthSchool() {
        Integer number = null;
        return number;
    }
}
