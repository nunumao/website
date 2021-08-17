package com._365d1.web.config.security.login.handler;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/4/17 2:44
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.common.utils.ResultFormat;
import com._365d1.model.LoginToken;
import com._365d1.web.config.security.GrantTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class Oauth2LoginSuccessAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    private boolean isBasic = false;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String msg = "登录";
        String clientId = "";
        String clientSecret = "";

        String grantType = request.getParameter("grantType");
        if (StringUtils.isEmpty(grantType)) {
            grantType = request.getParameter("grant_type");
        }

        if (grantType.equals(GrantTypeEnum.REFRESH.getValue())) {
            msg = "刷新";
        }

        if (grantType.equals(GrantTypeEnum.CLIENT.getValue())) {
            msg = "授权";
        }

        String header = request.getHeader("Authorization");
        if (StringUtils.isEmpty(header) || !header.toLowerCase().startsWith("basic ")) {
            clientId = request.getParameter("clientId");
            clientSecret = request.getParameter("clientSecret");
            isBasic = false;
        } else {
            String[] tokens;
            try {
                tokens = this.extractAndDecodeHeader(header, request);
                if (tokens.length != 2) {
                    this.error(msg + "失败,错误的[ basic client ]信息格式", response);
                    return;
                }
                clientId = tokens[0];
                clientSecret = tokens[1];
                isBasic = true;
            } catch (BadCredentialsException e) {
                this.error(msg + "失败,错误的[ basic client ]信息格式", response);
                return;
            }
        }

        if (StringUtils.isEmpty(clientId)) {
            this.error(msg + "失败, clientId 不能为空", response);
            return;
        }

        if (StringUtils.isEmpty(clientSecret)) {
            this.error(msg + "失败, clientSecret 不能为空", response);
            return;
        }

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (ObjectUtils.isEmpty(clientDetails)) {
            this.error(msg + "失败, clientId 不存在", response);
            return;
        }
        boolean flag = passwordEncoder.matches(clientSecret, clientDetails.getClientSecret());
        if (!flag) {
            this.error(msg + "失败, clientSecret 错误", response);
            return;
        }


        if (!clientDetails.getAuthorizedGrantTypes().contains(grantType)) {
            this.error(msg + "失败, clientId 不支持认证类型[ " + grantType + " ]", response);
            return;
        }

        String scope = request.getParameter("scope");
        if (StringUtils.isEmpty(scope)) {
            this.error(msg + "失败, scope 不能为空", response);
            return;
        }
        if (!clientDetails.getScope().contains(scope)) {
            this.error(msg + "失败, clientId 不支持 scope 域 [ " + scope + " ]", response);
            return;
        }

        TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, clientDetails.getScope(), grantType);

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token;
        if (grantType.equals(GrantTypeEnum.REFRESH.getValue())) {
            String refreshToken = request.getParameter("refreshToken");
            if (StringUtils.isEmpty(refreshToken)) {
                this.error(msg + "失败, refreshToken 不能为空", response);
                return;
            }
            token = authorizationServerTokenServices.refreshAccessToken(refreshToken, tokenRequest);
        } else {
            token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        }

        response.setContentType("application/json;charset=UTF-8");
        if (!isBasic) {
            LoginToken loginToken = new LoginToken();
            loginToken.setTokenType(token.getTokenType());
            loginToken.setAccessToken(token.toString());
            if (!grantType.equals(GrantTypeEnum.CLIENT.getValue())) {
                loginToken.setRefreshToken(token.getRefreshToken().getValue());
                loginToken.setAuthorities(token.getAdditionalInformation().get("authorities"));
            }else{
                loginToken.setRefreshToken("");
                loginToken.setAuthorities("");
            }
            loginToken.setExpires(token.getExpiresIn());
            response.getWriter().write(ResultFormat.response(ResultFormat.SUCCESS_CODE, msg + "成功", loginToken).toJson());
            response.getWriter().close();
        } else {
            response.getWriter().write(new ObjectMapper().writeValueAsString(token));
            response.getWriter().close();
        }
    }

    private void error(String message, HttpServletResponse response) throws IOException {
        if (!isBasic) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(ResultFormat.response(ResultFormat.ERROR_CODE, message, "").toJson());
        response.getWriter().close();
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("解析 client 信息失败");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("错误的 client 信息格式");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }


}
