package com._365d1.web.config.security.login;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/17 1:09
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.model.User;
import com._365d1.web.config.security.GrantTypeEnum;
import com._365d1.web.config.security.UserUtils;
import com._365d1.web.utils.LoginRole;
import com._365d1.web.utils.LoginRoleEnum;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Oauth2LoginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Oauth2LoginAuthenticationToken authenticationToken = (Oauth2LoginAuthenticationToken) authentication;
        Oauth2LoginAuthenticationToken authenticationResult;
        if (authenticationToken.getPrincipal().toString().equals(GrantTypeEnum.REFRESH.getValue())) {
            authenticationResult = new Oauth2LoginAuthenticationToken(GrantTypeEnum.REFRESH.getValue(), null, null);
        } else if (authenticationToken.getPrincipal().toString().equals(GrantTypeEnum.CLIENT.getValue())
                || authenticationToken.getPrincipal().toString().equals("client_credentials")) {
            authenticationResult = new Oauth2LoginAuthenticationToken(GrantTypeEnum.CLIENT.getValue(), null, null);
        } else {
            User user = authenticationToken.getUser();

            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            int roleValue = ObjectUtils.isEmpty(user.getRole()) ? LoginRole.MEMBER_VALUE : user.getRole();
            for (int i = 0; i < Integer.toBinaryString(roleValue).length(); i++) {
                //拿这个数字和 0x01 左移N位后位与即可得到
                if ((roleValue & (0x01 << i)) != 0) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(LoginRoleEnum.getNameByValue(roleValue & (0x01 << i))));
                }
            }
            String password;
            if (StringUtils.isEmpty(user.getPassword())) {
                password = "";
            } else {
                password = user.getPassword();
            }
            UserDetails userDetails = UserUtils.create(user.getId(), password, grantedAuthorityList);
            authenticationResult = new Oauth2LoginAuthenticationToken(user.getId(), user, userDetails.getAuthorities());
        }
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authenticate) {
        return Oauth2LoginAuthenticationToken.class.isAssignableFrom(authenticate);
    }

}
