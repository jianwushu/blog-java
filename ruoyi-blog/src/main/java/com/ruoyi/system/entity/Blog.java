package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bk_blogs")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(defaultValue = "文章id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String img;

    private String title;

    private String text;

    private Long authorId;

    private Long categoryId;

    private Integer views;

    private Integer status;

    private String password;

    private Boolean allowFeed;

    private Boolean allowComment;

    private Boolean allowShare;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date updateTime;

    @Version
    private Integer version;

    @TableLogic
    private Boolean deleted;
}
