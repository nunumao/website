package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: OAuth2.0客户端表
// +----------------------------------------------------------------------
// | 创建时间: 2021-05-19 00:16
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
import java.sql.Date;

@Data
@TableName(value = "client")
public class Client implements Serializable {

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";
	public static final String TOKEN_VALIDITY = "token_validity";
	public static final String REFRESH_VALIDITY = "refresh_validity";
	public static final String REDIRECT_URIS = "redirect_uris";
	public static final String SALT = "salt";
	public static final String VALIDITY = "validity";
	public static final String IS_SYSTEM = "is_system";

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@TableField(value = ID)
	private String id;

	/**
	 * 用户ID
	 */
	@TableField(value = USER_ID)
	private String userId;

	/**
	 * 客户端ID
	 */
	@TableField(value = CLIENT_ID)
	private String clientId;

	/**
	 * 客户端SECRET
	 */
	@TableField(value = CLIENT_SECRET)
	private String clientSecret;

	/**
	 * 授权方式
	 */
	@TableField(value = GRANT_TYPE)
	private String grantType;

	/**
	 * 作用域
	 */
	@TableField(value = SCOPE)
	private String scope;

	/**
	 * 令牌有效期
	 */
	@TableField(value = TOKEN_VALIDITY)
	private Integer tokenValidity;

	/**
	 * 刷新令牌有效期
	 */
	@TableField(value = REFRESH_VALIDITY)
	private Integer refreshValidity;

	/**
	 * 跳转地址
	 */
	@TableField(value = REDIRECT_URIS)
	private String redirectUris;

	/**
	 * 盐
	 */
	@TableField(value = SALT)
	private String salt;

	/**
	 * 过期时间
	 */
	@TableField(value = VALIDITY)
	private Date validity;

	/**
	 * 内部系统 [ 0 否 | 1 是 ]
	 */
	@TableField(value = IS_SYSTEM)
	private Integer isSystem;

}