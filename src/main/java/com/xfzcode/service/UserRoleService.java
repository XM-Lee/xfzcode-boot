package com.xfzcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfzcode.pojo.UserRole;
import com.xfzcode.vo.system.UserRoleVo;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:26
 * @Description:
 */
public interface UserRoleService  extends IService<UserRole> {

    List<UserRoleVo> selectRolesByUserId(List<Long> userIds);
}
