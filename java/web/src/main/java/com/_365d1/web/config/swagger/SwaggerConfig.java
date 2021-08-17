package com._365d1.web.config.swagger;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/26 2:33
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;

import java.util.HashMap;

@Configuration
public class SwaggerConfig {

    @Autowired
    private DocketUtils docketUtils;

    @Bean
    public Docket manager() {
        return docketUtils.buildDocket(
                "管理端",
                "com._365d1.web.web.manager",
                apiInfo(),
                new HashMap<String, String>() {{
                    put("all", "默认");
                }}
        );
    }

    @Bean
    public Docket portal() {
        return docketUtils.buildDocket(
                "PC",
                "com._365d1.web.controller.portal",
                apiInfo(),
                new HashMap<String, String>() {{
                    put("all", "默认");
                }}
        );
    }


    @Bean
    public SecurityConfiguration security() {
        return docketUtils.defaultClient("client", "tETiLBc6Jbw1QVf4PsFd0Y2MjnHSgGRo");
    }

    private ApiInfo apiInfo() {
        return docketUtils.buildApiInfo("网站服务", "v 0.0.1", "更新时间 2020年3月26日");
    }

}
