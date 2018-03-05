package com.tastebychance.sonchance.homepage.orderform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 下单支付、待结算时的订单返回信息
 * Created by shenbh on 2017/8/26.
 */

public class OrderInfo implements Serializable{
    private List<OrderDetailInfo> list;//	订单数组
    private float origin_price	;//	总价格
    private int coupon_count;//	可用优惠券数量
    private String datetime	;//	显示默认配送时间
    private String mins	;//	显示默认配送时间
    private float money	;//	账户余额
    public List<OrderDetailInfo> getList() {
        return list;
    }

    public void setList(List<OrderDetailInfo> list) {
        this.list = list;
    }

    public float getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(float origin_price) {
        this.origin_price = origin_price;
    }

    public int getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(int coupon_count) {
        this.coupon_count = coupon_count;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
