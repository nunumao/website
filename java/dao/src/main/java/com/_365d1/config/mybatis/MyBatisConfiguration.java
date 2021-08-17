package com._365d1.config.mybatis;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述:  MyBatis 配置
// +----------------------------------------------------------------------
// | 时　　间: 2019/12/1 21:54
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfiguration {

    @Bean
    public MybatisConfiguration batisConfiguration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        // 使用列标签替换列别名 默认:true
        configuration.setUseColumnLabel(true);
        // 使用jdbc的useGeneratedKeys获取数据库自增主键值
        configuration.setUseGeneratedKeys(true);
        // 开启驼峰命名转换
        configuration.setMapUnderscoreToCamelCase(true);
        // SQL语句控制台打印
        configuration.setLogImpl(StdOutImpl.class);
        // 设置分页插件
        configuration.addInterceptor(paginationInterceptor());

        return configuration;
    }


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
