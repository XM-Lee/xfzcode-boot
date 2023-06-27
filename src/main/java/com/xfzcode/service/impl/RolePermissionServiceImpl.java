package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfzcode.mapper.RolePermissionMapper;
import com.xfzcode.pojo.RolePermission;
import com.xfzcode.service.RolePermissionService;
import com.xfzcode.vo.system.PermissionsRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:17
 * @Description:
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional
    public boolean deleteByRoleId(Long id) {
        return  rolePermissionMapper.deleteByRoleId(id);
    }

    @Override
    public List<PermissionsRoleVo> selectPermissionsByRoleId(List<Long> roleIds) {
        return rolePermissionMapper.selectPermissionsByRoleId(roleIds);
    }
}
