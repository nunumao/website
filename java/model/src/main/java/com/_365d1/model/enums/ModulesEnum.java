package com._365d1.model.enums;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/20 19:37
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
public enum ModulesEnum {

    TRAVEL("旅游系统", 7),
    HOTEL("酒店系统", 1),
    SCENIC("景区系统", 8),
    NOTES("游记攻略", 5),
    SHOP("商城系统", 3),
    MARKET("超市系统", 6),
    OPERATION("运营管理", 4),
    USER("用户中心", 0),
    OFFICE("办公系统", 2);

    private String name;
    private int value;

    public static final Map<Integer, String> maps = new HashMap<>();

    static {
        for (ModulesEnum item : values()) {
            maps.put(item.getValue(), item.getName());
        }
    }

    public static String getNameByValue(Integer value) {
        return maps.get(value);
    }

}
