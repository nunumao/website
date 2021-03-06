package com._365d1.service;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-05-19 01:30
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.model.Rule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RuleService extends IService<Rule> {

    /**
     * 查询主菜单
     *
     * @return 菜单列表
     */
    List<Rule> queryMenu();

    /**
     * 查询树形列表
     *
     * @return 列表
     */
    List<Rule> queryTree();

}