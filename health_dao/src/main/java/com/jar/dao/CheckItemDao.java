package com.jar.dao;

import com.github.pagehelper.Page;
import com.jar.pojo.CheckItem;

import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/16
 * @time:11:30
 * @details:
 */
public interface CheckItemDao {
    /**
     * 新增检查项
     * @param checkItem
     */
    void addItem(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 查找出指定检查项
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    Integer edit(CheckItem checkItem);

    /**
     * 查看检查项是否被引用
     * @param id
     * @return
     */
    Integer countByCheckItemId(Integer id);

    /**
     * 删除检查项
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
