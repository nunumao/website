package com._365d1.web.config.security.system;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/12/4 15:12
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.model.User;
import com._365d1.model.enums.UserStatusEnum;
import com._365d1.service.UserService;
import com._365d1.web.config.security.IAuthentication;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PasswordLoginFilter implements IAuthentication {

    @Override
    public User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.attemptAuthentication(userService, request, response, null);
    }

    @Override
    public User attemptAuthentication(UserService userService, HttpServletRequest request, HttpServletResponse response, Object... objects) throws IOException {
        // 接收请求参数
        String account = request.getParameter("account");
        if (StringUtils.isEmpty(account)) {
            account = request.getParameter("username");
            if (StringUtils.isEmpty(account)) {
               return this.error("登录失败,账号不能为空", response);
            }
        }
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(password)) {
            return this.error("登录失败,密码不能为空", response);
        }

        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq(User.ACCOUNT, account);
        User user = userService.getOne(query);
        if (ObjectUtils.isEmpty(user)) {
            return this.error("登录失败,账号不存在", response);
        }
        // 判断禁用
        if (user.getStatus() == UserStatusEnum.FORBIDDEN.getValue()) {
            return this.error("登录失败,账号已禁用", response);
        }
        if (ObjectUtils.isEmpty(user.getRole())) {
            return this.error("登录失败,账号角色错误", response);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean flag = encoder.matches(password, user.getPassword());
        if (!flag) {
            return this.error("登录失败,密码错误", response);
        }

        return user;
    }

}
