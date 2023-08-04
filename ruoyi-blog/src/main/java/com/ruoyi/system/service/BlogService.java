package com.ruoyi.system.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.dto.BlogDTO;
import com.ruoyi.system.entity.vo.BlogListVo;
import com.ruoyi.system.entity.vo.BlogVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */

public interface BlogService extends IService<Blog> {
    List<BlogVo> getList(Wrapper<Blog> qw);
    String getCount(Wrapper<Blog> qw);
    void updView(Long id);
}
