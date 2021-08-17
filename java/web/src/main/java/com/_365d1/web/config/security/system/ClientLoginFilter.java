package com._365d1.web.config.security.system;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 客户端授权
// +----------------------------------------------------------------------
// | 时　　间: 2021/1/22 11:08
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientLoginFilter implements IAuthentication {

    @Override
    public User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return null;
    }

    @Override
    public User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response, Object... objects) throws IOException {
        return null;
    }

}
