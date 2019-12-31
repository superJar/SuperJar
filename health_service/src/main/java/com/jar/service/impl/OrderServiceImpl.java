package com.jar.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jar.constant.MessageConstant;
import com.jar.dao.MemberDao;
import com.jar.dao.OrderDao;
import com.jar.dao.OrderSettingDao;
import com.jar.entity.Result;
import com.jar.pojo.Member;
import com.jar.pojo.Order;
import com.jar.pojo.OrderSetting;
import com.jar.service.OrderService;
import com.jar.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/23
 * @time:11:44
 * @details:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Result submit(Map map) throws Exception{
        //1. 先判断前端传过来的日期能不能预约
        //先从map里拿到 "orderDate"
        String orderDate = (String) map.get("orderDate");
        //1.1 因为 orderDate是字符串类型, 需要转成Date类型
        Date date = DateUtils.parseString2Date(orderDate);
        //1.2 调用OrderSetting的dao, 查出OrderSetting表里是否为空值
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);

        if(orderSetting == null){
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2. 判断当前日期是否被预约满了
        int numberOfAvailable = orderSetting.getNumber();
        int numberOfReservations = orderSetting.getReservations();
        if(numberOfReservations >= numberOfAvailable){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3. 判断当前用户是否会员
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        //3.1 如果是会员, 避免重复预约
        if (member != null) {
            //3.2 先将map里的套餐id转成int类型
            int packageId = Integer.parseInt((String) map.get("packageId"));

            Order order = new Order(member.getId(),date,null,null,packageId);
            //3.3 调用Order的dao层, 看能不能从表格里找到有预约的Order对象
            List<Order> orderList = orderDao.findByCondition(order);

            if(orderList != null && orderList.size() > 0){
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else{
            //3.4 如果不是会员, 那么将注册为会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(date);
            member.setTelephone(telephone);

            //3.5 添加新的会员
            memberDao.add(member);

        }

        //4. 进行预约, 在OrderSetting的reservation上+1
//        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservations(orderSetting);

        //5. 将预约信息放入Order对象里, 并添加, 并返回到Controller
        Order order = new Order(
                member.getId(),
                date, (String) map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("packageId")) );

        //5.1 将Order对象添加到数据库里
        orderDao.add(order);

        //返回一个带有Order的Result对象
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 根据Order.id找出package.name, member.name, order.orderDate, order.orderType
     * @param id
     * @return
     */
    @Override
    public Map<String, String> findById4Detail(Integer id) {
        Map map = orderDao.findById4Detail(id);

        Date orderDate = (Date)map.get("orderDate");

        try {
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
