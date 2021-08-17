package com._365d1.dao;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-07-11 13:15
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.model.Model;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface ModelDao extends BaseMapper<Model> {

    void insertModelTable(
            @Param(value = "name") String name,
            @Param(value = "table") String table
    );

    void insertModelExtendTable(
            @Param(value = "name") String name,
            @Param(value = "table") String table
    );

    void deleteModelTable(
            @Param(value = "table") String table
    );

    void deleteModelExtendTable(
            @Param(value = "table") String table
    );

}