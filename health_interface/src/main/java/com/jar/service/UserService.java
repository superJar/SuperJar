package com.jar.service;

import com.jar.pojo.User;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:10:48
 * @details:
 */
public interface UserService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findUserRolePermissionByUsername(String username);
}
