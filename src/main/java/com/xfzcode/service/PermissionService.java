package com.xfzcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfzcode.pojo.Permission;
import com.xfzcode.vo.system.PermissionTreeVo;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 11:33
 * @Description:
 */
public interface PermissionService extends IService<Permission> {
    List<PermissionTreeVo> listAll();

    List<Permission> findUserPermissionDetailById(Long userId);

    List<String> findUserPermissionById(Long userId);
}
