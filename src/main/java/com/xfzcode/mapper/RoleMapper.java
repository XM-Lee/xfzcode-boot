package com.xfzcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfzcode.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findUserRoleDetailById(Long id);
}
