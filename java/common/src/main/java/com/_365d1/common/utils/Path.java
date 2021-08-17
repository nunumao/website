package com._365d1.common.utils;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/1 09:32
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.util.regex.Matcher;

public class Path {

    public static String getAbsolutePath() {
        return getAbsolutePath("");
    }

    public static String getAbsolutePath(String path) {
        ApplicationHome home = new ApplicationHome();
        String rootPath = home.getDir().getAbsolutePath();
        if (!"".equals(path)) {
            if (!path.endsWith(File.separator)) {
                path += File.separator;
            }
            rootPath = rootPath + File.separator + path.replaceAll("/", Matcher.quoteReplacement(File.separator));
        }
        return rootPath.endsWith(File.separator) ? rootPath : rootPath + File.separator;
    }

    public static String convertSeparator(String path) {
        return path.replaceAll("/", Matcher.quoteReplacement(File.separator));
    }

}
