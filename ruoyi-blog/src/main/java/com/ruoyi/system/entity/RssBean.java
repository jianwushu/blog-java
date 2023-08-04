package com.ruoyi.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author ilvyu.cn
 * @Date 2021/3/24 20:05
 * @Version 520.1314
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RssBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String link;
    private String description;
    private String pubDate;


}
