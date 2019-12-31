package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jar.dao.OrderSettingDao;
import com.jar.pojo.OrderSetting;
import com.jar.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author:superJar
 * @date:2019/12/20
 * @time:16:25
 * @details:
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        if (list != null) {
            for (OrderSetting orderSetting : list) {
                //判断 是否已存在相同的OrderSetting
                OrderSetting osInDb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                if (osInDb != null && osInDb.getNumber() >= orderSetting.getNumber()) {
                    //若已存在, 就覆盖
                    orderSettingDao.cover(orderSetting);
                }
                //若不存在, 就添加
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getAppInfo(String dateParam) {

        //拼接开始日期
        String beginDate = dateParam + "-01";
        //拼接结束日期
        String endDate = dateParam + "-31";

        //调用Dao层实现指定日期内List<OrderSetting>的查询
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingBetweenDate(beginDate, endDate);

        //转成List<Map<String,Integer>>, 因为前段this.leftobj格式为: [{date: 1, number: 120, reservations: 1}]
        List<Map<String,Integer>> mapList = new ArrayList<>();


        if (orderSettingList != null) {

            for (OrderSetting orderSetting : orderSettingList) {
                Map<String,Integer> map = new HashMap<>();
//                //这里要将日期变成int类型, 且只显示day数字
//                String dateStr = orderSetting.getOrderDate().toString();
//                String dayStr = dateStr.substring(dateStr.lastIndexOf("-") + 1);
//                int day = Integer.parseInt(dayStr) * 1;

                //将获取的对象信息添加到map里, 最后将map添加到mapList里
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number",orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());

                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 设置新的预约人数
     * @param orderSetting
     */
    @Override
    public void resetNumber(OrderSetting orderSetting) {

        orderSettingDao.resetNumber(orderSetting);

    }
}
