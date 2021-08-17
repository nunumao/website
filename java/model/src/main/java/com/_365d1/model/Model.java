package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 模型表
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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "model")
public class Model implements Serializable {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String TABLE_NAME = "table_name";
	public static final String CREATE_TIME = "create_time";

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 表名称
	 */
	@TableField(value = TABLE_NAME)
	private String tableName;

	/**
	 * 创建时间
	 */
	@TableField(value = CREATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createTime;

}