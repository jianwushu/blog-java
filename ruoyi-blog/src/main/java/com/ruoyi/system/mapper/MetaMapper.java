package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.Meta;
import com.ruoyi.system.entity.dto.MetaDTO;
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

public interface MetaMapper extends BaseMapper<Meta> {
    @Select("select * from bk_metas where type = 'category' and id = #{id}")
    Meta getCategory(Long id);

    @Select("select * from bk_metas  " +
            "left join bk_blog_meta " +
            "on meta_id = id " +
            "where type = 'tag' and blog_id = #{id}")
    List<Meta> getTags(@Param("id")Long id);

    @Insert("insert into bk_blog_meta value(#{blog_id},#{tag_id})")
    void addBlogTag(@Param("blog_id") Long blog_id,@Param("tag_id") Long tag_id);

    @Delete("delete from bk_blog_meta where meta_id = #{tag_id}")
    void delBlogTag(Long tag_id);

    @Update("update bk_metas set count=count +1,version = version +1 where id = #{id} and version=version")
    void upCount(@Param("id")Long id);
    @Update("update bk_metas set count=count -#{num},version = version +1 where id = #{id} and version=version")
    void downCount(@Param("id")Long id,@Param("num")Integer num);
}
