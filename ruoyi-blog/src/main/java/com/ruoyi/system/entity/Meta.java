package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("bk_metas")

public class Meta implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String type;

    private String description;

    private Integer count;

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
