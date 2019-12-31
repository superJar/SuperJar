package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.jar.constant.MessageConstant;
import com.jar.constant.RedisMessageConstant;
import com.jar.entity.Result;
import com.jar.pojo.Order;
import com.jar.service.OrderService;
import com.jar.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/23
 * @time:11:14
 * @details:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map map) {
        //1. 要比较前端传来的验证码和Redis里存储的验证码是否一致
        //首先先拿到前端那边传过来的Telephone
        String telephone = (String) map.get("telephone");

        //同时拿到提交的验证码
        String validateCode = (String) map.get("validateCode");

        //同时也要拿到Redis里存储的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        //判断: 验证码是否为空, 比较二者是否一样
        if (validateCode == null || !validateCode.equals(codeInRedis)) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //2. 调用业务层, 执行预约的一系列操作, 返回一个Result对象
        //先在map里放一个 "orderType"
        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.submit(map);
        } catch (Exception e) {
            e.printStackTrace();
            //为了防止不能实现, 要try/catch
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //如果返回值为true
        if (result.isFlag()) {
            try {
                //预约成功后, 要发送一个预约成功的短信返回给手机号持有人
                String orderDate = (String) map.get("orderDate");
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }
        return result;
    }

    @PostMapping("/findById")
    public Result findById(@RequestParam Integer id){

        Map<String,String> map = orderService.findById4Detail(id);

        return new Result(true,MessageConstant.ORDER_SUCCESS,map);

    }
}
