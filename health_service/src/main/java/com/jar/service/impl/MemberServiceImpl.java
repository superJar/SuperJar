package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jar.dao.MemberDao;
import com.jar.pojo.Member;
import com.jar.service.MemberService;
import com.jar.utils.DateUtils;
import com.jar.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author:superJar
 * @date:2019/12/24
 * @time:10:52
 * @details:
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;


    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        //帮会员的密码加密
        if(member.getPassword() != null){
            String encryptPassword = MD5Utils.md5(member.getPassword());
            member.setPassword(encryptPassword);
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> lists) {
        List<Integer> integerList = new ArrayList<>();
        for (String list : lists) {
            Integer count = memberDao.findMemberCountByMonth(list);
            integerList.add(count);
        }

        return integerList;
    }

    /**
     * 根据会员性别分类, 获取会员数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getMemberCountBySex() {
        return memberDao.getMemberCountBySex();
    }

    /**
     * 根据会员年龄段分类, 获取会员数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getMemberCountByAge() throws Exception {
        String eighteenYearsBefore = DateUtils.parseDate2String(DateUtils.backwardCertainYears(-18));
        String thirtyYearsBefore = DateUtils.parseDate2String(DateUtils.backwardCertainYears(-30));
        String fortyFiveYearsBefore = DateUtils.parseDate2String(DateUtils.backwardCertainYears(-45));
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        List<Map<String, Object>> list = memberDao.getMemberCountByAge(
                today,
                eighteenYearsBefore,
                thirtyYearsBefore,
                fortyFiveYearsBefore);
        //创建一个新的mapList
        List<Map<String,Object>> mapList = new ArrayList<>();

        for (Map<String, Object> map : list) {
            Map<String,Object> map2 = new HashMap<>();

            String ageCategory = (String) map.get("ageCategory");
            Long value = (Long) map.get("value");

            map2.put("name",ageCategory);
            map2.put("value",value);

            mapList.add(map2);

        }


        return mapList;
    }
}
