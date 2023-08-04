package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 网站设置对象 blog_options
 *
 * @author ruoyi
 * @date 2020-12-31
 */
@Data
@EqualsAndHashCode
@TableName("bk_options")
public class Option implements Serializable
{
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String val;
    private String type;
    private String description;
}
