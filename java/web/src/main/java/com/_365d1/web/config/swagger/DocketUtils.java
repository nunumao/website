package com._365d1.web.config.swagger;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/26 2:02
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class DocketUtils {

    private String url = "http://localhost:8888/oauth2/token";

    @Value(value = "${swagger.enable}")
    private boolean enable;

    public Docket buildDocket(String name, String path, ApiInfo apiInfo, Map<String, String> map) {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo)
                .groupName(name)
                .select()
                .apis(RequestHandlerSelectors.basePackage(path))
                .build()
                .securityContexts(Collections.singletonList(securityContext(map)))
                .securitySchemes(Arrays.asList(passwordSchema(map), clientSchema(map)));
    }


    private List<SecurityReference> defaultAuth(Map<String, String> map) {
        List<AuthorizationScope> scopeList = newArrayList();
        for (String key : map.keySet()) {
            scopeList.add(new AuthorizationScope(key, map.get(key)));
        }
        return new ArrayList<SecurityReference>() {{
            add(new SecurityReference("Password", scopeList.toArray(new AuthorizationScope[scopeList.size()])));
            add(new SecurityReference("Client", scopeList.toArray(new AuthorizationScope[scopeList.size()])));
        }};
    }


    private SecurityContext securityContext(Map<String, String> map) {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth(map))
                .build();
    }

    // 密码认证
    private OAuth passwordSchema(Map<String, String> scopesMap) {
        List<AuthorizationScope> authorizationScopeList = newArrayList();
        for (String key : scopesMap.keySet()) {
            authorizationScopeList.add(new AuthorizationScope(key, scopesMap.get(key)));
        }
        List<GrantType> grantTypes = newArrayList();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(url);
        grantTypes.add(passwordCredentialsGrant);
        return new OAuth("Password", authorizationScopeList, grantTypes);
    }

    // 客户端认证
    private OAuth clientSchema(Map<String, String> scopesMap) {
        List<AuthorizationScope> authorizationScopeList = newArrayList();
        for (String key : scopesMap.keySet()) {
            authorizationScopeList.add(new AuthorizationScope(key, scopesMap.get(key)));
        }
        List<GrantType> grantTypes = newArrayList();
        GrantType clientCredentialsGrant = new ClientCredentialsGrant(url);
        grantTypes.add(clientCredentialsGrant);
        return new OAuth("Client", authorizationScopeList, grantTypes);
    }

    // --- 辅助方法 ----------------------------------------------------------

    public ApiInfo buildApiInfo(String title, String version, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .contact(new Contact("官方网站", "https://www.365d1.com", "292018748@qq.com"))
                .version(version)
                .description(description)
                .build();
    }

    public SecurityConfiguration defaultClient(String client, String secret) {
        return SecurityConfigurationBuilder.builder()
                .clientId(client)
                .clientSecret(secret)
                .build();
    }

}
