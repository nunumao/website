package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/1 01:33
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.ResultFormat;
import com._365d1.common.utils.SQLDatetime;
import com._365d1.model.Folder;
import com._365d1.service.FolderService;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "目录管理")
@RestController(value = ManagerBase.CRTL_PREFIX + "FolderController")
@RequestMapping(value = "folder")
public class FolderController extends ManagerBase {

    @Autowired
    private FolderService folderService;

    @ApiOperation(value = "目录列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "上级ID") @RequestParam(value = "pid", defaultValue = "0") String pid
    ) {
        QueryWrapper<Folder> query = new QueryWrapper<>();
        query.eq(Folder.PRARENT_ID, pid);
        query.eq(Folder.IS_SYSTEM, 1);
        query.orderByDesc(Folder.CREATE_TIME);
        List<Folder> list = folderService.list(query);
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "新增目录")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "上级ID") @RequestParam(value = "pid", defaultValue = "0") String pid,
            @ApiParam(value = "名称") @RequestParam(value = "name") String name
    ) {
        Folder folder = new Folder();
        folder.setName(name);
        folder.setPrarentId(pid);
        folder.setIsSystem(1);
        folder.setCreateTime(SQLDatetime.time());
        boolean flag = folderService.save(folder);
        if (flag) {
            return ResultFormat.success("新增成功", folder.getId());
        }
        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "编辑目录")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "名称") @RequestParam(value = "name") String name
    ) {
        Folder folder = new Folder();
        folder.setName(name);
        QueryWrapper<Folder> query = new QueryWrapper<>();
        query.eq(Folder.ID, id);
        boolean flag = folderService.update(folder, query);
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "删除目录")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        QueryWrapper<Folder> query = new QueryWrapper<>();
        query.eq(Folder.ID, id);
        boolean flag = folderService.remove(query);
        if (flag) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }


}
