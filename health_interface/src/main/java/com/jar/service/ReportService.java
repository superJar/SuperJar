package com.jar.service;

import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/27
 * @time:14:11
 * @details:
 */
public interface ReportService {
    /**
     * 获得运营统计数据
     * Map数据格式：
     *      todayNewMember -> number
     *      totalMember -> number
     *      thisWeekNewMember -> number
     *      thisMonthNewMember -> number
     *      todayOrderNumber -> number
     *      todayVisitsNumber -> number
     *      thisWeekOrderNumber -> number
     *      thisWeekVisitsNumber -> number
     *      thisMonthOrderNumber -> number
     *      thisMonthVisitsNumber -> number
     *      hotPackages -> List<Package>
     */
    Map<String,Object> getBusinessReport() throws Exception;
}
