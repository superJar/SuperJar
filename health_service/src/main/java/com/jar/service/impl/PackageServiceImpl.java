package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jar.dao.PackageDao;
import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.pojo.Package;
import com.jar.pojo.PackageReport;
import com.jar.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/19
 * @time:10:45
 * @details:
 */
@Service(interfaceClass = PackageService.class)
@Transactional
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    /**
     * 添加套餐
     *
     * @param pk
     * @return
     */
    @Override
    public boolean add(Package pk, Integer[] checkGroupIds) {

        //先添加套餐

        Integer row1 = packageDao.add(pk);

        //如果有选上检查组, 则关联检查组
        if (!StringUtils.isEmpty(checkGroupIds)) {
            for (Integer checkGroupId : checkGroupIds) {
                Map<String, Integer> map = new HashMap<>();

                map.put("package_id", pk.getId());
                map.put("checkgroup_id", checkGroupId);
                packageDao.addPackageAndGroup(map);
            }
            return true;
        }

        return row1 > 0;
    }

    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        //先判断是否有查询条件

        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        //调用PageHelper, 添加limit ?, ?语句
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Package> page = packageDao.findPage(queryPageBean.getQueryString());

        PageResult<Package> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public List<Package> findAll() {

        return packageDao.findAll();
    }

    @Override
    public Package findDetailById(Integer id) {
        Package pkg = packageDao.findDetailById(id);
        return pkg;
    }

    @Override
    public Package findById(Integer id) {
        Package pkg = packageDao.findById(id);
        return pkg;
    }

    /**
     * 方法1: 找出所有套餐名以及对应的预约数量
     * @return
     */
    @Override
    public List<PackageReport> findPackageNameAndCount() {
        return packageDao.findPackageNameAndCount();
    }

    /**
     * 方法2: 找出所有套餐名以及对应的预约数量
     * @return
     */
    @Override
    public List<Map<String, Object>> findPackageNameAndCount2() {
        return packageDao.findPackageNameAndCount2();
    }
}
