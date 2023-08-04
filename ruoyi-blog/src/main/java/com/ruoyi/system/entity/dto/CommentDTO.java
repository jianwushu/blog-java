package com.ruoyi.system.entity.dto;

import com.ruoyi.system.entity.Comment;
import lombok.Data;


import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2020/11/16 13:51
 * @Version 520.1314
 */
@Data
public class CommentDTO extends Comment
{
    private Comment parentComment;
    private List<Comment> replyComment;
}
