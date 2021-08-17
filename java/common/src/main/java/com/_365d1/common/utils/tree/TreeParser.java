package com._365d1.common.utils.tree;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/22 15:49
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

public class TreeParser<T> {

    public static <T extends Tree<T>> List<T> getTreeList(Integer topId, List<T> entityList) {
        List<T> resultList = new ArrayList<>();

        //获取顶层元素集合
        Integer parentId;
        for (T entity : entityList) {
            parentId = entity.getParentId();
            if (parentId.equals(topId)) {
                resultList.add(entity);
            }
        }

        //获取每个顶层元素的子数据集合
        for (T entity : resultList) {
            entity.setChild(getSubList(entity.getId(), entityList));
        }

        return resultList;
    }

    private static <T extends Tree<T>> List<T> getSubList(Integer id, List<T> entityList) {
        List<T> childList = new ArrayList<T>();
        Integer parentId;

        //子集的直接子对象
        for (T entity : entityList) {
            parentId = entity.getParentId();
            if (id.equals(parentId)) {
                childList.add(entity);
            }
        }
        //子集的间接子对象
        for (T entity : childList) {
            entity.setChild(getSubList(entity.getId(), entityList));
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
