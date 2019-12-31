package com.jar.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.pojo.Permission;
import com.jar.pojo.Role;
import com.jar.pojo.User;
import com.jar.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:10:40
 * @details:
 */
@Service
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 根据用户名查询用户对象
//        User user = userService.findByUsername(username);
        User user = userService.findUserRolePermissionByUsername(username);
        if (user == null){
            return null;
        }

        //2. 进行授权
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorityList);

        return userDetails;
    }
}
