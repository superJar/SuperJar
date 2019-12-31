package com.jar.dao;

import com.github.pagehelper.Page;
import com.jar.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/17
 * @time:22:40
 * @details:
 */
public interface CheckGroupDao {

    /**
     * 添加检查组
     * @param checkGroup
     * @return
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系表
     * @param map
     * @return
     */
    void addItemAndGroup(Map<String,Integer> map);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 根据id查找检查组
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 用t_checkgroup_checkitem表格找出checkItem的id
     * @param id
     * @return
     */
    List<Integer> getCheckItemIds(Integer id);

    /**
     * 修改检查组
     * @param checkGroup
     * @return
     */
    Integer update(CheckGroup checkGroup);

    /**
     * 查看该检查组是否被套餐关联 (跟修改检查组一起用)
     * @param id
     * @return
     */
    Integer getCountFromPackage(Integer id);

    /**
     * 删除检查组
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 检查是否被检查项关联
     * @param id
     * @return
     */
    Integer getCountFromItem(Integer id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
