package com._365d1.config.druid;
// +----------------------------------------------------------------------
// | 广西西途比网络科技有限公司 www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 数据源配置
// +----------------------------------------------------------------------
// | 时　　间: 2019/1/7  17:08
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Slf4j
@Component
public class DruidDataSourceConfiguration {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Primary
    @Bean(name = "druidDataSource")
    public DruidDataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 1");
        try {
            // 监控统计用
            dataSource.setFilters("stat");
            // 防御 sql 注入
            dataSource.setFilters("wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }


}
