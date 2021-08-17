package com._365d1.web.config;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/1 10:27
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.Path;
import com._365d1.web.intercepter.CommonDataInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.regex.Matcher;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CommonDataInterceptor commonDataInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = Path.getAbsolutePath();
        // 检查目录是否存在
        File folder = new File(rootPath + "upload/".replaceAll("/", Matcher.quoteReplacement(java.io.File.separator)));
        if (!folder.isDirectory()) folder.mkdirs();
        String temp = (rootPath + "upload/temp/").replaceAll("/", Matcher.quoteReplacement(java.io.File.separator));
        File tempFolder = new File(temp);
        if (!tempFolder.isDirectory()) tempFolder.mkdirs();

        // 图片上传路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + rootPath + "upload/");

        WebMvcConfigurer.super.addResourceHandlers(registry);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonDataInterceptor).addPathPatterns("/**");
    }

}
