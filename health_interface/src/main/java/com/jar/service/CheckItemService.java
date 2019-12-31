package com.jar.service;

import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.exception.MyException;
import com.jar.pojo.CheckItem;

import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/16
 * @time:11:22
 */
public interface CheckItemService {
    /**
     * 增加检查项
     * @param checkItem
     */
    void addItem(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 查找出指定检查项
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    boolean edit(CheckItem checkItem);

    /**
     * 删除检查项
     * @param id
     * @return
     */
    boolean deleteById(Integer id) throws MyException;

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();

}
