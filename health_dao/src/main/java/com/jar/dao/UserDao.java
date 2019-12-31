package com.jar.dao;

import com.jar.pojo.User;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:11:05
 * @details:
 */
public interface UserDao {
    /**
     * 根据用户名查找用户对象
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findUserRolePermissionByUsername(String username);
}
