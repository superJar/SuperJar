package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jar.dao.CheckGroupDao;
import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.exception.MyException;
import com.jar.pojo.CheckGroup;
import com.jar.pojo.CheckItem;
import com.jar.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/17
 * @time:22:37
 * @details:
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkItemIds
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组里检查项的id
        addItemAndGroup(checkGroup.getId(), checkItemIds);
        //遍历检查项的checkItemIds, 添加检查组与检查项的关系表


    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //自动帮我们添加sql语句limit?,?
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //调用dao层返回Page对象,泛型为CheckGroup
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        //创建一个新的PageResult对象, 构造方法参数里写Page对象的get方法
        PageResult<CheckGroup> pageResult = new PageResult<>(page.getTotal(), page.getResult());

        return pageResult;

    }

    /**
     * 添加检查组和检查项的关系 (此方法和 "新增检查组" 是连在一起的)
     *
     * @param checkGroupId
     * @param checkItemIds
     */
    public void addItemAndGroup(Integer checkGroupId, Integer[] checkItemIds) {
        if (checkItemIds != null) {
            for (Integer checkItemId : checkItemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", checkGroupId);
                map.put("checkitem_id", checkItemId);
                checkGroupDao.addItemAndGroup(map);
            }
        }
    }

    /**
     * 根据id查找检查组
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        //调用dao层根据id查找检查组
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    /**
     * 拿到checkItem的id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> getCheckItemIds(Integer id) {
        //调用dao层用t_checkgroup_checkitem表格找出id
        List<Integer> list = checkGroupDao.getCheckItemIds(id);
        return list;
    }

    /**
     * 修改检查组
     *
     * @param checkGroup
     * @return
     */
    @Override
    public boolean update(CheckGroup checkGroup) {


        Integer row = checkGroupDao.update(checkGroup);
        return row > 0;
    }

    @Override
    public boolean delete(Integer id) throws MyException {
        //要先判断该CheckGroup是否被Package关联了, 如果关联了是不能被删除的
        Integer countFromPackage = checkGroupDao.getCountFromPackage(id);
        Integer countFromItem = checkGroupDao.getCountFromItem(id);

        if (countFromPackage > 0 || countFromItem > 0){
            //count大于0说明被关联了, 要报错并返回给客户端
            throw new MyException("该检查组已被套餐关联, 不能被删除, 如需删除请联系管理员");
        }

        Integer row = checkGroupDao.deleteById(id);

        return row > 0;
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {

        return checkGroupDao.findAll();
    }
}
