package com.ruoyi.system.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.lucene.PageLucene;

import java.io.IOException;
import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/13 13:39
 * @Version 520.1314
 */
public interface LuceneService {

    void createIndex();

    PageLucene searchIndex(PageLucene pageLucene) throws IOException;

    void updateIndex();

    void addIndex(Blog blog);

    void deleteIndex(Long[] ids);
}
