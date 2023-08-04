package com.ruoyi.web.controller.blog;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.system.entity.Option;
import com.ruoyi.system.service.OptionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 网站设置Controller
 *
 * @author ruoyi
 * @date 2020-12-31
 */
@RestController
@RequestMapping("/blog/option" )
public class OptionController extends BaseController {
    @Autowired
    private OptionService optionService;

    @GetMapping("/map")
    public AjaxResult getDataList(Option option){
        Wrapper<Option> qw = Wrappers.lambdaQuery(option);
        List<Option> optionList = optionService.list(qw);
        Map<String,String> result = new HashMap(8);
        for (Option op : optionList) {
            result.put(op.getName(),op.getVal());
        }
        return AjaxResult.success(result);
    }
    /**
     * 查询网站设置列表
     */
    @PreAuthorize("@ss.hasPermi('system:option:list')")
    @GetMapping("/list")
    public TableDataInfo list(Option option) {
        Wrapper<Option> lqw = Wrappers.lambdaQuery(option);
        List<Option> options = optionService.list(lqw);
        return getDataTable(options);
    }

    /**
     * 导出网站设置列表
     */
    @PreAuthorize("@ss.hasPermi('system:option:export')" )
    @Log(title = "网站设置" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Option option) {
        LambdaQueryWrapper<Option> lqw = new LambdaQueryWrapper<>(option);
        List<Option> list = optionService.list(lqw);
        ExcelUtil<Option> util = new ExcelUtil<Option>(Option. class);
        return util.exportExcel(list, "options" );
    }

    /**
     * 获取网站设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:option:query')" )
    @GetMapping(value = "/{title}" )
    public AjaxResult getInfo(@PathVariable("title" ) String title) {
        return AjaxResult.success(optionService.getById(title));
    }

    /**
     * 新增网站设置
     */
    @PreAuthorize("@ss.hasPermi('system:option:add')" )
    @Log(title = "网站设置" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Option option) {
        return toAjax(optionService.save(option) ? 1 : 0);
    }

    /**
     * 修改网站设置
     */
    @PreAuthorize("@ss.hasPermi('system:option:edit')" )
    @Log(title = "网站设置" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody List<Option> options) {
        return toAjax(optionService.updateBatchById(options) ? 1 : 0);
    }

    /**
     * 删除网站设置
     */
    @PreAuthorize("@ss.hasPermi('system:option:remove')" )
    @Log(title = "网站设置" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{titles}" )
    public AjaxResult remove(@PathVariable String[] titles) {
        return toAjax(optionService.removeByIds(Arrays.asList(titles)) ? 1 : 0);
    }
}
