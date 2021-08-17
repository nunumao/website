package com._365d1.web.utils;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/13 22:49
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.web.config.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.ObjectUtils;

import java.util.Map;

public class LoginUser {

    private static LoginUser _instance;

    private LoginUser() {
    }

    private Map<String, Object> getAuthenticationDecode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String, Object> map = (Map<String, Object>) details.getDecodedDetails();
        return map;
    }

    public static LoginUser instance() {
        if (_instance == null) {
            _instance = new LoginUser();
        }
        return _instance;
    }

    public String userId() {
        return this.getField("user_name");
    }

    public String clientId() {
        return this.getField("client_id");
    }

    public String storeId() {
        return this.getField("store_id");
    }

    public String retailId() {
        return this.getField("retail_id");
    }

    private String getField(String field){
        if (ObjectUtils.isEmpty(getAuthenticationDecode().get(field))) {
            throw new CustomException("[ TOKEN 令牌] 缺失 " + field + " 值");
        }
        return getAuthenticationDecode().get(field).toString();
    }


}
