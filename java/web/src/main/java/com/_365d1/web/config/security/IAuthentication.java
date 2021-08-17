package com._365d1.web.config.security;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/12/4 14:17
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.ResultFormat;
import com._365d1.model.User;
import com._365d1.model.vo.User4LoginVo;
import com._365d1.service.UserService;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IAuthentication {

    User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response) throws IOException;

    User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response, Object... objects) throws IOException;

    default User error(String message, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(ResultFormat.response(ResultFormat.ERROR_CODE, message, "").toJson());
        response.getWriter().close();
        return null;
    }

}
