package com._365d1.common.utils;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/1 01:42
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.date.DateTime;

import java.sql.Timestamp;

public class SQLDatetime {

    public static Timestamp time() {
        return SQLDatetime.time(DateTime.now());
    }

    public static Timestamp time(DateTime dateTime) {
        return new Timestamp(dateTime.getTime());
    }

}
