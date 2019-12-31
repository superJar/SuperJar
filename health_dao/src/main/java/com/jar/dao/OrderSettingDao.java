package com.jar.dao;

import com.jar.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/20
 * @time:21:53
 * @details:
 */
public interface OrderSettingDao {
    /**
     * 通过预约日期找出OrderSetting对象
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 覆盖旧的OrderSetting对象
     * @param orderSetting
     */
    void cover(OrderSetting orderSetting);

    /**
     * 添加新的OrderSetting
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 按照指定日期范围内查询所有的OrderSetting对象
     * @param beginDate
     * @param endDate
     * @return
     */
    List<OrderSetting> getOrderSettingBetweenDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 设置新的预约人数
     * @param orderSetting
     */
    void resetNumber(OrderSetting orderSetting);

    /**
     * 新增 1 个预约
     * @param orderSetting
     */
    void editReservations(OrderSetting orderSetting);
}
