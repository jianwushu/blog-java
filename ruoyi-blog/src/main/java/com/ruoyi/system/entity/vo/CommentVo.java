package com.ruoyi.system.entity.vo;

import lombok.Data;

/**
 * @Author ilvyu.cn
 * @Date 2020/11/16 21:07
 * @Version 520.1314
 */
@Data
public class CommentVo {
    private Long id;

    private String avatar;

    private String nickName;

    private String email;

    private String text;

    private String ip;

    private Long blogId;

    private Long parentId;
}
