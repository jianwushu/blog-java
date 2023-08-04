package com.ruoyi.web.controller.blog;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.entity.RssBean;
import com.ruoyi.system.util.RssUtil;
import com.ruoyi.system.util.QMusicUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author ilvyu.cn
 * @Date 2021/3/24 20:48
 * @Version 520.1314
 */
@RestController
@RequestMapping("/blog/tool")
public class ToolController {

    @GetMapping("/rss")
    public AjaxResult parse(@RequestParam("uri") String uri){
        RssBean rssBean = RssUtil.getRssBean(uri);
        return AjaxResult.success(rssBean);
    }

    @GetMapping("/qmusic")
    public AjaxResult qqMusic(@RequestParam("id") String id){
        List<Map> musics = QMusicUtil.parseMusic(id);
        return AjaxResult.success(musics);
    }
}
