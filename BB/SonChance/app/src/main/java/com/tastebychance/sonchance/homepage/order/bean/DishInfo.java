package com.tastebychance.sonchance.homepage.order.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 点餐界面，菜品详情
 * Created by shenbh on 2017/8/23.
 */

public class DishInfo  implements Serializable {
    private int id;//菜品ID
    private String name;//菜品名称
    private List<String> pics;//菜品图片
    private String describe;//菜品描述
    private float price;//菜品价格
    private int sail_count;//已售数量
    private int comments_count;//总评论数
    private int good_comment;//好评数
    private int bad_comment;//差评数
    private String nutritive_value;//营养价值
    private String raw_material;//原材料追溯
    private List<NutrientCompositionInfo> tag_list;
    private String promotion;//买送。优惠信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSail_count() {
        return sail_count;
    }

    public void setSail_count(int sail_count) {
        this.sail_count = sail_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getGood_comment() {
        return good_comment;
    }

    public void setGood_comment(int good_comment) {
        this.good_comment = good_comment;
    }

    public int getBad_comment() {
        return bad_comment;
    }

    public void setBad_comment(int bad_comment) {
        this.bad_comment = bad_comment;
    }

    public String getNutritive_value() {
        return nutritive_value;
    }

    public void setNutritive_value(String nutritive_value) {
        this.nutritive_value = nutritive_value;
    }

    public String getRaw_material() {
        return raw_material;
    }

    public void setRaw_material(String raw_material) {
        this.raw_material = raw_material;
    }

    public List<NutrientCompositionInfo> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<NutrientCompositionInfo> tag_list) {
        this.tag_list = tag_list;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
}
