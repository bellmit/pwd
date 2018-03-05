package com.tastebychance.sonchance.homepage.pay.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：SonChance 支付页面接口bean
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/26 13:59
 * 修改人：Administrator
 * 修改时间：2017/9/26 13:59
 * 修改备注：
 */

public class GetPayInfo implements Serializable{
    private OrderInfo order;
    private List<OrderDetailInfo> order_detail;
    private float money;
    private long time;

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }

    public List<OrderDetailInfo> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailInfo> order_detail) {
        this.order_detail = order_detail;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
