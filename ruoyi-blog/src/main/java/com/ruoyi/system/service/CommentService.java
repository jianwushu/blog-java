package com.ruoyi.system.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.Comment;
import com.ruoyi.system.entity.dto.CommentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */
public interface CommentService extends IService<Comment> {
    List<CommentDTO> getList(@Param("ew") Wrapper wrapper);
    String getCount(Wrapper<Comment> qw);
}
