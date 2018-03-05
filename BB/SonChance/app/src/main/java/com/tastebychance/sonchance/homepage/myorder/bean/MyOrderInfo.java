package com.tastebychance.sonchance.homepage.myorder.bean;

import com.tastebychance.sonchance.homepage.toevaluate.bean.ButtonInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：SonChance 我的订单
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/20 21:16
 * 修改人：Administrator
 * 修改时间：2017/9/20 21:16
 * 修改备注：
 */

public class MyOrderInfo  implements Serializable {

    private String order_detail;//	订单第一个菜名
    private int count;//	订单菜品数量
    private String order_sn;//订单号
    private int status;//	订单状态 0:等待支付 1：等待送达 2：订单已完成 3：订单已评价 4：订单已取消
    private List<ButtonInfo> button;//	订单按钮
    private String text;//	订单文字
    private int action_id;//	按钮操作 0 : 去支付 1：联系商家 2：确认收货 3：再来一单 4：去评价 5：订单取消
    private List<MyOrderDetailInfo> detail_list;//	订单详情
    private float ded_price;//	实付

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(String order_detail) {
        this.order_detail = order_detail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ButtonInfo> getButton() {
        return button;
    }

    public void setButton(List<ButtonInfo> button) {
        this.button = button;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public List<MyOrderDetailInfo> getDetail_list() {
        return detail_list;
    }

    public void setDetail_list(List<MyOrderDetailInfo> detail_list) {
        this.detail_list = detail_list;
    }

    public float getDed_price() {
        return ded_price;
    }

    public void setDed_price(float ded_price) {
        this.ded_price = ded_price;
    }
}
