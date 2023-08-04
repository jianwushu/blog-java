package com.ruoyi.system.entity.vo;


import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.Meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2020/11/16 12:31
 * @Version 520.1314
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogVo extends Blog{
    private SysUser sysUser;
    private Meta category;
    private List<Meta> tags;
}
