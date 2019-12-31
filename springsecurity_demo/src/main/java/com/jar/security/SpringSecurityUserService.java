package com.jar.security;


import com.jar.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:9:09
 * @details:
 */
@Service
public class SpringSecurityUserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名从数据库查询, 假设从数据库查
        User user = findByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        //角色名要与security.xml配置文件中的配置拦截路径的角色名要一样
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(sga);


        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(),authorities);
        return userDetails;
    }

    /**
     * 模拟数据库查询
     *
     * @param username
     * @return
     */
    private User findByUsername(String username) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        return user;
    }
}
