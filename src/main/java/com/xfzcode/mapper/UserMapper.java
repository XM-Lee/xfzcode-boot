package com.xfzcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xfzcode.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:37
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("UPDATE sys_users SET enabled = !enabled WHERE id = #{id} ")
    boolean enableUser(@Param("id") Long id);
}
