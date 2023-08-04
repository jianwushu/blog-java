package com.ruoyi.system.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.dto.BlogDTO;
import com.ruoyi.system.entity.vo.BlogListVo;
import com.ruoyi.system.entity.vo.BlogVo;
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

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("select * from bk_blogs ${ew.customSqlSegment}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "author_id", property = "sysUser",
                    one = @One(
                            select = "com.ruoyi.system.mapper.SysUserMapper.selectUserById"
                    )
            ),
            @Result(column = "category_id", property = "category",
                    one = @One(
                            select = "com.ruoyi.system.mapper.MetaMapper.getCategory"
                    )
            ),
            @Result(column = "id", property = "tags",
                    many = @Many(
                            select = "com.ruoyi.system.mapper.MetaMapper.getTags"
                    )
            )
    })
    List<BlogVo> getList(@Param(Constants.WRAPPER) Wrapper<Blog> wrapper);

    @Select("select count(*) from bk_blogs")
    String getCount(@Param(Constants.WRAPPER) Wrapper<Blog> wrapper);

    @Update("update bk_blogs set views=views+1,version = version +1 where id = #{id} and version=version")
    void updView(@Param("id")Long id);
}
