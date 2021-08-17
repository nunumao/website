package com._365d1.web.config.security.exception;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 资源服务器异常处理
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/23 13:18
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.ResultFormat;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResourceException implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String message = e.getMessage();
        int code = 401;
        if (message.contains("Full authentication is required")) {
            message = "请求缺少 TOKEN 信息";
        } else if (message.contains("Access token expired")) {
            code = 402;
            message = "TOKEN 已经过期";
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(ResultFormat.response(code, message, "").toJson());
    }

}
