package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 目录表
// +----------------------------------------------------------------------
// | 创建时间: 2021-08-01 01:39
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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "folder")
public class Folder implements Serializable {

	public static final String ID = "id";
	public static final String PRARENT_ID = "prarent_id";
	public static final String USER_ID = "user_id";
	public static final String STORE_ID = "store_id";
	public static final String NAME = "name";
	public static final String IS_SYSTEM = "is_system";
	public static final String CREATE_TIME = "create_time";

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private String id;

	/**
	 * 上级目录
	 */
	@TableField(value = PRARENT_ID)
	private String prarentId;

	/**
	 * 所属用户
	 */
	@TableField(value = USER_ID)
	private String userId;

	/**
	 * 所属商家
	 */
	@TableField(value = STORE_ID)
	private String storeId;

	/**
	 * 名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 系统
	 */
	@TableField(value = IS_SYSTEM)
	private Integer isSystem;

	/**
	 * 创建时间
	 */
	@TableField(value = CREATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createTime;

}