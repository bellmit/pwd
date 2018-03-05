package com.tastebychance.sonchance.homepage.afterpay.bean;

import com.alibaba.fastjson.JSON;
import com.tastebychance.sonchance.homepage.orderform.bean.GiveDetailInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：SonChance 配送页面接口
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/19 9:31
 * 修改人：Administrator
 * 修改时间：2017/9/19 9:31
 * 修改备注：
 */

public class OrderDetailInfo  implements Serializable {
    private int id	;//	订单详情ID号
    private int  order_id	;//	订单ID
    private int uid	;//	用户ID
    private int dishes_id	;//	菜品ID号
    private String dishes_name	;//	菜品名称
    private int count	;//	菜品数量
    private float unit_price	;//	菜品单价

    private Object give	;//	赠送内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
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
