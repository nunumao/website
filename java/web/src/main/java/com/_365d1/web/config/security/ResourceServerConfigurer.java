package com._365d1.web.config.security;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/23 15:46
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.web.config.security.exception.ResourceException;
import com._365d1.web.config.security.login.Oauth2LoginSecurityConfigurer;
import com._365d1.web.config.security.token.TokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private static final String[] matchers = {
            "/oauth/**",
            "/oauth2/**",
            "/manager/**"
    };

    private static final String[] ignores = {
            "/manager/index",
            "/manager/assets/**"
    };

    @Value(value = "${jwt.key}")
    private String jwtKey;

    @Autowired
    private Oauth2LoginSecurityConfigurer oauth2LoginSecurityConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
                .csrf().disable()
                .cors()
            .and()
                .requestMatchers()
                .antMatchers(matchers)
                .antMatchers(HttpMethod.OPTIONS)
            .and()
                .authorizeRequests()
                .antMatchers("/wap/**").access("#oauth2.hasAnyScope('wap','all')")
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(ignores).permitAll()
                .anyRequest().authenticated()
            .and()
                .apply(oauth2LoginSecurityConfigurer);
        // @formatter:on
    }

    @Autowired
    private TokenConverter tokenConverter;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(tokenConverter);
        converter.setSigningKey(jwtKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Autowired
    private ResourceException resourceException;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(resourceException);
    }


}
