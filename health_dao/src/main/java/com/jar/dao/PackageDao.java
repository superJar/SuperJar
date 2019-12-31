package com.jar.dao;

import com.github.pagehelper.Page;
import com.jar.pojo.Package;
import com.jar.pojo.PackageReport;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/19
 * @time:10:47
 * @details:
 */
public interface PackageDao {
    /**
     * 添加套餐
     * @param pk
     * @return
     */
    Integer add(Package pk);

    /**
     * 添加套餐与检查组关系
     * @param map
     * @return
     */
    Integer addPackageAndGroup(Map<String,Integer> map);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Package> findPage(String queryString);

    /**
     * 查找所有套餐
     * @return
     */
    List<Package> findAll();

    /**
     * 查找出对应套餐下的所有检查组和检查项
     * @param id
     * @return
     */
    Package findDetailById(Integer id);

    /**
     * 按照id查找套餐
     * @param id
     * @return
     */
    Package findById(Integer id);

    /**
     * 方法1: 找出所有套餐名以及对应的预约数量
     * @return
     */
    List<PackageReport> findPackageNameAndCount();

    /**
     * 方法2: 找出所有套餐名以及对应的预约数量
     * @return
     */
    List<Map<String,Object>> findPackageNameAndCount2();

    /**
     * 热门套餐
     * @return
     */
    List<Map<String,Object>> hotPackages();
}
