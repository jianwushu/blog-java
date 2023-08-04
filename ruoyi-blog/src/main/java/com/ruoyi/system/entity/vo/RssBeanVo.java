package com.ruoyi.system.entity.vo;

import com.ruoyi.system.entity.RssBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2021/3/24 20:57
 * @Version 520.1314
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RssBeanVo extends RssBean {

    private List<RssBean> rssBeans = new ArrayList<>();
}
