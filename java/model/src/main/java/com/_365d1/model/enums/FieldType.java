package com._365d1.model.enums;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/6/30 18:13
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum FieldType {

    CATEGORY("category", "栏目"),
    TITLE("title", "标题"),
    THUMB("thumb", "缩略图"),
    KEYWORDS("keywords", "关键词"),
    COPYFROM("copyfrom", "来源"),
    DESCRIPTION("description", "摘要"),
    EDITOR("editor", "内容"),
    ORIGIN("origin", "源内容"),
    VIEW_NUM("view_num", "浏览次数"),
    IS_TOP("is_top", "推荐"),
    CREATE_TIME("create_time", "创建时间"),
    STATUS("status", "状态");


    private String id;
    private String value;

    public static final Map<String, String> idMaps = new HashMap<>();
    public static final Map<String, String> valueMaps = new HashMap<>();

    static {
        for (FieldType item : values()) {
            idMaps.put(item.getValue(), item.getId());
            valueMaps.put(item.getId(), item.getValue());
        }
    }

    public static String getIdByValue(String value) {
        return idMaps.get(value);
    }

    public static String getValueByid(String value) {
        return valueMaps.get(value);
    }

}
