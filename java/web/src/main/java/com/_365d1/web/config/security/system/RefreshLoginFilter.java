package com._365d1.web.config.security.system;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/12/6 13:43
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.model.User;
import com._365d1.service.UserService;
import com._365d1.web.config.security.IAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RefreshLoginFilter implements IAuthentication {

    @Override
    public User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.attemptAuthentication(userService,request,response);
    }

    @Override
    public User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response, Object... objects) throws IOException {
        String refreshToken = request.getParameter("refreshToken");
        AuthorizationServerTokenServices tokenServices = (AuthorizationServerTokenServices) objects[0];
        return null;
    }

}
