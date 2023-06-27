package com.xfzcode.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:07
 * @Description:
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_permission")
public class RolePermission {

    private Long roleId;
    private Long permissionId;
}
