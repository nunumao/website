package com._365d1.web.web.portal;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/2/21 02:25
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.model.Category;
import com._365d1.service.CategoryService;
import com._365d1.service.ContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller(value = "CategoryController")
@RequestMapping(value = {"list/{id}.html", "list/{id}/{p}.html"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContentService contentService;

    @GetMapping
    public String list(
            Model model,
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "p", required = false) Integer p
    ) {

        if (ObjectUtils.isEmpty(p)) {
            p = 1;
        }

        List<Category> menu = categoryService.list(new QueryWrapper<Category>().eq(Category.PARENT_ID, 0));
        model.addAttribute("menu", menu);

        Category current = categoryService.getById(id);
        model.addAttribute("current", current);

        model.addAttribute("topActive", current.getId());
        if (!current.getParentId().equals(0)) {
            id = current.getParentId();
            model.addAttribute("topActive", current.getParentId());
        }

        List<Category> category = categoryService.list(new QueryWrapper<Category>()
                .eq(Category.PARENT_ID, id)
        );
        model.addAttribute("category", category);

        if (current.getParentId().equals(0)) {
            IPage<HashMap> list = contentService.queryAllPage(current.getId(), "", p);
            model.addAttribute("list", list);
        } else {
            IPage<HashMap> list = contentService.queryPage(current.getId(), "", p);
            model.addAttribute("list", list);
        }

        return "list";
    }

}
