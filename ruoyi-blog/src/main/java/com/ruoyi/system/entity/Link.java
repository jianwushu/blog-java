package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 友链对象 blog_links
 *
 * @author ruoyi
 * @date 2020-12-31
 */
@Data
@EqualsAndHashCode
@TableName("bk_links")
public class Link implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 友链id */
    @TableId(value = "link_id", type = IdType.AUTO)
    private Long linkId;
    /** 友链网站名称 */
    @Excel(name = "友链网站名称")
    private String linkName;
    /** 友链链接 */
    @Excel(name = "友链链接")
    private String linkUrl;
    /** 友链描述 */
    @Excel(name = "友链描述")
    private String description;
}
