package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 站点表
// +----------------------------------------------------------------------
// | 创建时间: 2021-07-10 23:20
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
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "site")
public class Site implements Serializable {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String IDENTIFIER = "identifier";
	public static final String STATUS = "status";

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private String id;

	/**
	 * 站点名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 站点标识
	 */
	@TableField(value = IDENTIFIER)
	private String identifier;

	/**
	 * 状态[0 禁用 | 1 启用]
	 */
	@TableField(value = STATUS)
	private Integer status;

}