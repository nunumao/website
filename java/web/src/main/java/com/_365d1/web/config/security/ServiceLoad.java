package com._365d1.web.config.security;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/12/4 17:27
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.service.MicroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceLoad {

    @Autowired
    private MicroService microService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    public Object[] getServices() {
        return new Object[]{authorizationServerTokenServices, microService};
    }

}
