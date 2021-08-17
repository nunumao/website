package com._365d1.model.vo;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/3/1 02:50
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.tree.Tree;
import lombok.Data;

import java.util.List;

@Data
public class SideMenuVo implements Tree<SideMenuVo> {

    private Integer id;
    private Integer parentId;
    private String name;
    private String path;

    private List<SideMenuVo> child;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getParentId() {
        return this.parentId;
    }

    @Override
    public void setChild(List<SideMenuVo> child) {
        this.child = child;
    }
}
