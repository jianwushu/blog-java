package com.ruoyi.web.controller.blog;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Images;
import com.ruoyi.system.service.ImagesService;
import com.ruoyi.system.util.UPUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片Controller
 *
 * @author ruoyi
 * @date 2021-03-16
 */
@RestController
@RequestMapping("/system/images" )
public class ImagesController extends BaseController {
    @Autowired
    private ImagesService imagesService;

    /**
     * 查询图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:images:list')")
    @GetMapping("/list")
    public TableDataInfo list(Images images) {
        startPage();
        LambdaQueryWrapper<Images> lqw = Wrappers.lambdaQuery(images);
        if (images.getUserId() != null) {
            lqw.eq(Images::getUserId, images.getUserId());
        }
        if (images.getFloderId() != null) {
            lqw.eq(Images::getFloderId, images.getFloderId());
        }
        if (StringUtils.isNotBlank(images.getStrategy())) {
            lqw.eq(Images::getStrategy, images.getStrategy());
        }
        if (StringUtils.isNotBlank(images.getPath())) {
            lqw.eq(Images::getPath, images.getPath());
        }
        if (StringUtils.isNotBlank(images.getName())) {
            lqw.like(Images::getName, images.getName());
        }
        if (StringUtils.isNotBlank(images.getAliasName())) {
            lqw.like(Images::getAliasName, images.getAliasName());
        }
        if (StringUtils.isNotBlank(images.getPathname())) {
            lqw.like(Images::getPathname, images.getPathname());
        }
        if (images.getSize() != null) {
            lqw.eq(Images::getSize, images.getSize());
        }
        if (StringUtils.isNotBlank(images.getMime())) {
            lqw.eq(Images::getMime, images.getMime());
        }
        if (StringUtils.isNotBlank(images.getSha1())) {
            lqw.eq(Images::getSha1, images.getSha1());
        }
        List<Images> list = imagesService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:images:export')" )
    @Log(title = "图片" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Images images) {
        LambdaQueryWrapper<Images> lqw = new LambdaQueryWrapper<Images>(images);
        List<Images> list = imagesService.list(lqw);
        ExcelUtil<Images> util = new ExcelUtil<Images>(Images. class);
        return util.exportExcel(list, "images" );
    }

    /**
     * 获取图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:images:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(imagesService.getById(id));
    }

    /**
     * 新增图片
     */
    @PreAuthorize("@ss.hasPermi('system:images:add')" )
//    @Log(title = "图片" , businessType = BusinessType.INSERT)
    @PostMapping
    public TableDataInfo add(@RequestParam("file") MultipartFile[] files) {
        List<Images> images = UPUtil.uploadFile(files);
        imagesService.saveBatch(images);
        System.out.println(images.size());
        return getDataTable(images);
    }

    /**
     * 修改图片
     */
    @PreAuthorize("@ss.hasPermi('system:images:edit')" )
    @Log(title = "图片" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Images images) {
        return toAjax(imagesService.updateById(images) ? 1 : 0);
    }

    /**
     * 删除图片
     */
    @PreAuthorize("@ss.hasPermi('system:images:remove')" )
    @Log(title = "图片" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(imagesService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
