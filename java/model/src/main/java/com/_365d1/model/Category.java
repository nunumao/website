package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 创建时间: 2021-02-21 02:35
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
@TableName(value = "category")
public class Category implements Tree<Category> {

    public static final String ID = "id";
    public static final String SITE_ID = "site_id";
    public static final String MODEL_ID = "model_id";
    public static final String NAME = "name";
    public static final String SORT = "sort";
    public static final String COUNT = "count";
    public static final String ALIAS = "alias";
    public static final String INTRO = "intro";
    public static final String ROOT_ID = "root_id";
    public static final String PARENT_ID = "parent_id";
    public static final String TEMPLATE = "template";
    public static final String LOG_TEMPLATE = "log_template";
    public static final String META = "meta";

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 站点ID
     */
    @TableField(value = SITE_ID)
    private Integer siteId;


    /**
     *
     */
    @TableField(value = NAME)
    private String name;

    @TableField(value = MODEL_ID)
    private Integer modelId;

    /**
     *
     */
    @TableField(value = SORT)
    private Integer sort;

    /**
     *
     */
    @TableField(value = COUNT)
    private Integer count;

    /**
     *
     */
    @TableField(value = ALIAS)
    private String alias;

    /**
     *
     */
    @TableField(value = INTRO)
    private String intro;

    /**
     *
     */
    @TableField(value = ROOT_ID)
    private String rootId;

    /**
     *
     */
    @TableField(value = PARENT_ID)
    private Integer parentId;

    /**
     *
     */
    @TableField(value = TEMPLATE)
    private String template;

    /**
     *
     */
    @TableField(value = LOG_TEMPLATE)
    private String logTemplate;

    /**
     *
     */
    @TableField(value = META)
    private String meta;

    @TableField(exist = false)
    private List<Category> child;

    @Override
    public void setChild(List<Category> child) {
        this.child = child;
    }

}