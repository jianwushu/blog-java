package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图片对象 images
 *
 * @author ruoyi
 * @date 2021-03-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("bk_images")
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 图片id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /** 归属用户id */
    @Excel(name = "归属用户id")
    private Long userId;
    /** 文件夹id */
    @Excel(name = "文件夹id")
    private Long floderId;
    /** 存储策略 */
    @Excel(name = "存储策略")
    private String strategy;
    /** 路径 */
    @Excel(name = "路径")
    private String path;
    /** 名称 */
    @Excel(name = "名称")
    private String name;
    /** 别名 */
    @Excel(name = "别名")
    private String aliasName;
    /** 路径名称 */
    @Excel(name = "路径名称")
    private String pathname;
    /** 大小 */
    @Excel(name = "大小")
    private Long size;
    /** 文件类型 */
    @Excel(name = "文件类型")
    private String mime;
    /** sha1 */
    @Excel(name = "sha1")
    private String sha1;

}
