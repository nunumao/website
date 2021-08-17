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

import com._365d1.common.controller.SuperController;
import com._365d1.common.utils.Pages;
import com._365d1.common.utils.ResultFormat;
import com._365d1.model.Category;
import com._365d1.model.Site;
import com._365d1.service.CategoryService;
import com._365d1.service.SiteService;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "站点管理")
@RestController(value = ManagerBase.CRTL_PREFIX + "SiteController")
@RequestMapping(value = "site")
public class SiteController extends ManagerBase<SiteService, Site> {

    @Autowired
    private SiteService siteService;

    @Override
    public void config(SuperController config) {
        config.setService(siteService);
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "名称") @RequestParam(value = "name", defaultValue = "") String name,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        QueryWrapper<Site> query = new QueryWrapper<>();
        if (StringUtils.hasLength(name)) {
            query.like(Site.NAME, name);
        }
        IPage<Site> list = siteService.page(new Pages<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
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
            @ApiParam(value = "标识") @RequestParam(value = "identifier") String identifier,
            @ApiParam(value = "状态") @RequestParam(value = "status", defaultValue = "1") Integer status
    ) {
        Site site = new Site();
        site.setName(name);
        site.setIdentifier(identifier);
        site.setStatus(status);

        return this._add(site);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        boolean flag = siteService.removeById(id);
        if (flag) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}
