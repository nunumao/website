package com._365d1.web.config.security.login;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/17 1:11
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class Oauth2LoginAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    private User user;

    public Oauth2LoginAuthenticationToken(Object principal, User user) {
        super(null);
        this.principal = principal;
        this.user = user;
        setAuthenticated(false);
    }

    public Oauth2LoginAuthenticationToken(Object principal, User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public User getUser(){
        return user;
    }
}
