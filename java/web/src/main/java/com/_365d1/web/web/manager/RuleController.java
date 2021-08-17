package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/20 20:07
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.GsonUtils;
import com._365d1.common.utils.ResultFormat;
import com._365d1.common.utils.tree.TreeParser;
import com._365d1.model.Rule;
import com._365d1.service.RuleService;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "节点管理")
@RestController(value = ManagerBase.PREFIX + "RuleController")
@RequestMapping(value = "rule")
public class RuleController extends ManagerBase {

    @Autowired
    private RuleService ruleService;

    @ApiOperation(value = "菜单")
    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public Object menu() {
        List<Rule> list = ruleService.queryMenu();
        if (list.size() > 0) {
            list = TreeParser.getTreeList(0, list);
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "节点列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list() {
        List<Rule> list = ruleService.queryTree();
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "查询上级")
    @RequestMapping(value = "parent", method = RequestMethod.GET)
    public Object parent(
            @ApiParam(value = "上级ID") @RequestParam(value = "parent", defaultValue = "0") String parent
    ) {
        QueryWrapper<Rule> query = new QueryWrapper<>();
        query.eq(Rule.PARENT_ID, parent);
        List<Rule> list = ruleService.list(query);
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "节点详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        Rule rule = ruleService.getById(id);
        if (!ObjectUtils.isEmpty(rule)) {
            return ResultFormat.success("查询成功", rule);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "新增节点")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "上级ID") @RequestParam(value = "parentId", defaultValue = "0") Integer parentId,
            @ApiParam(value = "菜单名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "菜单类型") @RequestParam(value = "type") Integer type,
            @ApiParam(value = "作为菜单") @RequestParam(value = "menu", defaultValue = "0") Integer menu,
            @ApiParam(value = "菜单路径") @RequestParam(value = "url", defaultValue = "") String url,
            @ApiParam(value = "页面路径") @RequestParam(value = "path", defaultValue = "") String path,
            @ApiParam(value = "接口") @RequestParam(value = "apis", defaultValue = "") String apis,
            @ApiParam(value = "排序") @RequestParam(value = "sort", defaultValue = "9999") Integer sort
    ) {
        Rule rule = new Rule();
        rule.setType(type);
        rule.setParentId(parentId);
        rule.setName(name);
        rule.setPath(path);
        rule.setMenu(menu);
        rule.setUrl(url);
        if (!StringUtils.isEmpty(apis)) {
            List<String> apiList = GsonUtils.toList(apis, String.class);
            rule.setApis(GsonUtils.toJson(apiList));
        }
        rule.setSort(sort);

        boolean flag = ruleService.save(rule);
        if (flag) {
            return ResultFormat.success("新增成功", rule.getId());
        }
        return ResultFormat.error("新增失败");
    }

    // --- 编辑 ------------------------------------------------------------

    @ApiOperation(value = "编辑菜单")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "上级ID") @RequestParam(value = "parentId", defaultValue = "0") Integer parentId,
            @ApiParam(value = "菜单名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "菜单类型") @RequestParam(value = "type") Integer type,
            @ApiParam(value = "作为菜单") @RequestParam(value = "menu", defaultValue = "0") Integer menu,
            @ApiParam(value = "菜单路径") @RequestParam(value = "url", defaultValue = "") String url,
            @ApiParam(value = "页面路径") @RequestParam(value = "path", defaultValue = "") String path,
            @ApiParam(value = "接口") @RequestParam(value = "apis", defaultValue = "") String apis,
            @ApiParam(value = "排序") @RequestParam(value = "sort", defaultValue = "9999") Integer sort
    ) {

        if (type.equals(2)) {
            // 保持菜单只有一个
            QueryWrapper<Rule> query = new QueryWrapper<>();
            query.eq(Rule.PARENT_ID, parentId);
            Rule up = new Rule();
            up.setMenu(0);
            ruleService.update(up, query);
        }

        Rule rule = new Rule();
        rule.setType(type);
        rule.setParentId(parentId);
        rule.setName(name);
        rule.setPath(path);
        rule.setMenu(menu);
        rule.setUrl(url);
        if (!StringUtils.isEmpty(apis)) {
            List<String> apiList = GsonUtils.toList(apis, String.class);
            rule.setApis(GsonUtils.toJson(apiList));
        }
        rule.setSort(sort);
        QueryWrapper<Rule> upQuery = new QueryWrapper<>();
        upQuery.eq(Rule.ID, id);
        boolean flag = ruleService.update(rule, upQuery);
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    // --- 删除 ------------------------------------------------------------

    @ApiOperation(value = "删除节点")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        // 查询是否含有下级
        QueryWrapper<Rule> query = new QueryWrapper<>();
        query.eq(Rule.PARENT_ID, id);
        List<Rule> list = ruleService.list(query);
        if (list.size() > 0) {
            return ResultFormat.error("请先删除下级");
        }
        boolean result = ruleService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}
