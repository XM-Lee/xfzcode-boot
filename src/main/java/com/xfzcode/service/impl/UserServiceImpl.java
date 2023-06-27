package com.xfzcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.constants.ResultCode;
import com.xfzcode.constants.ResultMessage;
import com.xfzcode.mapper.PermissionMapper;
import com.xfzcode.mapper.RoleMapper;
import com.xfzcode.mapper.UserMapper;
import com.xfzcode.mapper.UserRoleMapper;
import com.xfzcode.pojo.Role;
import com.xfzcode.pojo.User;
import com.xfzcode.pojo.UserRole;
import com.xfzcode.service.UserRoleService;
import com.xfzcode.service.UserService;
import com.xfzcode.vo.auth.UserInfoVo;
import com.xfzcode.vo.system.ResultUserVo;
import com.xfzcode.vo.system.UserRoleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User getByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }


    @Override
    @Transactional
    public HttpResult delete(Long id) {
        if (!userRoleMapper.deleteByUserId(id)) {
            return HttpResult.failure(ResultMessage.DELETE_ERROR);
        }
        if (roleMapper.deleteById(id) > 0) {
            return HttpResult.success();
        }
        return HttpResult.failure(ResultMessage.DELETE_ERROR);
    }


    @Override
    @Transactional
    public boolean removeUser(Long id) {
        userRoleMapper.deleteByUserId(id);
        if (userMapper.deleteById(id) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public HttpResult selectList(Integer currentPage, Integer pageSize, String realName) {
        Page<ResultUserVo> transfer = null;
        try {
            ArrayList<ResultUserVo> resultUserVos = new ArrayList<>();
            if (StringUtils.isNotBlank(realName)) {
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("real_name", realName);
                transfer = userTransfer(currentPage, pageSize, resultUserVos, queryWrapper);
            } else {
                transfer = userTransfer(currentPage, pageSize, resultUserVos, null);
            }
            return HttpResult.success(transfer);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return HttpResult.success(transfer);
    }

    @Override
    public boolean enable(Long id) {
        return userMapper.enableUser(id);
    }

    private Page<ResultUserVo> userTransfer(Integer currentPage, Integer pageSize, ArrayList<ResultUserVo> resultUserVos, QueryWrapper<User> queryWrapper) {
        Page<User> userPage = userMapper.selectPage(new Page<>(currentPage, pageSize), queryWrapper);
        List<User> records = userPage.getRecords();
        List<Long> userIds = records.stream().map(User::getId).collect(Collectors.toList());

        //查询对应角色的权限数据
        if (userIds.size() > 0) {
            List<UserRoleVo> result = userRoleService.selectRolesByUserId(userIds);
            //通过roleid对权限数据进行分组
            Map<Long, List<UserRoleVo>> groupBy = result.stream().collect(Collectors.groupingBy(UserRoleVo::getUserId));
            //遍历角色数据，听过角色去匹配权限数据
            for (User user : records) {
                ResultUserVo userVo = new ResultUserVo();
                userVo.setUser(user);
                ArrayList<Role> roles = new ArrayList<>();
                if (groupBy.containsKey(user.getId())) {
                    List<UserRoleVo> userRoleDtos = groupBy.get(user.getId());
                    for (UserRoleVo userRoleDto : userRoleDtos) {
                        if (Objects.equals(userRoleDto.getUserId(), user.getId())) {
                            Role role = new Role();
                            BeanUtils.copyProperties(userRoleDto, role);
                            roles.add(role);
                        }
                    }
                }
                userVo.setRoles(roles);
                resultUserVos.add(userVo);
            }
        }
        Page<ResultUserVo> resultPage = new Page<>();
        BeanUtils.copyProperties(userPage, resultPage);
        resultPage.setRecords(resultUserVos);
        return resultPage;
    }
}
