package com.xfzcode.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.pojo.Role;
import com.xfzcode.vo.system.RoleVo;

public interface RoleService extends IService<Role> {

    boolean saveRoleAndPermission(RoleVo roleVo);

    boolean removeRole(Long id);

    boolean updateRoleAndPermission(RoleVo roleVo);

    HttpResult selectByTree(Integer currentPage, Integer pageSize, String roleName);
}
