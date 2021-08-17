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

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com._365d1.common.controller.SuperController;
import com._365d1.common.utils.Pages;
import com._365d1.common.utils.ResultFormat;
import com._365d1.model.Model;
import com._365d1.model.Site;
import com._365d1.service.ModelService;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

import java.sql.Timestamp;

@Api(tags = "模型管理")
@RestController(value = ManagerBase.CRTL_PREFIX + "ModelController")
@RequestMapping(value = "model")
public class ModelController extends ManagerBase<ModelService, Model> {

    @Autowired
    private ModelService modelService;

    @Override
    public void config(SuperController config) {
        config.setService(modelService);
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "名称") @RequestParam(value = "name", defaultValue = "") String name,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        QueryWrapper<Model> query = new QueryWrapper<>();
        if (StringUtils.hasLength(name)) {
            query.like(Site.NAME, name);
        }
        IPage<Model> list = modelService.page(new Pages<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "存在")
    @RequestMapping(value = "hasTable", method = RequestMethod.GET)
    public Object hasTable(
            @ApiParam(value = "表名称") @RequestParam(value = "table") String table
    ) {
        int count = modelService.count(new QueryWrapper<Model>().eq(Model.TABLE_NAME, table));
        if (count <= 0) {
            return ResultFormat.success("未存在", "");
        }
        return ResultFormat.error("已存在");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @Override
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        return super.detail(id);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "表名称") @RequestParam(value = "tableName") String tableName
    ) {
        Model model = new Model();
        model.setName(name);
        model.setTableName(tableName);
        model.setCreateTime(new Timestamp(DateTime.now().getTime()));

        Integer lastId = modelService.create(model);
        if (!ObjectUtils.isEmpty(lastId)) {
            return ResultFormat.success("新增成功", lastId);
        }
        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id,
            @ApiParam(value = "名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "表名称") @RequestParam(value = "tableName") String tableName
    ) {
        Model model = new Model();
        model.setName(name);
        model.setTableName(tableName);
        boolean flag = modelService.update(model, new QueryWrapper<Model>().eq(Model.ID, id));
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        boolean flag = modelService.removeSingle(id);
        if (flag) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}
