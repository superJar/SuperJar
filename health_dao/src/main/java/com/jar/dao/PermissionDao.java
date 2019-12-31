package com.jar.dao;

import com.jar.pojo.Permission;

import java.util.Set;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:11:05
 * @details:
 */
public interface PermissionDao {
    /**
     * 根据Role对象的id找出对应的所有Permission
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);
}
