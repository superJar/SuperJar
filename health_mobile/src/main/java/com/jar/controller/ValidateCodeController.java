package com.jar.controller;

import com.aliyuncs.exceptions.ClientException;
import com.jar.constant.MessageConstant;
import com.jar.constant.RedisMessageConstant;
import com.jar.entity.Result;
import com.jar.utils.SMSUtils;
import com.jar.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


/**
 * @author:superJar
 * @date:2019/12/23
 * @time:1:09
 * @details:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(@RequestParam String telephone){

        //生成一个4位数的随机数字
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        //发送短信,有可能发送不了, 所以要try/catch
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //如果发送成功, 将验证码保存至redis
        //setex方法: 格式为: setex(key, 10, value)
        //其中10为10秒, 表示key中的value存活的时间, 10秒之后, 这个value会自动清除
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @PostMapping("/send4Login")
    public Result send4Login(@RequestParam String telephone){
        //先用工具类生成一个4位数的随机数
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //判断: Redis里是否存在与该随机数相同的数字
        if(code.toString() != jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_LOGIN)) {
            try {
                //发送验证码到手机用户上
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
            } catch (ClientException e) {
                e.printStackTrace();

                return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        //发送成功后, 将验证码保存到Redis里
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,60*5,code.toString());

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
