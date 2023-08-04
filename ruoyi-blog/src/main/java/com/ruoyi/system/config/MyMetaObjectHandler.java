package com.ruoyi.system.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author ilvyu.cn
 * @Date 2020/12/20 11:24
 * @Version 520.1314
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 数据第一次存储自动填充创建、更新时间
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    /**
     * 数据二次更新自动修改更新
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
