package com.xfzcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfzcode.pojo.RolePermission;
import com.xfzcode.vo.system.PermissionsRoleVo;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:16
 * @Description:
 */
public interface RolePermissionService extends IService<RolePermission> {
    boolean deleteByRoleId(Long id);

    List<PermissionsRoleVo> selectPermissionsByRoleId(List<Long> roleIds);
}
