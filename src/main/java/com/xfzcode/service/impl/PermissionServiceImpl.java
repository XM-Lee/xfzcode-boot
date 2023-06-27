package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfzcode.mapper.PermissionMapper;
import com.xfzcode.pojo.Permission;
import com.xfzcode.service.PermissionService;
import com.xfzcode.vo.system.PermissionTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 11:34
 * @Description:
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionTreeVo> listAll() {
        //查询出所有的权限-->可以直接写SQL，用对应的Vo去接收就可以了
        List<Permission> permissionList = permissionMapper.selectList(null);
        //将部门转换成Vo
        List<PermissionTreeVo> permissionTreeVoList = permissionList.stream().map((permission) -> {
            PermissionTreeVo vo = new PermissionTreeVo();
            BeanUtils.copyProperties(permission, vo);
            vo.setChildren(null);
            return vo;
        }).collect(Collectors.toList());
        //使用filter去进行拦截，进行判断

        return permissionTreeVoList.stream().filter(organizationVo -> 0 == organizationVo.getParentId())
                .peek(permissionTreeVo -> permissionTreeVo.setChildren(createChildList(permissionTreeVo, permissionTreeVoList)))
                .sorted(Comparator.comparing(PermissionTreeVo::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Permission> findUserPermissionDetailById(Long userId) {
        return this.baseMapper.findUserPermissionDetailById(userId);
    }

    @Override
    public List<String> findUserPermissionById(Long userId) {
        return this.baseMapper.findUserPermissionById(userId);
    }

    /**
     * @param permissionTreeVo  父级
     * @param permissionTreeVoList 对应的list
     * @return
     */
    private static List<PermissionTreeVo> createChildList(PermissionTreeVo permissionTreeVo, List<PermissionTreeVo> permissionTreeVoList) {
        return permissionTreeVoList.stream().filter(model -> permissionTreeVo.getId().equals(model.getParentId()))
                .peek(model -> model.setChildren(createChildList(model, permissionTreeVoList)))
                .sorted((Comparator.comparing(PermissionTreeVo::getId))).collect(Collectors.toList());
    }

}
