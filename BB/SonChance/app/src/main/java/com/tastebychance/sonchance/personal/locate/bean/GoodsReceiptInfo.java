package com.tastebychance.sonchance.personal.locate.bean;

import java.io.Serializable;

/**收货地址管理
 * Created by shenbh on 2017/8/23.
 */

public class GoodsReceiptInfo implements Serializable{
    private int id;
    private int uid	;//	用户ID
    private String username;//	收货人姓名
    private String tel;//	收货人手机号码
    private String address;//	收货地址
    private String is_check;//  1 为默认地址， 0 是普通地址
    private String location_longitude	;//	经度
    private String location_latitude	;//	纬度
    private String village	;//	所在小区
    private int city_id	;//	当前定位城市ID
    private int sex	;//	0为男，1为女
    private String address_detail	;//	完整地址信息
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }
}
