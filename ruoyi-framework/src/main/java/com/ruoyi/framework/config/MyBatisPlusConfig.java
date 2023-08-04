package com.ruoyi.framework.config;

import org.springframework.context.annotation.Bean;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author ilvyu.cn
 * @Date 2020/11/15 16:52
 * @Version 520.1314
 */
public class MyBatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }

    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 如果是对全表的删除或更新操作，就会终止该操作
     */
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * sql性能规范插件(垃圾SQL拦截)
     * 如有需要可以启用
     */
//	@Bean
//	public IllegalSQLInnerInterceptor illegalSQLInnerInterceptor() {
//		return new IllegalSQLInnerInterceptor();
//	}
}
