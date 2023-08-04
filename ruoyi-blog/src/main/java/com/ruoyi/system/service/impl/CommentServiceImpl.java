package com.ruoyi.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.Comment;
import com.ruoyi.system.entity.dto.CommentDTO;
import com.ruoyi.system.mapper.CommentMapper;
import com.ruoyi.system.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> getList(Wrapper wrapper) {
        List<CommentDTO> list = commentMapper.getList(wrapper);
        List<CommentDTO> reply = new ArrayList<>();
        for (CommentDTO commentDTO : list) {
            List<Comment> subReply = new ArrayList<>();

            LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper<>();
            qw.eq(Comment::getParentId,commentDTO.getId());
            List<Comment> comments = commentMapper.selectList(qw);

            for (Comment comment : comments) {
                CommentDTO commentDTO1 = new CommentDTO();
                BeanUtils.copyProperties(comment,commentDTO1);
//                commentDTO1.setParentComment(commentDTO);
                subReply.add(commentDTO1);
                getReply(subReply,comment);
            }
            commentDTO.setReplyComment(subReply);
            reply.add(commentDTO);
        }
        return reply;
    }

    @Override
    public String getCount(Wrapper<Comment> qw) {
        return commentMapper.getCount(qw);
    }

    public void getReply(List<Comment> subReply,Comment comment){
        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper<>();
        qw.eq(Comment::getParentId, comment.getId());
        List<Comment> comments = commentMapper.selectList(qw);
        if(comments.size()<1) {
            return;
        }
        for (Comment comment1 : comments) {
            CommentDTO commentDTO1 = new CommentDTO();
            BeanUtils.copyProperties(comment1, commentDTO1);
            commentDTO1.setParentComment(comment);
            subReply.add(commentDTO1);
            getReply(subReply, comment1);
        }
    }

}
