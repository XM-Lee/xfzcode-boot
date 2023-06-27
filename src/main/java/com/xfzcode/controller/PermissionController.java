package com.xfzcode.controller;

import com.xfzcode.annotation.OperationLogAnnotation;
import com.xfzcode.constants.ApiVersion;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 11:29
 * @Description:
 */
@RestController
@RequestMapping(ApiVersion.V1_PERMISSION)
@RequiredArgsConstructor
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/listAll")
    @OperationLogAnnotation(description = "查询全部权限数据")
    public HttpResult<?> getRoleListAll() {
        return HttpResult.success(permissionService.listAll());
    }

}
