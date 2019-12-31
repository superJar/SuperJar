package com.jar.dao;

import com.jar.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/23
 * @time:19:38
 * @details:
 */
public interface MemberDao {
    /**
     *根据Telephone找出对应的会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加新的会员
     * @param newMember
     */
    void add(Member newMember);

    /**
     * 截至指定日期内的会员总和
     * @param
     * @return
     */
    Integer findMemberCountByMonth(String date);

    /**
     * 今日新增会员
     * @param today
     * @return
     */
    Integer todayNewMember(String today);

    /**
     * 总会员数量
     * @return
     */
    Integer totalMember();

    /**
     * 本周新增会员
     * @param thisWeekMonday
     * @param thisWeekSunday
     * @return
     */
    Integer thisWeekNewMember(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 本月新增会员
     * @param firstDayOfThisMonth
     * @param lastDayOfThisMonth
     * @return
     */
    Integer thisMonthNewMember(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 根据会员性别分类, 获取会员数量
     * @return
     */
    List<Map<String,Object>> getMemberCountBySex();

    /**
     * 根据会员年龄段分类, 获取会员数量
     * @return
     */
    List<Map<String,Object>> getMemberCountByAge(@Param("today") String date1, @Param("eighteenYearsBefore") String date2, @Param("thirtyYearsBefore") String date3, @Param("fortyFiveYearsBefore") String date4);
}
