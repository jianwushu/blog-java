package com.ruoyi.system.entity.lucene;

import com.ruoyi.system.entity.vo.BlogLuceneVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/13 21:08
 * @Version 520.1314
 */
@Data
@AllArgsConstructor
public class PageLucene {
    private Integer current;
    private Integer limit;
    private Long total;
    private String key;
    private List<BlogLuceneVo> blogs;
}
