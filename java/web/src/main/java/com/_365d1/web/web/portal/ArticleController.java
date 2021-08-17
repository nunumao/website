package com._365d1.web.web.portal;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/2/21 01:13
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.GeneralViews;
import com._365d1.common.utils.Pages;
import com._365d1.common.utils.ResultFormat;
import com._365d1.model.Article;
import com._365d1.service.ArticleService;
import com._365d1.web.web.PortalBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "文章")
@RestController(value = PortalBase.CRTL_PREFIX + "ArticleController")
@RequestMapping(value = "article")
public class ArticleController extends PortalBase {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "推荐")
    @RequestMapping(value = "hot", method = RequestMethod.GET)
    @JsonView(value = GeneralViews.ISimpleView.class)
    public Object hot(
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        query.eq(Article.TYPE, 0);
        query.eq(Article.IS_TOP, 1);
        query.eq(Article.STATUS, 0);
        query.orderByDesc(Article.CREATE_TIME);
        IPage<Article> list = articleService.page(new Pages<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @JsonView(value = GeneralViews.ISimpleView.class)
    public Object list(
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        query.eq(Article.TYPE, 0);
        query.orderByDesc(Article.CREATE_TIME);
        IPage<Article> list = articleService.page(new Pages<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        query.eq(Article.ID, id);
        query.eq(Article.STATUS, 0);
        Article article = articleService.getOne(query);
        if (!ObjectUtils.isEmpty(article)) {
            return ResultFormat.success("查询成功", article);
        }
        return ResultFormat.error("没有数据");
    }

}
