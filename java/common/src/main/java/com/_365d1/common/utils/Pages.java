package com._365d1.common.utils;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2019-12-02 14:14
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class Pages<T> extends Page<T> {

    public Pages() {
        super();
    }

    public Pages(long current, long size) {
        super(current, size);
    }

    public Pages(long current, long size, long total) {
        super(current, size, total);
    }

    public Pages(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public Pages(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    @JsonView(value = GeneralViews.INormalView.class)
    @Override
    public List<T> getRecords() {
        return super.getRecords();
    }

    @JsonView(value = GeneralViews.INormalView.class)
    @Override
    public long getTotal() {
        return super.getTotal();
    }

    @JsonView(value = GeneralViews.INormalView.class)
    @Override
    public long getSize() {
        return super.getSize();
    }

    @JsonView(value = GeneralViews.INormalView.class)
    @Override
    public long getCurrent() {
        return super.getCurrent();
    }

    @Override
    public List<OrderItem> getOrders() {
        return super.getOrders();
    }

    @Override
    public boolean isSearchCount() {
        return super.isSearchCount();
    }

    @JsonView(value = GeneralViews.INormalView.class)
    @Override
    public long getPages() {
        return super.getPages();
    }

}
