package com.jar.dao;

import com.jar.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/23
 * @time:19:39
 * @details:
 */
public interface OrderDao {
    /**
     * 根据order的信息找出是否存在一个以上该order
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 添加一个新的Order
     * @param order
     */
    void add(Order order);

    /**
     * 根据Order.id找出package.name, member.name, order.orderDate, order.orderType
     * @param id
     * @return
     */
    Map findById4Detail(Integer id);

    /**
     * 今日预约数
     * @param today
     * @return
     */
    Integer todayOrderNumber(String today);

    /**
     * 今日到访数
     * @param today
     * @return
     */
    Integer todayVisitsNumber(String today);

    /**
     * 本周预约数
     * @param thisWeekMonday
     * @param thisWeekSunday
     * @return
     */
    Integer thisWeekOrderNumber(@Param("startDate") String startDate, @Param("endDate")String endDate);

    /**
     * 本周到访数
     * @param thisWeekMonday
     * @param today
     * @return
     */
    Integer thisWeekVisitsNumber(@Param("startDate") String startDate, @Param("endDate")String endDate);

    /**
     * 本月预约数
     * @param firstDayOfThisMonth
     * @param lastDayOfThisMonth
     * @return
     */
    Integer thisMonthOrderNumber(@Param("startDate") String startDate, @Param("endDate")String endDate);

    /**
     * 本月到访数
     * @param firstDayOfThisMonth
     * @param lastDayOfThisMonth
     * @return
     */
    Integer thisMonthVisitsNumber(@Param("startDate") String startDate, @Param("endDate")String endDate);
}
