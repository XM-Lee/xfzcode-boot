package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfzcode.mapper.UserRoleMapper;
import com.xfzcode.pojo.UserRole;
import com.xfzcode.service.UserRoleService;
import com.xfzcode.vo.system.UserRoleVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:26
 * @Description:
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRoleVo> selectRolesByUserId(List<Long> userIds) {
        return this.baseMapper.selectRolesByUserId(userIds);
    }
}
