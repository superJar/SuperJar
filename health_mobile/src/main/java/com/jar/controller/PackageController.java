package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.entity.Result;
import com.jar.pojo.CheckGroup;
import com.jar.pojo.Package;
import com.jar.service.PackageService;
import com.jar.utils.QiNiuUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/21
 * @time:23:08
 * @details:
 */
@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;

    /**
     * 获取所有package
     * @return
     */
    @PostMapping("/getPackage")
    public Result getPackage(){
        List<Package> packages = packageService.findAll();

        for (Package pkg : packages) {
            pkg.setImg(QiNiuUtil.DOMAIN+pkg.getImg());
        }
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,packages);
    }

    /**
     * 根据套餐id找出该套餐的信息及与其关联的所有检查组和检查项信息
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(@RequestParam Integer id){

        Package pkg = packageService.findDetailById(id);

        pkg.setImg(QiNiuUtil.DOMAIN+pkg.getImg());

        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pkg);
    }

    @PostMapping("/findById")
    public Result findById(@RequestParam Integer id){
        Package pkg = packageService.findById(id);

        pkg.setImg(QiNiuUtil.DOMAIN+pkg.getImg());

        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pkg);
    }
}
