package com.jar.service;

import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.exception.MyException;
import com.jar.pojo.CheckGroup;
import com.jar.pojo.CheckItem;

import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/17
 * @time:22:35
 * @details:
 */
public interface CheckGroupService {
    /**
     * 添加检查组, 以及插入检查项与检查组的关系表
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查找检查组
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 用t_checkgroup_checkitem表格拿到checkItem的id
     *
     * @param id
     * @return
     */
    List<Integer> getCheckItemIds(Integer id);

    /**
     * 修改checkGroup
     *
     * @param checkGroup
     * @return
     */
    boolean update(CheckGroup checkGroup);

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    boolean delete(Integer id) throws MyException;

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
