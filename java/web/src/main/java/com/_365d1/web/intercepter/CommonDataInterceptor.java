package com._365d1.web.intercepter;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/8/5 11:36
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonDataInterceptor implements HandlerInterceptor {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContentService contentService;

    private enum Type {
        CATEGORY_LIST,
        TOP_LIST,
        NEW_LIST
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!ObjectUtils.isEmpty(modelAndView)) {
            modelAndView.addObject(Type.CATEGORY_LIST.name(), categoryService.list(new QueryWrapper<Category>()
                    .eq(Category.PARENT_ID, 0).orderByAsc(Category.SORT)));

            modelAndView.addObject(Type.TOP_LIST.name(), contentService.top());

            modelAndView.addObject(Type.NEW_LIST.name(), contentService.sidebarNewList());

        }
    }

}
