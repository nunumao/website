package com._365d1.web.config.security;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/21 23:44
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.web.config.security.service.UserDeitalImpl;
import com._365d1.web.config.security.token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value(value = "${jwt.key}")
    private String jwtKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtKey);
        return converter;
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        CustomTokenEnhancer enhancer = new CustomTokenEnhancer();
        return enhancer;
    }

    @Autowired
    private UserDeitalImpl userDeital;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;


    /**
     * 认证服务安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 允许使用表单提交的方式进行权限 可以以POST表单 访问 /oauth/token
                .allowFormAuthenticationForClients()
                // 访问认证服务器获取 TokenKey 需要使用 client-id client-secret 进行 basic 认证访问 /oauth/token_key
                .tokenKeyAccess("permitAll()")
                // 暂时未知具体作用
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 认证服务端点配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        endpoints
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                // 用户服务
                .userDetailsService(userDeital)
                // 刷新令牌
                .reuseRefreshTokens(true)
                // 连接 authenticationManager 支持 password 认证模式
                .authenticationManager(authenticationManager)
                // 设置端点请求允许 Method
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 设置 token 服务
                .tokenServices(tokenServices(tokenEnhancerChain, endpoints.getClientDetailsService()))
                // 设置自定义异常
                .exceptionTranslator(customWebResponseExceptionTranslator);
    }

    /**
     * Token 增强
     *
     * @param tokenEnhancerChain
     * @param clientDetailsService
     * @return
     */
    public DefaultTokenServices tokenServices(TokenEnhancerChain tokenEnhancerChain, ClientDetailsService clientDetailsService) {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        return defaultTokenServices;
    }

    /**
     * 配置客户端
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.withClientDetails(clientId -> {
            BaseClientDetails details = new BaseClientDetails();
            details.setClientId("");
            details.setClientSecret(new BCryptPasswordEncoder().encode(""));
            details.setAuthorizedGrantTypes(Arrays.asList(
                    "client_credentials",
                    "password",
                    "refresh_token",
                    "refresh",
                    "client",
                    "wechat_micro"
            ));
            details.setScope(Arrays.asList(
                    "all"
            ));
            details.setAccessTokenValiditySeconds(3600);
            details.setRefreshTokenValiditySeconds(86400);
            return details;
        });
        // @formatter:on
    }

}
