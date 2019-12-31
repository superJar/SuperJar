package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.entity.Result;
import com.jar.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:23:34
 * @details:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @PostMapping("/getLoginUsername")
    public Result getLoginUsername(){
        //security的配置信息
        SecurityContext context = SecurityContextHolder.getContext();
        //获取认证信息
        Authentication authentication = context.getAuthentication();
        //获取登录用户
        User principal = (User) authentication.getPrincipal();
        return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,principal.getUsername());
    }
}
