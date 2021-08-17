package com._365d1.model;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 创建时间: 2021-01-25 21:55
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.GeneralViews;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@TableName(value = "article")
public class Article implements Serializable {

	public static final String ID = "id";
	public static final String CATE_ID = "cate_id";
	public static final String AUTHOR_ID = "author_id";
	public static final String TAG = "tag";
	public static final String TYPE = "type";
	public static final String ALIAS = "alias";
	public static final String IS_TOP = "is_top";
	public static final String IS_LOCK = "is_lock";
	public static final String TITLE = "title";
	public static final String THUMB = "thumb";
	public static final String INTRO = "intro";
	public static final String CONTENT = "content";
	public static final String MARKDOWN = "markdown";
	public static final String COMM_NUMS = "comm_nums";
	public static final String VIEW_NUMS = "view_nums";
	public static final String LIKE_NUMS = "like_nums";
	public static final String TEMPLATE = "template";
	public static final String META = "meta";
	public static final String CREATE_TIME = "create_time";
	public static final String STATUS = "status";

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@TableField(value = ID)
	@JsonView(value = GeneralViews.ISimpleView.class)
	private Integer id;

	/**
	 * 
	 */
	@TableField(value = CATE_ID)
	private Integer cateId;

	/**
	 * 
	 */
	@TableField(value = AUTHOR_ID)
	private Integer authorId;

	/**
	 * 
	 */
	@TableField(value = TAG)
	private String tag;

	/**
	 * 
	 */
	@TableField(value = TYPE)
	private Integer type;

	/**
	 * 
	 */
	@TableField(value = ALIAS)
	private String alias;

	/**
	 * 
	 */
	@TableField(value = IS_TOP)
	private Integer isTop;

	/**
	 * 
	 */
	@TableField(value = IS_LOCK)
	private Integer isLock;

	/**
	 * 
	 */
	@TableField(value = TITLE)
	@JsonView(value = GeneralViews.ISimpleView.class)
	private String title;

	/**
	 * 
	 */
	@TableField(value = THUMB)
	@JsonView(value = GeneralViews.ISimpleView.class)
	private String thumb;

	/**
	 * 
	 */
	@TableField(value = INTRO)
	@JsonView(value = GeneralViews.ISimpleView.class)
	private String intro;

	/**
	 * 
	 */
	@TableField(value = CONTENT)
	private String content;

	/**
	 * 
	 */
	@TableField(value = MARKDOWN)
	private String markdown;

	/**
	 * 
	 */
	@TableField(value = COMM_NUMS)
	private Integer commNums;

	/**
	 * 
	 */
	@TableField(value = VIEW_NUMS)
	@JsonView(value = GeneralViews.ISimpleView.class)
	private Integer viewNums;

	/**
	 * 点赞/喜欢
	 */
	@TableField(value = LIKE_NUMS)
	private Integer likeNums;

	/**
	 * 
	 */
	@TableField(value = TEMPLATE)
	private String template;

	/**
	 * 
	 */
	@TableField(value = META)
	private String meta;

	/**
	 * 
	 */
	@TableField(value = CREATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@JsonView(value = GeneralViews.ISimpleView.class)
	private Timestamp createTime;

	/**
	 * 
	 */
	@TableField(value = STATUS)
	private Integer status;

}