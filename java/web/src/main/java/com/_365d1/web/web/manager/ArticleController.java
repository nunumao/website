package com._365d1.web.web.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/1/25 22:28
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.model.Article;
import com._365d1.service.ArticleService;
import com._365d1.web.web.ManagerBase;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = ManagerBase.CRTL_PREFIX + "ArticleController")
@RequestMapping(value = "article")
public class ArticleController extends ManagerBase {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        query.orderByDesc(Article.CREATE_TIME);
        IPage<Article> list = articleService.page(new Page<>(1, 10), query);
        model.addAttribute("list", list);
        return "/manager/article_list";
    }

}
