package com._365d1.web.web;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/5/15
// +----------------------------------------------------------------------
// | 代码创建: 赵婉香 <947554190@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.controller.SuperController;

public class PortalBase extends SuperController {

    public static final String PREFIX = "portal";

    public static final String MAPPING_PREFIX = PREFIX;

    public static final String CRTL_PREFIX = PREFIX;

    public static final String TEMPLATE = PREFIX + "/";

    public static final Integer size = 10;

    public PortalBase() {
        super(PREFIX);
    }

    public PortalBase(String prefix) {
        super(PREFIX + "/" + prefix);
    }
}
