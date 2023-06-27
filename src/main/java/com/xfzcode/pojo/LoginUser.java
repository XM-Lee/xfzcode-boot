package com.xfzcode.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: XMLee
 * @Date: 2023/2/14 17:30
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;


    private List<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> collect = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return collect;
    }

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return user.getPassword();
    }

    //TODO
    @Override
    public String getUsername() {
//        return user.getUserName();
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {          // 账号是否没有过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {           // 账号是否没有被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {      // 账号的凭证是否没有过期
        return true;
    }

    @Override
    public boolean isEnabled() {                    // 账号是否可用
        return true;
    }
}
