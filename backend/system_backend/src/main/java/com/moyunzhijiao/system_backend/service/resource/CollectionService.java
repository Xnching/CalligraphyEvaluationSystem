package com.moyunzhijiao.system_backend.service.resource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.resource.CollectionDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Collection;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.resource.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CollectionService extends ServiceImpl<CollectionMapper, Collection> {
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    VideoCollectionService videoCollectionService;
    public IPage<CollectionDTO> selectCollection(IPage<Collection> page, String str, boolean isRecommended) {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        queryWrapper.like("tag",str);
        queryWrapper.eq("is_recommended",isRecommended);
        page = collectionMapper.selectPage(page,queryWrapper);
        List<CollectionDTO> dtoList = page.getRecords().stream().map(this::fillVideos).toList();
        IPage<CollectionDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    public CollectionDTO fillVideos(Collection collection){
        CollectionDTO collectionDTO = convertToDTO(collection);
        collectionDTO.setList(videoCollectionService.getVideoByCollection(collection.getId()));
        return collectionDTO;
    }
    @Transactional
    public void deleteCollection(Integer id) {
        videoCollectionService.deleteVideoByCollectionId(id);
        collectionMapper.deleteById(id);
    }

    @Transactional
    public void updateCollectionAndRelationship(CollectionDTO collectionDTO) {
        Collection collection = convertToEntity(collectionDTO);
        collectionMapper.updateById(collection);
        videoCollectionService.updateByCollection(collection.getId(),collectionDTO.getList());
    }

    @Transactional
    public void addCollection(MultipartFile image, CollectionDTO collectionDTO) {
        File projectRoot = new File(System.getProperty("user.dir"));
        String imageFileName = UUID.randomUUID() + "-" +image.getOriginalFilename();
        String imageFilePath = projectRoot.getParentFile().getParent()+ "/resources/image/collectionPicture/" + imageFileName;
        String tempImageFilePath = "http://localhost:8084/upload/images/collection-picture/" + imageFileName;
        File imageDest = new File(imageFilePath);
        try {
            image.transferTo(imageDest);
            if(collectionDTO!=null){
                collectionDTO.setPictureUrl(tempImageFilePath);
                Collection collection = convertToEntity(collectionDTO);
                collectionMapper.insert(collection);
                videoCollectionService.addBatch(collection.getId(),collectionDTO.getList());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统保存文件失败");
        }
    }

    public Collection convertToEntity(CollectionDTO collectionDTO){
        Collection collection = new Collection();
        BeanUtil.copyProperties(collectionDTO,collection);
        return collection;
    }
    public CollectionDTO convertToDTO(Collection collection){
        CollectionDTO collectionDTO = new CollectionDTO();
        BeanUtil.copyProperties(collection,collectionDTO);
        return collectionDTO;
    }

}
