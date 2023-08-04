package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoyi.system.entity.Comment;
import com.ruoyi.system.entity.dto.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haohao
 * @since 2020-11-13
 */

public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select * from bk_comments ${ew.customSqlSegment}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentComment",
                    one = @One(
                            select = "com.ruoyi.system.mapper.CommentMapper.selectById"
                    )
            )
    })
    List<CommentDTO> getList(@Param(Constants.WRAPPER) Wrapper wrapper);
    @Select("select count(*) from bk_comments")
    String getCount(@Param(Constants.WRAPPER) Wrapper<Comment> wrapper);
}
