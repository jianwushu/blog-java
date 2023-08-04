package com.ruoyi.web.controller.blog;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.entity.Meta;
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
import com.ruoyi.system.service.MetaService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分类和标签Controller
 *
 * @author ruoyi
 * @date 2020-11-15
 */
@RestController
@RequestMapping("/blog/meta" )
public class MetaController extends BaseController {
    @Autowired
    private MetaService metaService;

    /**
     * 查询分类和标签列表
     */
//    @PreAuthorize("@ss.hasPermi('system:meta:list')")
    @GetMapping("/list")
    public TableDataInfo list(Meta meta) {
        startPage();
        LambdaQueryWrapper<Meta> lqw = Wrappers.lambdaQuery(meta);
        if (StringUtils.isNotBlank(meta.getName())){
            lqw.like(Meta::getName ,meta.getName());
        }
        if (StringUtils.isNotBlank(meta.getType())){
            lqw.eq(Meta::getType ,meta.getType());
        }
        if (StringUtils.isNotBlank(meta.getDescription())){
            lqw.eq(Meta::getDescription ,meta.getDescription());
        }
        if (meta.getCount() != null){
            lqw.eq(Meta::getCount ,meta.getCount());
        }
        if (meta.getVersion() != null){
            lqw.eq(Meta::getVersion ,meta.getVersion());
        }
        if (meta.getDeleted() != null){
            lqw.eq(Meta::getDeleted ,meta.getDeleted());
        }
        List<Meta> list = metaService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出分类和标签列表
     */
    @PreAuthorize("@ss.hasPermi('system:meta:export')" )
    @Log(title = "分类和标签" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Meta meta) {
        LambdaQueryWrapper<Meta> lqw = new LambdaQueryWrapper<Meta>(meta);
        List<Meta> list = metaService.list(lqw);
        ExcelUtil<Meta> util = new ExcelUtil<Meta>(Meta. class);
        return util.exportExcel(list, "meta" );
    }

    /**
     * 获取分类和标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:meta:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(metaService.getById(id));
    }

    /**
     * 新增分类和标签
     */
    @PreAuthorize("@ss.hasPermi('system:meta:add')" )
    @Log(title = "分类和标签" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Meta meta) {
        return toAjax(metaService.save(meta) ? 1 : 0);
    }

    /**
     * 修改分类和标签
     */
    @PreAuthorize("@ss.hasPermi('system:meta:edit')" )
    @Log(title = "分类和标签" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Meta meta) {
        return toAjax(metaService.updateById(meta) ? 1 : 0);
    }

    /**
     * 删除分类和标签
     */
    @PreAuthorize("@ss.hasPermi('system:meta:remove')" )
    @Log(title = "分类和标签" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(metaService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

