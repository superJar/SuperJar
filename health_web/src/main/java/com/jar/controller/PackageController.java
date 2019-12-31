package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.constant.RedisConstant;
import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.entity.Result;

import com.jar.pojo.Package;
import com.jar.service.PackageService;
import com.jar.utils.QiNiuUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author:superJar
 * @date:2019/12/19
 * @time:10:44
 * @details:
 */
@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传 (使用七牛工具类)
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        //先获取上传文件的名称
        String originalFilename = imgFile.getOriginalFilename();
        //获取到文件名称最后一个 "." 的索引
        int index = originalFilename.lastIndexOf(".");
        //生成后缀
        String suffix = originalFilename.substring(index);
        //通过UUID生成一个随机码并拼接成新的文件名称
        String newFileName = UUID.randomUUID().toString() + suffix;
        //使用七牛上传
//        QiNiuUtil.uploadFile(imgFile.toString(),newFileName);
        try {
            QiNiuUtil.uploadViaByte(imgFile.getBytes(),newFileName);

            String imageUrl = QiNiuUtil.DOMAIN + newFileName;

            System.out.println("文件名称 ==="+imageUrl+"===");
            //将图片名称存入到Redis里
            jedisPool.getResource().sadd(RedisConstant.PACKAGE_PIC_RESOURCES,newFileName);

            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,imageUrl);
        } catch (IOException e) {
            e.printStackTrace();

        }
//        //图片回显
//        Map<String, String> map = new HashMap<>();
//        //图片的域名  q2qkubjje.bkt.clouddn.com
//        map.put("domain",QiNiuUtil.DOMAIN);
//        //图片的名称, 存在七牛, 必须唯一  A12asd121.jpg
//        map.put("imgFile",newFileName);


        return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);

    }

    @PostMapping("/add")
    public Result add(@RequestBody Package pk, Integer[] checkGroupIds){

        boolean success = packageService.add(pk, checkGroupIds);

        if(!success){
            return new Result(success,MessageConstant.ADD_SETMEAL_FAIL);
        }

        //将图片名称存入到Redis中
        jedisPool.getResource().sadd(RedisConstant.PACKAGE_PIC_DB_RESOURCES,pk.getImg());

        return new Result(success,MessageConstant.ADD_SETMEAL_SUCCESS);

    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        //调用业务层方法, 返回一个泛型为Package的PageResult
        PageResult<Package> data = packageService.findPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,data);

    }


}
