package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jar.dao.MemberDao;
import com.jar.dao.OrderDao;
import com.jar.dao.PackageDao;

import com.jar.service.ReportService;
import com.jar.utils.DateUtils;
import javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/27
 * @time:14:11
 * @details:
 *
 * Map数据格式：
 *    todayNewMember -> number
 *    totalMember -> number
 *    thisWeekNewMember -> number
 *    thisMonthNewMember -> number
 *    todayOrderNumber -> number
 *    todayVisitsNumber -> number
 *    thisWeekOrderNumber -> number
 *    thisWeekVisitsNumber -> number
 *    thisMonthOrderNumber -> number
 *    thisMonthVisitsNumber -> number
 *    hotPackages -> List<Package>
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PackageDao packageDao;

    @Override
    public Map<String, Object> getBusinessReport() throws Exception {
        //获得当天日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获得本周星期天的日期
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        //获得本月第一天
        String firstDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //获得本月最后一天
        String lastDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getLastDayFromThisMonth());


        //今日新增会员数
        Integer todayNewMember = memberDao.todayNewMember(today);
        //总会员数量
        Integer totalMember = memberDao.totalMember();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.thisWeekNewMember(thisWeekMonday,thisWeekSunday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.thisMonthNewMember(firstDayOfThisMonth,lastDayOfThisMonth);

        //今日预约数
        Integer todayOrderNumber = orderDao.todayOrderNumber(today);
        //今日到访数
        Integer todayVisitsNumber = orderDao.todayVisitsNumber(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.thisWeekOrderNumber(thisWeekMonday,thisWeekSunday);
        //本周到访数
        Integer thisWeekVisitsNumber = orderDao.thisWeekVisitsNumber(thisWeekMonday,today);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.thisMonthOrderNumber(firstDayOfThisMonth,lastDayOfThisMonth);
        //本月到访数
        Integer thisMonthVisitsNumber = orderDao.thisMonthVisitsNumber(firstDayOfThisMonth,lastDayOfThisMonth);

        //热门套餐
        List<Map<String,Object>> hotPackages = packageDao.hotPackages();

        //创建map
        Map<String, Object> map = new HashMap<>();
        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotPackages",hotPackages);

        return map;
    }
}
