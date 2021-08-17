package com._365d1.web.config.security.login;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/17 1:05
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.service.UserService;
import com._365d1.web.config.security.ServiceLoad;
import com._365d1.web.config.security.login.handler.Oauth2LoginFailAuthenticationHandler;
import com._365d1.web.config.security.login.handler.Oauth2LoginSuccessAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class Oauth2LoginSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ServiceLoad serviceLoad;

    @Autowired
    private UserService userService;

    @Autowired
    private Oauth2LoginSuccessAuthenticationHandler oauth2LoginSuccessAuthenticationHandler;

    @Autowired
    private Oauth2LoginFailAuthenticationHandler oauth2LoginFailAuthenticationHandler;

    @Override
    public void configure(HttpSecurity security) throws Exception {

        Oauth2LoginFilter filter = new Oauth2LoginFilter();
        filter.setAuthenticationManager(security.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(oauth2LoginSuccessAuthenticationHandler);
        filter.setAuthenticationFailureHandler(oauth2LoginFailAuthenticationHandler);
        filter.setUserService(userService);
        filter.setExtendService(serviceLoad.getServices());
        Oauth2LoginAuthenticationProvider provider = new Oauth2LoginAuthenticationProvider();
        security
                .authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }


}
