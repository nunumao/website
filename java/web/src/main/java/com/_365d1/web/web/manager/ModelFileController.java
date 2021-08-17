package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/31 02:38
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.ResultFormat;
import com._365d1.model.ModelField;
import com._365d1.service.ModelFieldService;
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

@Api(tags = "模型字段")
@RestController(value = ManagerBase.CRTL_PREFIX + "ModelFileController")
@RequestMapping(value = "modelFiled")
public class ModelFileController extends ManagerBase {

    @Autowired
    private ModelFieldService modelFieldService;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        List<ModelField> list = modelFieldService.list(new QueryWrapper<ModelField>().eq(ModelField.MODEL_ID, id));
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "名称") @RequestParam(value = "name") String name
    ) {
        ModelField modelField = new ModelField();
        modelField.setName(name);


        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        ModelField modelField = modelFieldService.getById(id);
        if (!ObjectUtils.isEmpty(modelField)) {
            return ResultFormat.error("数据不存在");
        }
        if (modelField.getIsSystem().equals(1)) {
            return ResultFormat.error("不能删除默认字段");
        }
        boolean flag = modelFieldService.removeById(id);
        if (flag) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}
