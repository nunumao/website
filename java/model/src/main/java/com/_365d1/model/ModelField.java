package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 模型字段表
// +----------------------------------------------------------------------
// | 创建时间: 2021-07-11 13:15
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName(value = "model_field")
public class ModelField implements Serializable {

    public static final String ID = "id";
    public static final String MODEL_ID = "model_id";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String FIELD = "field";
    public static final String TIPS = "tips";
    public static final String CSS = "css";
    public static final String MIN_LENGTH = "min_length";
    public static final String MAX_LENGTH = "max_length";
    public static final String PATTERN = "pattern";
    public static final String ERROR = "error";
    public static final String IS_CORE = "is_core";
    public static final String IS_SYSTEM = "is_system";
    public static final String IS_UNIQUE = "is_unique";
    public static final String IS_HIDDEN = "is_hidden";

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 模型ID
     */
    @TableField(value = MODEL_ID)
    private Integer modelId;

    /**
     * 字段类型
     */
    @TableField(value = TYPE)
    private String type;

    /**
     * 名称
     */
    @TableField(value = NAME)
    private String name;

    /**
     * 列名称
     */
    @TableField(value = FIELD)
    private String field;

    /**
     * 表单提示
     */
    @TableField(value = TIPS)
    private String tips;

    /**
     * 样式
     */
    @TableField(value = CSS)
    private String css;

    /**
     * 最小长度
     */
    @TableField(value = MIN_LENGTH)
    private Integer minLength;

    /**
     * 最大长度
     */
    @TableField(value = MAX_LENGTH)
    private Integer maxLength;

    /**
     * 正则表达式
     */
    @TableField(value = PATTERN)
    private String pattern;

    /**
     * 错误提示
     */
    @TableField(value = ERROR)
    private String error;

    /**
     * 核心字段[ 0 核心字段  | 1 扩展字段 ]
     */
    @TableField(value = IS_CORE)
    private Integer isCore;

    /**
     * 系统字段[0 否|1 是]
     */
    @TableField(value = IS_SYSTEM)
    private Integer isSystem;

    /**
     * 必填字段[0 否|1 是]
     */
    @TableField(value = IS_UNIQUE)
    private Integer isUnique;

    /**
     * 是否隐藏[0 否|1 是]
     */
    @TableField(value = IS_HIDDEN)
    private Integer isHidden;

}