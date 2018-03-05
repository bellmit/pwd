package com.tastebychance.sonchance.homepage.afterpay.bean;


import com.tastebychance.sonchance.homepage.toevaluate.bean.ButtonInfo;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

/**
 * 配送页面接口
 * Created by shenbh on 2017/8/30.
 */
public class AfterPayInfo  implements Serializable {
    private String order_sn;//	订单编号
    private String received_address;//	收货地址
    private String received_tel;//	收货人电话
    private String received_username;//	收货人姓名
    private int status;//订单状态，0等待支付1等待接单2已接单3已配送4已送达5已评价6已取消
    private List<OrderDetailInfo> order_detail;//	订单详情
    private String send_time;//送达时间
    private float coupon;//	优惠券金额
    private String total_price	;//	总价格
    private List<ButtonInfo> button;
    private String create_time;//下单时间
    private String ded_price  ;	//实付金额

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getReceived_address() {
        return received_address;
    }

    public void setReceived_address(String received_address) {
        this.received_address = received_address;
    }

    public String getReceived_tel() {
        return received_tel;
    }

    public void setReceived_tel(String received_tel) {
        this.received_tel = received_tel;
    }

    public String getReceived_username() {
        return received_username;
    }

    public void setReceived_username(String received_username) {
        this.received_username = received_username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderDetailInfo> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailInfo> order_detail) {
        this.order_detail = order_detail;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public float getCoupon() {
        return coupon;
    }

    public void setCoupon(float coupon) {
        this.coupon = coupon;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<ButtonInfo> getButton() {
        return button;
    }

    public void setButton(List<ButtonInfo> button) {
        this.button = button;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDed_price() {
        return ded_price;
    }

    public void setDed_price(String ded_price) {
        this.ded_price = ded_price;
    }
}
