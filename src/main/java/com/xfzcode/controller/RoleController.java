package com.xfzcode.controller;

import com.xfzcode.annotation.OperationLogAnnotation;
import com.xfzcode.constants.ApiVersion;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.constants.ResultMessage;
import com.xfzcode.service.RolePermissionService;
import com.xfzcode.service.RoleService;
import com.xfzcode.vo.system.RoleVo;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: XMLee
 * @Date: 2023/4/20 9:51
 * @Description:
 */
@RestController
@RequestMapping(ApiVersion.V1_ROLE)
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/listAll")
    @OperationLogAnnotation(description = "查询全部角色数据")
    public HttpResult<?> getRoleListAll() {
        return HttpResult.success(roleService.list());
    }

    @GetMapping()
    @OperationLogAnnotation(description = "查询分页角色",detailParam = "currentPage,pageSize,roleName")
    public HttpResult<?>  getRoleList(@RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                                     @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                     @RequestParam(name = "roleName", required = false) String roleName) {
        return roleService.selectByTree(currentPage, pageSize, roleName);
    }

    @PostMapping()
    @OperationLogAnnotation(description = "添加角色",detailParam = "RoleVo")
    public HttpResult<?> createRole(@RequestBody RoleVo roleVo) {
        try {
            if (roleService.saveRoleAndPermission(roleVo)) {
                return HttpResult.success();
            }else {
                return HttpResult.failure(ResultMessage.PARAM_ERROR_OR_AUTHORITY_NOT_UNI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.failure(ResultMessage.ADD_FAIL);
    }

    @DeleteMapping("/{id}")
    @OperationLogAnnotation(description = "删除角色",detailParam = "id")
    public HttpResult<?> deleteByIds(@PathVariable("id") Long id) {
        try {
            if (roleService.removeRole(id)) {
                return HttpResult.success();
            }
            return HttpResult.failure(ResultMessage.DELETE_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.failure(ResultMessage.DELETE_ERROR);
    }

    @OperationLogAnnotation(description = "修改角色",detailParam = "RoleVo")
    @PutMapping()
    public HttpResult<?> updateRole(@RequestBody RoleVo roleVo) {
        if (roleVo.getRole().getId() == null) {
            return HttpResult.failure(ResultMessage.PARAM_ERROR);
        }
        if (roleService.updateRoleAndPermission(roleVo)) {
            return HttpResult.success();
        }
        return HttpResult.failure(ResultMessage.UPDATE_ERROR);
    }

}
