package com.ruoyi.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.vo.BlogVo;
import com.ruoyi.system.mapper.BlogMapper;
import com.ruoyi.system.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<BlogVo> getList(Wrapper<Blog> qw) {
//        List<BlogDTO> blogs = blogMapper.getList(qw);
//        List<BlogListVo> blogss = new ArrayList<>();
//        BlogListVo b = null;
//        for (BlogDTO blog : blogs) {
//            BeanUtils.copyProperties(b,blog);
//            blogss.add(b);
//        }
//        System.out.println(blogss.get(0));
        return blogMapper.getList(qw);
    }

    @Override
    public String getCount(Wrapper<Blog> qw) {
        return blogMapper.getCount(qw);
    }

    @Override
    public void updView(Long id) {
        blogMapper.updView(id);
    }
}
