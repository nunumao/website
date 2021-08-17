package com._365d1.web.config.security.login;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/16 14:15
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.ResultFormat;
import com._365d1.model.User;
import com._365d1.service.UserService;
import com._365d1.web.config.security.GrantTypeEnum;
import com._365d1.web.config.security.IAuthentication;
import com._365d1.web.config.security.system.PasswordLoginFilter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Setter
public class Oauth2LoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    private Object[] extendService;

    private IAuthentication loginFilter;

    protected Oauth2LoginFilter() {
        super(new AntPathRequestMatcher("/oauth2/token"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            this.error("登录失败,不支持[ " + request.getMethod() + " ]认证", response);
        }
        String grantType = request.getParameter("grantType");
        if (StringUtils.isEmpty(grantType)) {
            grantType = request.getParameter("grant_type");
            if (StringUtils.isEmpty(grantType)) {
                return this.error("登录失败,认证类型不能为空", response);
            }
        }
        User user;
        Oauth2LoginAuthenticationToken authenticationToken;
        if (GrantTypeEnum.REFRESH.getValue().equals(grantType) || GrantTypeEnum.CLIENT.getValue().equals(grantType) || "client_credentials".equals(grantType)) {
            authenticationToken = new Oauth2LoginAuthenticationToken(grantType, null);
        } else {
            loginFilter = new PasswordLoginFilter();

            user = loginFilter.attemptAuthentication(userService, request, response, extendService);
            if (ObjectUtils.isEmpty(user)) {
                return this.error("登录失败,用户不存在", response);
            }

            authenticationToken = new Oauth2LoginAuthenticationToken(user.getId(), user);
        }

        this.setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    public void setDetails(HttpServletRequest request, Oauth2LoginAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private Authentication error(String message, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(ResultFormat.response(ResultFormat.ERROR_CODE, message, "").toJson());
        response.getWriter().close();
        return null;
    }

}
