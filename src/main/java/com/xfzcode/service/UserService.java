package com.xfzcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xfzcode.constants.HttpResult;
import com.xfzcode.pojo.User;

/**
 * @Author: XMLee
 * @Date: 2023/4/12 11:33
 * @Description:
 */
public interface UserService extends IService<User> {


    HttpResult delete(Long id);


    boolean removeUser(Long id);

    HttpResult selectList(Integer currentPage, Integer pageSize, String realName);

    boolean enable(Long id);
}
