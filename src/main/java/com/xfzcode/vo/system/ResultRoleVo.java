package com.xfzcode.vo.system;

import com.xfzcode.pojo.Permission;
import com.xfzcode.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 9:47
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultRoleVo implements Serializable {
    private Role role;
    private List<Permission> permissions;
}
