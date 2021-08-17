package com._365d1.config.mybatis;
// +----------------------------------------------------------------------
// | 广西西途比网络科技有限公司 www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 请输入描述
// +----------------------------------------------------------------------
// | 时　　间: 2019/1/7  17:09
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@MapperScan(value = "com._365d1.dao")
@Component
public class MyBatisPlusConfiguration {

    @Value(value = "${entity.packages}")
    private String entityPackages;

    @Autowired
    @Qualifier(value = "druidDataSource")
    private DataSource dataSource;

    @Autowired
    private MybatisConfiguration batisConfiguration;

    @Bean(name = "sqlSessionFactory")
    public MybatisSqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        batisConfiguration.getGlobalConfig().getDbConfig()
        // 设置 mybatis configuration
        bean.setConfiguration(batisConfiguration);
        // 添加mapper 扫描路径 多模块需要使用 classpath*
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**.xml"));
        // 设置dataSource
        bean.setDataSource(dataSource);
        // 设置实体类所在的 package 路径
        bean.setTypeAliasesPackage(entityPackages);
        return bean;
    }


}
