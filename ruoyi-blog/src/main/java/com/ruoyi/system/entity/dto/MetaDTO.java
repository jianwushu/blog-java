package com.ruoyi.system.entity.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.util.Date;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/9 18:14
 * @Version 520.1314
 */
@Data
public class MetaDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String type;

    private String description;

    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private Date updateTime;
}
