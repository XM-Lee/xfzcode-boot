package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xfzcode.constants.ResultMessage;
import com.xfzcode.exception.AuthException;
import com.xfzcode.mapper.PermissionMapper;
import com.xfzcode.mapper.UserMapper;
import com.xfzcode.pojo.LoginUser;
import com.xfzcode.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询用户数据
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        // 如果查询不到数据，说明用户名或者密码错误，直接抛出异常
        if (user == null) {
            //throw new RuntimeException("用户名或者密码错误");
            throw new AuthException(ResultMessage.LOGIN_ERROR);
        }
        List<String> permissions = permissionMapper.findUserPermissionById(user.getId());
        // 将查询到的对象转换成Spring Security所需要的UserDetails对象
        return new LoginUser(user, permissions);
    }
}
