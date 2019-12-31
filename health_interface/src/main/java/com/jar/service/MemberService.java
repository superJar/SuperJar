package com.jar.service;

import com.jar.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/24
 * @time:10:52
 * @details:
 */
public interface MemberService {
    /**
     * 根据电话查找会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加一个新的会员
     * @param member
     */
    void add(Member member);

    /**
     * 截至指定日期内, 会员数量总和
     * @param list
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> list);

    /**
     * 根据会员性别分类, 获取会员数量
     * @return
     */
    List<Map<String,Object>> getMemberCountBySex();

    /**
     * 根据会员年龄段分类, 获取会员数量
     * @return
     */
    List<Map<String,Object>> getMemberCountByAge() throws Exception;
}
