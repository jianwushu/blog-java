package com.ruoyi.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.Meta;
import com.ruoyi.system.entity.dto.MetaDTO;
import com.ruoyi.system.entity.vo.MetaVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */
public interface MetaService extends IService<Meta> {
    Meta getCategory(Long id);
    List<Meta> getTags(Long id);
    void addBlogTag(Long blog_id,List<Meta> tags);
    void updBlogTag(Long blog_id,List<Meta> oldTags,List<Meta> newTags);
    void upCount(Long id);
    void downCount(Long id,Integer num);
}
