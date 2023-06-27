package com.xfzcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfzcode.pojo.RolePermission;
import com.xfzcode.vo.system.PermissionsRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:08
 * @Description:
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    boolean deleteByRoleId(@Param("id") Long id);

    List<PermissionsRoleVo> selectPermissionsByRoleId(@Param("roleIds") List<Long> roleIds);
}
