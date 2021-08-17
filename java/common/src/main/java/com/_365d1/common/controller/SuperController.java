package com._365d1.common.controller;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2019-12-02 13:34
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.Pages;
import com._365d1.common.utils.ResultFormat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.*;
import java.util.*;

@Slf4j
public class SuperController<S, E> {

    protected QueryWrapper<E> queryWrapper = null;
    protected boolean autoSearch = true;
    protected Integer pageSize = 10;
    private String prefix = "";

    @Autowired
    private HttpServletRequest request;

    private S service;

    public void setService(S service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        this._binder(binder);
    }

    protected void _binder(WebDataBinder binder) {

    }

    public SuperController() {

    }

    public SuperController(String prefix) {
        // 查询类是否存在 @RequestMapping 注解
        if (this.getClass().isAnnotationPresent(RequestMapping.class)) {
            this.prefix = prefix;
            // 获取注解实例
            RequestMapping mapping = this.getClass().getAnnotation(RequestMapping.class);
            // 获取实例代理
            InvocationHandler handler = Proxy.getInvocationHandler(mapping);
            try {
                // 获取 value 值
                Field field = handler.getClass().getDeclaredField("memberValues");
                field.setAccessible(true);
                Map<String, Object> values = (Map<String, Object>) field.get(handler);
                String[] paths = (String[]) values.get("value");
                List<String> pathList = new ArrayList<String>();
                // 将 value 添加前缀
                for (String s : paths) {
                    pathList.add(this.prefix + "/" + s);
                }
                // 修改 @RequestMapping value 值
                String[] newPaths = new String[pathList.size()];
                newPaths = pathList.toArray(newPaths);
                values.put("value", newPaths);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void config(SuperController config) {

    }

    protected void _initialize(SuperController supers) {
    }

    public Object list(QueryWrapper<E> query, Integer page) {
        return this._list(query, page);
    }

    protected Object _list(QueryWrapper<E> query, Integer page) {
        this.config(this);
        if (this.service == null) {
            return ResultFormat.error("服务未定义");
        }
        this.queryWrapper = new QueryWrapper<>();
        this._initialize(this);
        // 判断是否需要自动匹配查询
//        if (this.autoSearch) {
//            this._search(e);
//        }
//        this._filter(e);
        IPage<E> list = ((IService) service).page(new Pages(page, this.pageSize), this.queryWrapper);
        if (list.getTotal() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("暂无数据");
    }

    public Object detail(Integer id) {
        return this._detail(id);
    }

    private Object _detail(Integer id) {
        this.config(this);
        if (this.service == null) {
            return ResultFormat.error("服务未定义");
        }
        E e = (E) ((IService) service).getById(id);
        if (!ObjectUtils.isEmpty(e)) {
            return ResultFormat.success("查询成功", e);
        }
        return ResultFormat.error("没有数据");
    }

    private void _search(E e) {
        Map<String, Object> map = new HashMap();
        for (Field obj : e.getClass().getDeclaredFields()) {
            for (String param : Collections.list(request.getParameterNames())) {
                if (obj.getName().equals(param) && request.getParameter(param) != null) {
                    if (!"".equals(request.getParameter(param))) {
                        map.put(this.param2column(obj.getName()), request.getParameter(param));
                    }
                }
            }
        }
        this.queryWrapper.allEq(map);
    }

    protected void _filter(E e) {
    }

    public Object add(E e) {
        return this._add(e);
    }

    protected E _before_add(E e) {
        return e;
    }

    protected Object _add(E e) {
        this.config(this);
        if (this.service == null) {
            return ResultFormat.error("服务未定义");
        }
        e = this._before_add(e);
        if (((IService) service).save(e)) {
            return ResultFormat.success("新增成功");
        }
        return ResultFormat.error("新增失败");
    }

    public Object edit(String id, E e) {
        return this._edit(e, id);
    }

    protected E _before_edit(E e) {
        return e;
    }

    protected Object _edit(E e, String id) {
        this.config(this);
        if (this.service == null) {
            return ResultFormat.error("服务未定义");
        }
        e = this._before_edit(e);
        if (((IService) service).update(e, new QueryWrapper<E>().eq("id", id))) {
            return ResultFormat.success("编辑成功");
        }
        return ResultFormat.error("编辑失败");
    }

    public Object edit(String id) {
        return this._edit(id);
    }

    protected Object _edit(String id) {
        this.config(this);
        if (this.service == null) {
            return ResultFormat.error("服务未定义");
        }
        E e = (E) ((IService) service).getOne(new QueryWrapper<E>().eq("id", id));
        if (e != null) {
            return ResultFormat.success("查询成功", e);
        }
        return ResultFormat.error("暂无数据");
    }

    public Object delete(String[] id) {
        return this._delete(id);
    }

    private Object _delete(String[] id) {
        this.config(this);
        if (this.service == null) {
            return ResultFormat.error("服务未定义");
        }
        if (((IService) service).removeByIds(Arrays.asList(id))) {
            return ResultFormat.success("删除成功");
        }
        return ResultFormat.error("删除失败");
    }

    // 将请求的驼峰命名参数转为数据库下划线命名
    protected String param2column(String param) {
        String str = "";
        for (char c : param.toCharArray()) {
            if (Character.isUpperCase(c)) {
                str += "_" + Character.toLowerCase(c);
            } else {
                str += c;
            }
        }
        return str;
    }


}
