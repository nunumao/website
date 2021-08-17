package com._365d1.web.config.security.token;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: Token 附带信息增强
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/22 18:08
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2Request request = authentication.getOAuth2Request();

        Map<String, Object> map = new HashMap<>();

        // --- 增加角色ID ---------------------------------------
        map.put("user_id", "");
        // --- 增加角色值 ---------------------------------------
        List<String> roleList = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            roleList.add(grantedAuthority.getAuthority());
        }
        map.put("authorities", roleList.toArray());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
        return accessToken;
    }

}
