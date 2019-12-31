package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jar.dao.PermissionDao;
import com.jar.dao.RoleDao;
import com.jar.dao.UserDao;
import com.jar.pojo.Permission;
import com.jar.pojo.Role;
import com.jar.pojo.User;
import com.jar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:10:48
 * @details:
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        //首先根据用户名获取用户对象
        User user = userDao.findByUsername(username);

        if(user != null){
            //再根据Uid找到该用户下的Role
            Set<Role> roles = roleDao.findByUid(user.getId());
            if(roles != null && roles.size() > 0){
                //遍历Role对象
                for (Role role : roles) {
                    //根据每个Role对象的id找到对应的Permission
                    Integer roleId = role.getId();
                    Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                    //将找到的Permission对象塞入到每个Role对象里
                    role.setPermissions(permissions);
                }
            }
            //最后将整个Roles集合塞入到User对象里
            user.setRoles(roles);
        }
        return user;


    }

    @Override
    public User findUserRolePermissionByUsername(String username) {
        return userDao.findUserRolePermissionByUsername(username);
    }
}
