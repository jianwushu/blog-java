package com.ruoyi.system.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.entity.Blog;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2020/11/16 12:47
 * @Version 520.1314
 */
@Data
public class BlogDTO extends Blog {
    private Long id;
    private String title;
    private String img;
    private Integer views;
    private String text;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date createTime;
    private SysUser sysUser;
    private MetaDTO categroy;
    private List<MetaDTO> tags;
}
