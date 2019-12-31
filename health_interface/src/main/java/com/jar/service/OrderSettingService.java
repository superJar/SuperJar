package com.jar.service;

import com.jar.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/20
 * @time:16:26
 * @details:
 */
public interface OrderSettingService {
    /**
     * 添加预约设定到数据库
     * @param list
     */
    void add(List<OrderSetting> list);

    /**
     * 在页面显示预约信息
     * @param dateParam
     * @return
     */
    List<Map<String,Integer>> getAppInfo(String dateParam);

    /**
     * 设置新的预约人数
     * @param orderSetting
     */
    void resetNumber(OrderSetting orderSetting);
}
