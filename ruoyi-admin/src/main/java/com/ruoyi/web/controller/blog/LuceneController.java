package com.ruoyi.web.controller.blog;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.entity.lucene.PageLucene;
import com.ruoyi.system.service.impl.LuceneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/13 13:53
 * @Version 520.1314
 */
@RestController
@RequestMapping("/blog" )
public class LuceneController {
    @Autowired
    private LuceneServiceImpl luceneService;
    @RequestMapping("/index")
    public String createIndex(){
        luceneService.createIndex();
        return "ok";
    }

    @RequestMapping("/search")
    public AjaxResult searchKey(PageLucene pageLucene){
        PageLucene pageLucene1 = luceneService.searchIndex(pageLucene);
        return AjaxResult.success(pageLucene1);
    }
}
