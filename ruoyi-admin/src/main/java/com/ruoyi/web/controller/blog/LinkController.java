package com.ruoyi.web.controller.blog;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.LinkService;
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
import com.ruoyi.system.entity.Link;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 友链Controller
 *
 * @author ruoyi
 * @date 2020-12-31
 */
@RestController
@RequestMapping("/blog/link" )
public class LinkController extends BaseController {
    @Autowired
    private LinkService linkService;

    /**
     * 查询友链列表
     */
//    @PreAuthorize("@ss.hasPermi('system:link:list')")
    @GetMapping("/list")
    public TableDataInfo list(Link link)
    {
        startPage();
        LambdaQueryWrapper<Link> lqw = Wrappers.lambdaQuery(new Link());
        if (StringUtils.isNotBlank(link.getLinkName())){
            lqw.like(Link::getLinkName ,link.getLinkName());
        }
        if (StringUtils.isNotBlank(link.getLinkUrl())){
            lqw.eq(Link::getLinkUrl ,link.getLinkUrl());
        }
        if (StringUtils.isNotBlank(link.getDescription())){
            lqw.eq(Link::getDescription ,link.getDescription());
        }
        List<Link> list = linkService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出友链列表
     */
    @PreAuthorize("@ss.hasPermi('system:link:export')" )
    @Log(title = "友链" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Link links) {
        LambdaQueryWrapper<Link> lqw = new LambdaQueryWrapper<Link>(links);
        List<Link> list = linkService.list(lqw);
        ExcelUtil<Link> util = new ExcelUtil<Link>(Link. class);
        return util.exportExcel(list, "links" );
    }

    /**
     * 获取友链详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:link:query')" )
    @GetMapping(value = "/{linkId}" )
    public AjaxResult getInfo(@PathVariable("linkId" ) Long linkId) {
        return AjaxResult.success(linkService.getById(linkId));
    }

    /**
     * 新增友链
     */
    @PreAuthorize("@ss.hasPermi('system:link:add')" )
    @Log(title = "友链" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Link links) {
        return toAjax(linkService.save(links) ? 1 : 0);
    }

    /**
     * 修改友链
     */
    @PreAuthorize("@ss.hasPermi('system:link:edit')" )
    @Log(title = "友链" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Link links) {
        return toAjax(linkService.updateById(links) ? 1 : 0);
    }

    /**
     * 删除友链
     */
    @PreAuthorize("@ss.hasPermi('system:link:remove')" )
    @Log(title = "友链" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{linkIds}" )
    public AjaxResult remove(@PathVariable Long[] linkIds) {
        return toAjax(linkService.removeByIds(Arrays.asList(linkIds)) ? 1 : 0);
    }
}
