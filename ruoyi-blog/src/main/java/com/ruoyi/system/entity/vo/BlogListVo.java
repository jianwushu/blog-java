package com.ruoyi.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.entity.Meta;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2021/1/7 11:20
 * @Version 520.1314
 */
@Data
public class BlogListVo {
    private Long id;
    private String title;
    private String img;
    private Integer views;
    private String text;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date createTime;
    private SysUser sysUser;
    private MetaVo categroy;
    private List<MetaVo> tags;
}
