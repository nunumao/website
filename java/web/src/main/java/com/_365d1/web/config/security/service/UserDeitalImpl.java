package com._365d1.web.config.security.service;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2020/3/23 14:29
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.model.User;
import com._365d1.service.UserService;
import com._365d1.web.utils.LoginRoleEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDeitalImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userService.getOne(new QueryWrapper<User>().eq(User.ID, account));
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 角色配置
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        int roleValue = 7;
        for (int i = 0; i < Integer.toBinaryString(roleValue).length(); i++) {
            //拿这个数字和 0x01 左移N位后位与即可得到
            if ((roleValue & (0x01 << i)) != 0) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(LoginRoleEnum.getNameByValue(roleValue & (0x01 << i))));
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPassword(), true, true, true, true, grantedAuthorityList);
    }

}
