package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/15 22:05
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com._365d1.common.utils.ResultFormat;
import com._365d1.common.utils.tree.TreeParser;
import com._365d1.model.Category;
import com._365d1.model.Model;
import com._365d1.model.ModelField;
import com._365d1.model.Site;
import com._365d1.service.*;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "内容管理")
@RestController(value = ManagerBase.CRTL_PREFIX + "ContentController")
@RequestMapping(value = "content")
public class ContentController extends ManagerBase {

    @Autowired
    private SiteService siteService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelFieldService modelFieldService;

    @Autowired
    private ContentService contentService;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "栏目ID") @RequestParam(value = "categoryId") String categoryId,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        Category category = categoryService.getById(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            return ResultFormat.error("栏目不存在");
        }
        Model model = modelService.getById(category.getModelId());
        if (ObjectUtils.isEmpty(model)) {
            return ResultFormat.error("模型不存在");
        }
        IPage<HashMap> list = contentService.queryPage(model.getTableName(), categoryId, "", page);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "站点")
    @RequestMapping(value = "site", method = RequestMethod.GET)
    public Object site() {
        List<Site> list = siteService.list();
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "栏目")
    @RequestMapping(value = "category", method = RequestMethod.GET)
    public Object category(
            @ApiParam(value = "站点ID") @RequestParam(value = "siteId") Integer siteId
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

    @ApiOperation(value = "栏目详情")
    @RequestMapping(value = "categoryDetail", method = RequestMethod.GET)
    public Object categoryDetail(
            @ApiParam(value = "栏目ID") @RequestParam(value = "categoryId") Integer categoryId
    ) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.eq(Category.ID, categoryId);
        Category category = categoryService.getOne(query);
        if (!ObjectUtils.isEmpty(category)) {
            return ResultFormat.success("查询成功", category);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "模型")
    @RequestMapping(value = "model", method = RequestMethod.GET)
    public Object model(
            @ApiParam(value = "栏目ID") @RequestParam(value = "categoryId") String categoryId
    ) {
        Category category = categoryService.getOne(new QueryWrapper<Category>().eq(Category.ID, categoryId));
        if (ObjectUtils.isEmpty(category)) {
            return ResultFormat.error("栏目不存在");
        }
        Model model = modelService.getOne(new QueryWrapper<Model>().eq(Model.ID, category.getModelId()));
        if (ObjectUtils.isEmpty(model)) {
            return ResultFormat.error("模型不存在");
        }
        List<ModelField> list = modelFieldService.list(new QueryWrapper<ModelField>()
                .eq(ModelField.MODEL_ID, model.getId())
                .eq(ModelField.IS_HIDDEN, 0));
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id,
            @ApiParam(value = "栏目ID") @RequestParam(value = "categoryId") Integer categoryId
    ) {
        HashMap<String, Object> entity = contentService.queryDetail(categoryId, id);
        if (!ObjectUtils.isEmpty(entity)) {
            return ResultFormat.success("查询成功", entity);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "栏目ID") @RequestParam(value = "categoryId") String categoryId,
            @ApiParam(value = "内容") @RequestParam(value = "content") String content
    ) {
        JSONObject obj = JSONUtil.parseObj(content);
        Category category = categoryService.getById(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            return ResultFormat.error("栏目不存在");
        }
        Model model = modelService.getById(category.getModelId());
        if (ObjectUtils.isEmpty(model)) {
            return ResultFormat.error("模型不存在");
        }
        QueryWrapper<ModelField> fieldQuery = new QueryWrapper<>();
        fieldQuery.eq(ModelField.MODEL_ID, category.getModelId());
        List<ModelField> list = modelFieldService.list(fieldQuery);
        if (list.size() <= 0) {
            return ResultFormat.error("模型字段不存在");
        }
        Map<String, Object> contentMap = new HashMap<>();
        for (String key : obj.keySet()) {
            if (list.stream().map(field -> field.getName().equals(key)).count() > 0) {
                contentMap.put(key, obj.get(key));
            }
        }
        boolean flag = contentService.create(model.getId(), contentMap);
        if (flag) {
            return ResultFormat.success("新增成功", "");
        }
        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id,
            @ApiParam(value = "栏目ID") @RequestParam(value = "categoryId") String categoryId,
            @ApiParam(value = "内容") @RequestParam(value = "content") String content
    ) {
        JSONObject obj = JSONUtil.parseObj(content);
        Category category = categoryService.getById(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            return ResultFormat.error("栏目不存在");
        }
        Model model = modelService.getById(category.getModelId());
        if (ObjectUtils.isEmpty(model)) {
            return ResultFormat.error("模型不存在");
        }
        QueryWrapper<ModelField> fieldQuery = new QueryWrapper<>();
        fieldQuery.eq(ModelField.MODEL_ID, category.getModelId());
        List<ModelField> list = modelFieldService.list(fieldQuery);
        if (list.size() <= 0) {
            return ResultFormat.error("模型字段不存在");
        }
        Map<String, Object> contentMap = new HashMap<>();
        for (String key : obj.keySet()) {
            if (list.stream().map(field -> field.getName().equals(key)).count() > 0) {
                contentMap.put(key, obj.get(key));
            }
        }

        boolean flag = contentService.modify(id, model.getId(), contentMap);
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

}
