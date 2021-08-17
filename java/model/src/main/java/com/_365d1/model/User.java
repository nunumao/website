package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 用户表
// +----------------------------------------------------------------------
// | 创建时间: 2021-05-19 00:37
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
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@TableName(value = "user")
public class User implements Serializable {

	public static final String ID = "id";
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String ROLE = "role";
	public static final String PHONE = "phone";
	public static final String GENDER = "gender";
	public static final String NICK_NAME = "nick_name";
	public static final String FACE = "face";
	public static final String ID_CARD = "id_card";
	public static final String LOGIN_COUNT = "login_count";
	public static final String LAST_LOGIN_IP = "last_login_ip";
	public static final String LAST_LOGIN_TIME = "last_login_time";
	public static final String ORIGIN = "origin";
	public static final String IS_ADMIN = "is_admin";
	public static final String CREATE_TIME = "create_time";
	public static final String UPDATE_TIME = "update_time";
	public static final String STATUS = "status";

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@TableField(value = ID)
	private String id;

	/**
	 * 账号
	 */
	@TableField(value = ACCOUNT)
	private String account;

	/**
	 * 密码
	 */
	@TableField(value = PASSWORD)
	private String password;

	/**
	 * 姓名
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 安全角色
	 */
	@TableField(value = ROLE)
	private Integer role;

	/**
	 * 手机
	 */
	@TableField(value = PHONE)
	private String phone;

	/**
	 * 性别
	 */
	@TableField(value = GENDER)
	private Integer gender;

	/**
	 * 昵称
	 */
	@TableField(value = NICK_NAME)
	private String nickName;

	/**
	 * 头像
	 */
	@TableField(value = FACE)
	private String face;

	/**
	 * 身份证号
	 */
	@TableField(value = ID_CARD)
	private String idCard;

	/**
	 * 登录次数
	 */
	@TableField(value = LOGIN_COUNT)
	private Integer loginCount;

	/**
	 * 最后登录IP
	 */
	@TableField(value = LAST_LOGIN_IP)
	private String lastLoginIp;

	/**
	 * 最后登录时间
	 */
	@TableField(value = LAST_LOGIN_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp lastLoginTime;

	/**
	 * 注册来源[使用ClientID标记来源]
	 */
	@TableField(value = ORIGIN)
	private String origin;

	/**
	 * 是否管理员
	 */
	@TableField(value = IS_ADMIN)
	private Integer isAdmin;

	/**
	 * 创建时间
	 */
	@TableField(value = CREATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = UPDATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp updateTime;

	/**
	 * 状态 [ -1 禁用 | 0 审核 | 1 正常 ]
	 */
	@TableField(value = STATUS)
	private Integer status;

}