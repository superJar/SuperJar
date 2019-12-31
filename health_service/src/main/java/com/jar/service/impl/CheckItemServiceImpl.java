package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jar.dao.CheckItemDao;
import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.exception.MyException;
import com.jar.pojo.CheckItem;
import com.jar.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/16
 * @time:11:25
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired(required = true)
    private CheckItemDao checkItemDao;

    /**
     *  新增检查项
     * @param checkItem
     */
    @Override
    public void addItem(CheckItem checkItem) {
        //调用dao
        checkItemDao.addItem(checkItem);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //先判断是否有查询条件, 可以用到StringUtils的isEmpty方法
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //将queryPageBean里的查询条件加上 '%'
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //紧接着查询语句会被自动分页, 相当于加了limit?, ?
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());

        //getResult表示得到搜素出来的结果集
        /**
         * 疑问: 为什么不返回page对象呢?
         * 点进去page源码, 看到page的成员变量有很多int, 由于int没有实现序列化, 因此不够安全
         */
        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public CheckItem findById(Integer id) {

        return checkItemDao.findById(id);
    }

    @Override
    public boolean edit(CheckItem checkItem) {
        Integer row = checkItemDao.edit(checkItem);
        return row > 0;
    }

    @Override
    public boolean deleteById(Integer id) throws MyException{
        //检查该检查项是否已被引用
        Integer count = checkItemDao.countByCheckItemId(id);
        if(count > 0){
            //报错, 说明该数据已被引用, 不能被删除
            throw new MyException("该检查项已经被引用, 不能删除");
        }

        Integer row = checkItemDao.deleteById(id);

        return row > 0;
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = checkItemDao.findAll();

        return checkItems;
    }
}
