package com.xfzcode.vo.auth;

import com.xfzcode.pojo.Permission;
import com.xfzcode.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/11 17:47
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo implements Serializable {
    private User user;

    private List<String> permissions;
    private List<Permission> permissionDetail;

    private String token;
}
