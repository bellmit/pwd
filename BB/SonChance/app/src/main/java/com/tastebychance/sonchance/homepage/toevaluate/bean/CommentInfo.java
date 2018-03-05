package com.tastebychance.sonchance.homepage.toevaluate.bean;

import java.io.Serializable;

/**
 * 项目名称：SonChance 去评价初始化界面的Info
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/19 16:15
 * 修改人：Administrator
 * 修改时间：2017/9/19 16:15
 * 修改备注：
 */

public class CommentInfo  implements Serializable {
    private int id	;//	订单详情ID
    private int order_id	;//	订单ID号
    private int uid	;//	用户ID号
    private int dishes_id	;//	菜品ID
    private String dishes_name	;//	菜品名称
    private int count	;//	数量
    private float unit_price	;//	菜品单价
    private String give	;//	赠送内容

    private float grade;//评分
    private String content;//评价内容

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

    public String getGive() {
        return give;
    }

    public void setGive(String give) {
        this.give = give;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
