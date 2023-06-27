package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.mapper.RoleMapper;
import com.xfzcode.mapper.UserRoleMapper;
import com.xfzcode.pojo.Permission;
import com.xfzcode.pojo.Role;
import com.xfzcode.pojo.RolePermission;
import com.xfzcode.service.RolePermissionService;
import com.xfzcode.service.RoleService;
import com.xfzcode.vo.system.PermissionsRoleVo;
import com.xfzcode.vo.system.ResultRoleVo;
import com.xfzcode.vo.system.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    @Transactional
    public boolean saveRoleAndPermission(RoleVo roleVo) {
        Role role = roleVo.getRole();
        if (StringUtils.isBlank(role.getRoleName()) || StringUtils.isBlank(role.getAuthority())) {
            return false;
        }
        if (roleMapper.selectList(new QueryWrapper<Role>().eq("authority", role.getAuthority())).size() > 0) {
            return false;
        }
        roleMapper.insert(role);
        Long roleId = role.getId();
        List<Long> permissionsIds = roleVo.getPermissionsIds();
        List<RolePermission> rolePermissionList = permissionsIds.stream().map(item -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(item);
            return rolePermission;
        }).collect(Collectors.toList());
        if (rolePermissionService.saveBatch(rolePermissionList)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeRole(Long id) {
        userRoleMapper.deleteByRoleId(id);
        rolePermissionService.deleteByRoleId(id);
        if (roleMapper.deleteById(id) > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateRoleAndPermission(RoleVo roleVo) {
        try {
            Role role = roleVo.getRole();
            if (StringUtils.isBlank(role.getRoleName()) || StringUtils.isBlank(role.getAuthority())) {
                return false;
            }
            Role authority = roleMapper.selectOne(new QueryWrapper<Role>().eq("authority", role.getAuthority()));
            if (!Objects.equals(authority.getId(), role.getId())) {
                return false;
            }
            roleMapper.updateById(role);
            rolePermissionService.deleteByRoleId(role.getId());
            List<Long> permissionsIds = roleVo.getPermissionsIds();
            List<RolePermission> rolePermissionList = permissionsIds.stream().map(item -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(item);
                return rolePermission;
            }).collect(Collectors.toList());
            if (rolePermissionService.saveBatch(rolePermissionList)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public HttpResult selectByTree(Integer currentPage, Integer pageSize, String roleName) {
        Page<ResultRoleVo> transfer = null;
        try {
            ArrayList<ResultRoleVo> resultRoleVos = new ArrayList<>();
            if (StringUtils.isNotBlank(roleName)) {
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("role_name", roleName);
                transfer = transfer(currentPage, pageSize, resultRoleVos, queryWrapper);
            } else {
                transfer = transfer(currentPage, pageSize, resultRoleVos, null);
            }
            return HttpResult.success(transfer);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return HttpResult.success(transfer);
    }

    private  Page<ResultRoleVo> transfer(Integer currentPage, Integer pageSize, ArrayList<ResultRoleVo> resultRoleVos, QueryWrapper<Role> queryWrapper) {
        Page<Role> rolePage = roleMapper.selectPage(new Page<>(currentPage, pageSize), queryWrapper);
        if (rolePage.getRecords().size() > 0 && rolePage.getRecords() != null) {

            List<Role> records = rolePage.getRecords();
            List<Long> roleIds = records.stream().map(Role::getId).collect(Collectors.toList());
            //Map<Long, Role> roleMaps = records.stream().collect(Collectors.toMap(Role::getId, Function.identity(), (key1, key2) -> key2));

            //查询对应角色的权限数据
            List<PermissionsRoleVo> result = rolePermissionService.selectPermissionsByRoleId(roleIds);
            //通过roleid对权限数据进行分组
            Map<Long, List<PermissionsRoleVo>> groupBy = result.stream().collect(Collectors.groupingBy(PermissionsRoleVo::getRoleId));

            //遍历角色数据，听过角色去匹配权限数据
            for (Role role : records) {
                ResultRoleVo resultRoleVo = new ResultRoleVo();
                resultRoleVo.setRole(role);

                ArrayList<Permission> permissions = new ArrayList<>();
                if (groupBy.containsKey(role.getId())) {
                    List<PermissionsRoleVo> permissionsRoleDtos = groupBy.get(role.getId());
                    for (PermissionsRoleVo permissionsRoleDto : permissionsRoleDtos) {
                        if (Objects.equals(permissionsRoleDto.getRoleId(), role.getId())) {
                            Permission permission = new Permission();
                            BeanUtils.copyProperties(permissionsRoleDto, permission);
                            permissions.add(permission);
                        }
                    }
                }
                resultRoleVo.setPermissions(permissions);
                resultRoleVos.add(resultRoleVo);
            }
            Page<ResultRoleVo> resultPage = new Page<>();
            BeanUtils.copyProperties(rolePage, resultPage);
            resultPage.setRecords(resultRoleVos);
            return resultPage;
        }
        return null;
    }
}
