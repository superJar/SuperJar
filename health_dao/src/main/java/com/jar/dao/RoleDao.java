package com.jar.dao;

import com.jar.pojo.Role;

import java.util.Set;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:11:05
 * @details:
 */
public interface RoleDao {
    /**
     * 根据User的id找出对应的所有Role对象
     * @param id
     * @return
     */
    Set<Role> findByUid(Integer id);
}
