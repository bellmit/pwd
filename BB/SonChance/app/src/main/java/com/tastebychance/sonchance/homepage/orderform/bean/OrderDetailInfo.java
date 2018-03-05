package com.tastebychance.sonchance.homepage.orderform.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：SonChance 下单支付、待结算时的订单返回信息的订单详情
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/8 11:15
 * 修改人：Administrator
 * 修改时间：2017/9/8 11:15
 * 修改备注：
 */

public class OrderDetailInfo implements Serializable{
    private int dishes_id;//	菜品ID
    private String dishes_name;//	菜品名称
    private int count;//	菜品数量
    private float price;//	价格
    private Object give;//	赠送信息

    public int getDishes_id() {
        return dishes_id;
    }

    public void setDishes_id(int dishes_id) {
        this.dishes_id = dishes_id;
    }

    public String getDishes_name() {
        return dishes_name;
    }

    public void setDishes_name(String dishes_name) {
        this.dishes_name = dishes_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Object getGive() {
        return give;
    }

    public void setGive(Object give) {
        this.give = give;
    }

    public <T> List<T> getGiveList(Class<T> cla){
        String str = JSON.toJSONString(getGive());
        List<T> list = JSON.parseArray(str, cla);
        if(list == null){
            list = new ArrayList<T>();
        }
        return list;
    }
}
