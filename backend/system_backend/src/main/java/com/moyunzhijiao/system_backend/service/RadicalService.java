package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.Radical;
import com.moyunzhijiao.system_backend.mapper.RadicalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RadicalService extends ServiceImpl<RadicalMapper, Radical> {
    @Autowired
    RadicalMapper radicalMapper;

}
