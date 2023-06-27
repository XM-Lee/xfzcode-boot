package com.xfzcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfzcode.pojo.UserRole;
import com.xfzcode.vo.system.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/4/24 10:27
 * @Description:
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    boolean deleteByRoleId(@Param("id") Long id);

    boolean deleteByUserId(@Param("id")Long id);

    List<UserRoleVo> selectRolesByUserId(@Param("userIds") List<Long> userIds);
}
