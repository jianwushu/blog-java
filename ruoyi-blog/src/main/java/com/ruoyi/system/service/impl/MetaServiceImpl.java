package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.Meta;
import com.ruoyi.system.entity.dto.MetaDTO;
import com.ruoyi.system.mapper.MetaMapper;
import com.ruoyi.system.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */
@Service
public class MetaServiceImpl extends ServiceImpl<MetaMapper, Meta> implements MetaService {
    @Autowired
    private MetaMapper metaMapper;

    @Override
    public Meta getCategory(Long id) {
        return metaMapper.getCategory(id);
    }

    @Override
    public List<Meta> getTags(Long id) {
        return metaMapper.getTags(id);
    }

    @Override
    public void addBlogTag(Long blog_id, List<Meta> tags) {
        for (Meta tag : tags) {
            metaMapper.addBlogTag(blog_id,tag.getId());
        }
    }

    @Override
    public void updBlogTag(Long blog_id, List<Meta> oldTags, List<Meta> newTags) {
        List<Long> oldIds = new ArrayList<>();
        List<Long> temp = new ArrayList<>();
        for (Meta oldTag : oldTags) {
            oldIds.add(oldTag.getId());
            temp.add(oldTag.getId());
        }
        List<Long> newIds = new ArrayList<>();
        for (Meta newTag : newTags) {
            newIds.add(newTag.getId());
        }
        oldIds.removeAll(newIds);
        for (Long oldId : oldIds) {
            metaMapper.delBlogTag(oldId);
        }
        System.out.println("**********"+ Arrays.toString(temp.toArray()));
        newIds.removeAll(temp);
        System.out.println("**********"+Arrays.toString(newIds.toArray()));
        for (Long newId : newIds) {
            metaMapper.addBlogTag(blog_id,newId);
        }
    }

    @Override
    public void upCount(Long id) {
        metaMapper.upCount(id);
    }

    @Override
    public void downCount(Long id,Integer num) {
        metaMapper.downCount(id,num);
    }
}
