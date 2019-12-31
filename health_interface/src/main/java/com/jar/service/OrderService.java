package com.jar.service;

import com.jar.entity.Result;

import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/23
 * @time:11:44
 * @details:
 */
public interface OrderService {
    /**
     * 预约设置 (调用了MemberDao, OrderDao, OrderSettingDao)
     * @param map
     * @return
     */
    Result submit(Map map) throws Exception;

    /**
     *根据Order.id找出package.name, member.name, order.orderDate, order.orderType
     * @param id
     * @return
     */
    Map<String,String> findById4Detail(Integer id);
}
