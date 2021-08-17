package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 微信小程序表
// +----------------------------------------------------------------------
// | 创建时间: 2021-05-19 00:52
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
@TableName(value = "micro")
public class Micro implements Serializable {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String APP_ID = "app_id";
	public static final String SECRET = "secret";
	public static final String TOKEN = "token";
	public static final String AES_KEY = "aes_key";
	public static final String MSG_DATA_FORMAT = "msg_data_format";

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@TableField(value = ID)
	private String id;

	/**
	 * 小程序名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * APP_ID
	 */
	@TableField(value = APP_ID)
	private String appId;

	/**
	 * APP_SERCET
	 */
	@TableField(value = SECRET)
	private String secret;

	/**
	 * 
	 */
	@TableField(value = TOKEN)
	private String token;

	/**
	 * 
	 */
	@TableField(value = AES_KEY)
	private String aesKey;

	/**
	 * 
	 */
	@TableField(value = MSG_DATA_FORMAT)
	private String msgDataFormat;

}