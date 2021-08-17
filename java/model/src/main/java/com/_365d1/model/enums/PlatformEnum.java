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
public enum PlatformEnum {

    PLATFORM("平台端", 0),
    STORE("商家端", 1),
    USER("用户端", 2);

    private String name;
    private int value;

    public static final Map<Integer, String> maps = new HashMap<>();

    static {
        for (PlatformEnum item : values()) {
            maps.put(item.getValue(), item.getName());
        }
    }

    public static String getNameByValue(Integer value) {
        return maps.get(value);
    }

}
