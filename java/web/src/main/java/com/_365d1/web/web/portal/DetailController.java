package com._365d1.web.web.portal;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/29 14:22
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
import com._365d1.web.utils.template.CommonData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller(value = "DetailController")
@RequestMapping(value = "detail/{c}/{id}.html")
public class DetailController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContentService contentService;

    @GetMapping
    public String detail(
            Model model,
            @PathVariable(value = "c") Integer c,
            @PathVariable(value = "id") Integer id
    ) {

        Category current = categoryService.getById(c);
        model.addAttribute("current", current);

        model.addAttribute("topActive", current.getId());
        int topC = c;
        if (!current.getParentId().equals(0)) {
            topC = current.getParentId();
            model.addAttribute("topActive", current.getParentId());
        }

        List<Category> category = categoryService.list(new QueryWrapper<Category>()
                .eq(Category.PARENT_ID, topC)
        );
        model.addAttribute("category", category);

        HashMap<String, Object> entity = contentService.queryDetail(c, id);
        model.addAttribute("entity", entity);

        model.addAttribute("helper", new CommonData());

        return "detail";
    }

}
