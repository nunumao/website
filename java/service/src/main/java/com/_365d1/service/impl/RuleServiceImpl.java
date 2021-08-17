package com._365d1.service.impl;
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

import com._365d1.common.utils.tree.TreeParser;
import com._365d1.model.enums.ModulesEnum;
import com._365d1.model.enums.PlatformEnum;
import com._365d1.model.vo.MainMenuVo;
import com._365d1.model.vo.SideMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com._365d1.dao.RuleDao;
import com._365d1.model.Rule;
import com._365d1.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RuleServiceImpl extends ServiceImpl<RuleDao, Rule> implements RuleService {

    @Autowired
    private RuleDao ruleDao;

    @Override
    public List<Rule> queryMenu() {
        return ruleDao.selectMenu();
    }

    @Override
    public List<Rule> queryTree() {
        List<Rule> list = ruleDao.selectList(new QueryWrapper<Rule>()
                .orderByAsc(Rule.SORT)
        );
        if (list.size() > 0) {
            list = TreeParser.getTreeList(0, list);
        }
        return list;
    }

}