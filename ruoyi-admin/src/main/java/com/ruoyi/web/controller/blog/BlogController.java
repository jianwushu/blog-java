package com.ruoyi.web.controller.blog;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.*;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.Meta;
import com.ruoyi.system.entity.dto.BlogDTO;
import com.ruoyi.system.entity.dto.MetaDTO;
import com.ruoyi.system.entity.vo.BlogListVo;
import com.ruoyi.system.entity.vo.BlogVo;
import com.ruoyi.system.service.BlogService;
import com.ruoyi.system.service.LuceneService;
import com.ruoyi.system.service.MetaService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文章Controller
 *
 * @author ruoyi
 * @date 2020-11-15
 */

@RestController
@RequestMapping("/blog/blog" )
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private LuceneService luceneService;

    @GetMapping("/count")
    public AjaxResult getCount(){

        LambdaQueryWrapper<Blog> qw = new LambdaQueryWrapper<>();
        qw.eq(Blog::getStatus,2);
        qw.eq(Blog::getDeleted,0);
        String count = blogService.getCount(qw);
        return AjaxResult.success(count);
    }

    @GetMapping("/view")
    public AjaxResult updView(Blog blog){
        blogService.updView(blog.getId());
        return AjaxResult.success("success");
    }

    @GetMapping("/archive")
    public TableDataInfo getList(){
        List<Blog> list = blogService.list();
        List<BlogListVo> list2 = new ArrayList<>();

        for (Blog blog : list) {
            BlogListVo blogListVo = new BlogListVo();
            BeanUtils.copyProperties(blog,blogListVo);
            list2.add(blogListVo);
        }
        return getDataTable(list2);
    }
    /**
     * 查询文章列表
     */
//    @PreAuthorize("@ss.hasPermi('system:blog:list')")
    @GetMapping("/list")
    public TableDataInfo list(Blog blog) {
        startPage();
        LambdaQueryWrapper<Blog> lqw = new LambdaQueryWrapper<>();
        if (blog.getId()!=null){
            lqw.eq(Blog::getId ,blog.getId());
        }
        if (StringUtils.isNotBlank(blog.getTitle())){
            lqw.eq(Blog::getTitle ,blog.getTitle());
        }
        if (StringUtils.isNotBlank(blog.getText())){
            lqw.eq(Blog::getText ,blog.getText());
        }
        if (blog.getAuthorId() != null){
            lqw.eq(Blog::getAuthorId ,blog.getAuthorId());
        }
        if (blog.getCategoryId() != null){
            lqw.eq(Blog::getCategoryId ,blog.getCategoryId());
        }
        if (blog.getViews() != null){
            lqw.eq(Blog::getViews ,blog.getViews());
        }
        if (blog.getStatus()!=null){
            lqw.eq(Blog::getStatus ,blog.getStatus());
        }
        if (StringUtils.isNotBlank(blog.getPassword())){
            lqw.eq(Blog::getPassword ,blog.getPassword());
        }
        if (blog.getAllowFeed()!=null){
            lqw.eq(Blog::getAllowFeed ,blog.getAllowFeed());
        }
        if (blog.getAllowComment()!=null){
            lqw.eq(Blog::getAllowComment ,blog.getAllowComment());
        }
        if (blog.getAllowShare()!=null){
            lqw.eq(Blog::getAllowShare ,blog.getAllowShare());
        }
        if (blog.getDeleted() != null){
            lqw.eq(Blog::getDeleted ,blog.getDeleted());
        }
        List<BlogVo> list = blogService.getList(lqw.orderBy(true,false,Blog::getStatus).orderBy(true,false,Blog::getCreateTime));
        return getDataTable(list);
    }

    /**
     * 导出文章列表
     */
    @PreAuthorize("@ss.hasPermi('system:blog:export')" )
    @Log(title = "文章" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Blog blog) {
        LambdaQueryWrapper<Blog> lqw = new LambdaQueryWrapper<Blog>(blog);
        List<Blog> list = blogService.list(lqw);
        ExcelUtil<Blog> util = new ExcelUtil<Blog>(Blog. class);
        return util.exportExcel(list, "blog" );
    }

    /**
     * 获取文章详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:blog:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        BlogVo blogVo = new BlogVo();
        BeanUtils.copyProperties(blogService.getById(id),blogVo);
        blogVo.setTags(metaService.getTags(blogVo.getId()));
        return AjaxResult.success(blogVo);
    }

    /**
     * 新增文章
     */
    @PreAuthorize("@ss.hasPermi('system:blog:add')" )
    @Log(title = "文章" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlogVo blog) {
        metaService.saveBatch(blog.getTags());
        boolean save = blogService.save(blog);
        metaService.upCount(blog.getCategoryId());
        metaService.addBlogTag(blog.getId(),blog.getTags());
        luceneService.addIndex(blog);
        return toAjax(save ? 1 : 0 );
    }

    /**
     * 修改文章
     */
    @PreAuthorize("@ss.hasPermi('system:blog:edit')" )
    @Log(title = "文章" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlogVo blog) {
        List<Meta> oldTags = metaService.getTags(blog.getId());
        metaService.saveOrUpdateBatch(blog.getTags());
        metaService.updBlogTag(blog.getId(),oldTags,blog.getTags());
        boolean update = blogService.saveOrUpdate(blog);
        return toAjax(update ? 1 : 0);
    }

    /**
     * 删除文章
     */
    @PreAuthorize("@ss.hasPermi('system:blog:remove')" )
    @Log(title = "文章" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            metaService.downCount(blogService.getById(id).getCategoryId(),1);;
        }
        luceneService.deleteIndex(ids);
        boolean remove = blogService.removeByIds(Arrays.asList(ids));
        return toAjax(remove ? 1 : 0);
    }
}

