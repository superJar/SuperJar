package com.jar.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约信息
 */
public class Order implements Serializable {

    public static final String ORDERTYPE_TELEPHONE="电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";

    private Integer id;
    private Integer member_id;
    private Date orderDate;
    private String orderType;
    private String orderStatus;
    private Integer package_id;


    public Order() {
    }

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer member_id, Date orderDate, String orderType, String orderStatus, Integer package_id) {
        this.member_id = member_id;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.package_id = package_id;
    }

    public Order(Integer id, Integer member_id, Date orderDate, String orderType, String orderStatus, Integer package_id) {
        this.id = id;
        this.member_id = member_id;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.package_id = package_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", member_id=" + member_id +
                ", orderDate=" + orderDate +
                ", orderType='" + orderType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", package_id=" + package_id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Integer package_id) {
        this.package_id = package_id;
    }
}
