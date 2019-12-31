package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.constant.RedisMessageConstant;
import com.jar.entity.Result;
import com.jar.pojo.Member;
import com.jar.service.MemberService;
import com.jar.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/24
 * @time:10:29
 * @details:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/check")
    public Result loginCheck(@RequestBody Map map, HttpServletResponse response){
        //获取前端传过来的电话号码
        String telephone = (String) map.get("telephone");
        //获取前端传来的验证码
        String validateCode = (String) map.get("validateCode");
        //获取Redis里的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //判断: 比较俩验证码
        if(codeInRedis == null || !validateCode.equalsIgnoreCase(codeInRedis)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //判断: 是否为会员
        Member member = memberService.findByTelephone(telephone);

        if(member == null){
            //如果不是会员, 自动帮他注册一个会员
            member = new Member();
            member.setTelephone(telephone);
            member.setRegTime(DateUtils.getToday());

            memberService.add(member);
        }

        //创建cookie, 将会员的电话号码存入到cookie
        Cookie cookie = new Cookie("login_member_cookie",telephone);
        cookie.setMaxAge(60*60*24*30); // 设置最大有效时间
        cookie.setPath("/");// 设置网站的根路径, 当访问到这个网站就会携带Cookie
        response.addCookie(cookie); //将cookie返回到浏览器客户端

        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }

}
