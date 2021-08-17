package com._365d1.web.web.portal;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/17 02:39
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
import com._365d1.web.web.PortalBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = PortalBase.CRTL_PREFIX + "ContentController")
@RequestMapping(value = "content")
public class ContentController extends PortalBase {

    @Autowired
    private ContentService contentService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "list/{id}", method = RequestMethod.GET)
    public String list(
            Model model,
            @PathVariable(value = "id") Integer id
    ) {
        Category category = categoryService.getById(id);



        model.addAttribute("test", "测试的内容");
        return TEMPLATE + "list";
    }

}
