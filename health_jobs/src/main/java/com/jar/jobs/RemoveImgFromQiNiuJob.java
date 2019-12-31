package com.jar.jobs;

import com.jar.constant.RedisConstant;
import com.jar.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author:superJar
 * @date:2019/12/20
 * @time:9:28
 * @details:
 */
@Component
public class RemoveImgFromQiNiuJob {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 实现: 定时清理图片
     */
    public void doJob(){
        System.out.println("Job启动...");

        //获取Jedis
        Jedis jedis = jedisPool.getResource();
        //获取Jedis中两个集合的差集, 第一个集合为所有上传过的图片, 第二个集合为上传并提交的图片
        Set<String> need2BeRemoved = jedis.sdiff(RedisConstant.PACKAGE_PIC_RESOURCES, RedisConstant.PACKAGE_PIC_DB_RESOURCES);

        if (need2BeRemoved != null) {
            //将set集合转成数组
            String[] img = need2BeRemoved.toArray(new String[]{});
            //调用七牛工具包删除
            QiNiuUtil.removeFiles(img);
            //移除Redis的缓存
            jedis.del(RedisConstant.PACKAGE_PIC_RESOURCES,RedisConstant.PACKAGE_PIC_DB_RESOURCES);
        }
        System.out.println("Job执行完毕.");
    }
}
