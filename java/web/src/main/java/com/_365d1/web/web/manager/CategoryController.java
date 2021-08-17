package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/10 23:16
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.ResultFormat;
import com._365d1.common.utils.tree.TreeParser;
import com._365d1.model.Category;
import com._365d1.model.Model;
import com._365d1.service.CategoryService;
import com._365d1.service.ModelService;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "栏目管理")
@RestController(value = ManagerBase.CRTL_PREFIX + "CategoryController")
@RequestMapping(value = "category")
public class CategoryController extends ManagerBase {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelService modelService;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "站点ID") @RequestParam(value = "siteId", defaultValue = "1") Integer siteId
    ) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.eq(Category.SITE_ID, siteId);
        List<Category> list = categoryService.list(query);
        if (list.size() > 0) {
            list = TreeParser.getTreeList(0, list);
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.eq(Category.ID, id);
        Category category = categoryService.getOne(query);
        if (!ObjectUtils.isEmpty(category)) {
            return ResultFormat.success("查询成功", category);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "模型")
    @RequestMapping(value = "model", method = RequestMethod.GET)
    public Object model() {
        List<Model> list = modelService.list();
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "上级")
    @RequestMapping(value = "parent", method = RequestMethod.GET)
    public Object parent() {
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.eq(Category.PARENT_ID, 0);
        List<Category> list = categoryService.list(query);
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "站点ID") @RequestParam(value = "siteId", defaultValue = "1") Integer siteId,
            @ApiParam(value = "栏目名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "上级ID") @RequestParam(value = "parentId", defaultValue = "0") Integer parentId,
            @ApiParam(value = "模型ID") @RequestParam(value = "modelId") Integer modelId
    ) {
        Category category = new Category();
        category.setSiteId(siteId);
        category.setName(name);
        category.setParentId(parentId);
        category.setModelId(modelId);

        boolean flag = categoryService.save(category);
        if (flag) {
            return ResultFormat.success("新增成功", category.getId());
        }
        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id,
            @ApiParam(value = "站点ID") @RequestParam(value = "siteId") Integer siteId,
            @ApiParam(value = "上级ID") @RequestParam(value = "parentId") Integer parentId,
            @ApiParam(value = "栏目名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "模型ID") @RequestParam(value = "modelId") Integer modelId
    ) {
        Category category = new Category();
        category.setSiteId(siteId);
        category.setName(name);
        category.setParentId(parentId);
        category.setModelId(modelId);

        boolean flag = categoryService.update(category, new QueryWrapper<Category>().eq(Category.ID, id));
        if (flag) {
            return ResultFormat.success("编辑成功", category.getId());
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        int count = categoryService.count(new QueryWrapper<Category>().eq(Category.PARENT_ID, id));
        if (count > 0) {
            return ResultFormat.error("请先删除子栏目");
        }
        boolean flag = categoryService.removeById(id);
        if (flag) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}
