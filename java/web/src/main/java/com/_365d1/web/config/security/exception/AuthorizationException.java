package com._365d1.web.config.security.exception;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 自定义认证错误
// +----------------------------------------------------------------------
// | 时　　间: 2019/9/22 14:14
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.common.utils.ResultFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationException implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        log.error("认证异常 {}", e.getMessage());
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        String message = oAuth2Exception.getMessage();
        if (oAuth2Exception instanceof UnsupportedGrantTypeException) {
            message = "不支持的认证方式";
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResultFormat.response(401, "认证失败 [ " + message + " ]", ""));
    }

}
