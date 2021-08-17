package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 节点表
// +----------------------------------------------------------------------
// | 创建时间: 2021-05-19 01:30
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.tree.Tree;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "rule")
public class Rule implements Tree<Rule> {

    public static final String ID = "id";
    public static final String PARENT_ID = "parent_id";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String PAGE = "page";
    public static final String MENU = "menu";
    public static final String URL = "url";
    public static final String PATH = "path";
    public static final String APIS = "apis";
    public static final String SORT = "sort";

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 上级菜单ID
     */
    @TableField(value = PARENT_ID)
    private Integer parentId;

    /**
     * 类型 [ 0 分组 | 1 功能 | 2 操作 ]
     */
    @TableField(value = TYPE)
    private Integer type;

    /**
     * 名称
     */
    @TableField(value = NAME)
    private String name;

    @TableField(value = PAGE)
    private Integer page;

    /**
     * 菜单
     */
    @TableField(value = MENU)
    private Integer menu;

    /**
     * URL
     */
    @TableField(value = URL)
    private String url;

    /**
     * 页面路径
     */
    @TableField(value = PATH)
    private String path;

    /**
     * 关联接口
     */
    @TableField(value = APIS)
    private String apis;

    /**
     * 排序
     */
    @TableField(value = SORT)
    private Integer sort;


    @TableField(exist = false)
    private List<Rule> child;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return parentId;
    }

    @Override
    public void setChild(List<Rule> child) {
        this.child = child;
    }

}