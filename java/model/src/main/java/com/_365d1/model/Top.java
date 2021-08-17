package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 创建时间: 2021-08-04 23:10
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

import java.io.Serializable;

@Data
@TableName(value = "top")
public class Top implements Serializable {

	public static final String ID = "id";
	public static final String CATEGORY_ID = "category_id";
	public static final String DETAIL_ID = "detail_id";

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 栏目ID
	 */
	@TableField(value = CATEGORY_ID)
	private Integer categoryId;

	/**
	 * 内容ID
	 */
	@TableField(value = DETAIL_ID)
	private Integer detailId;

}