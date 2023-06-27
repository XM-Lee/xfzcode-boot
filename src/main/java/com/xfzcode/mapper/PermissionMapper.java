package com.xfzcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfzcode.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:26
 * @Description:
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findUserPermissionDetailById(Long userId);

    List<String> findUserPermissionById(Long userId);
}
