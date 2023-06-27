package com.xfzcode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xfzcode.annotation.OperationLogAnnotation;
import com.xfzcode.constants.ApiVersion;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.constants.ResultMessage;
import com.xfzcode.pojo.User;
import com.xfzcode.service.impl.UserServiceImpl;
import io.swagger.annotations.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiVersion.V1_USER)
//@RolesAllowed(Roles.USER_ADMIN)
public class UserController {


    @Autowired
    private UserServiceImpl userService;


    @DeleteMapping(value = "/{id}")
    @OperationLogAnnotation(description = "删除用户", entityParam = "id")
    public HttpResult delete( @PathVariable("id") Long id) {
        try {
            if (userService.removeUser(id)) {
                return HttpResult.success();
            }
            return HttpResult.failure(ResultMessage.DELETE_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.failure(ResultMessage.DELETE_ERROR);
    }

    @GetMapping(value = "enable/{id}")
    @OperationLogAnnotation(description = "禁用|启用 用户", entityParam = "id")
    public HttpResult enable( @PathVariable Long id) {
        try {
            if (userService.enable(id)) {
                return HttpResult.success();
            }
            return HttpResult.failure(ResultMessage.OPERATION_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.failure(ResultMessage.OPERATION_SUCCESS);
    }


    @GetMapping(value = "/getUserList")
    public HttpResult<?> getUserList(@RequestParam(defaultValue = "1",name = "currentPage") Integer currentPage,
                                          @RequestParam(defaultValue = "5",name = "pageSize") Integer pageSize,
                                          @RequestParam(required = false,name = "realName") String realName) {
        return HttpResult.success(userService.selectList(currentPage, pageSize, realName));
    }
}
