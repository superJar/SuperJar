package com.jar.service;

import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.pojo.Package;
import com.jar.pojo.PackageReport;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/19
 * @time:10:45
 * @details:
 */


public interface PackageService {
    /**
     * 添加套餐
     * @param pk
     * @return
     */
    boolean add(Package pk, Integer[] checkGroupIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Package> findPage(QueryPageBean queryPageBean);

    /**
     * 查询所有套餐
     * @return
     */
    List<Package> findAll();

    /**
     * 查询指定套餐下的所有检查组和检查项
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
}
