package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/3 07:40
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import lombok.Data;

import java.io.Serializable;

@Data
public class SqlField implements Serializable {

    private String Field;
    private String Type;

}