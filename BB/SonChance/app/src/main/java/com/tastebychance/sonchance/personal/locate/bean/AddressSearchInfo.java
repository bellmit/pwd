package com.tastebychance.sonchance.personal.locate.bean;

import java.io.Serializable;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/10/10 13:33
 * 修改人：Administrator
 * 修改时间：2017/10/10 13:33
 * 修改备注：
 */

public class AddressSearchInfo implements Serializable{
    private String city;//城市
    private String keyword;//关键字
    private double location_longitude	;//	经度
    private double location_latitude	;//	纬度

    //用于列表
    private String locationName;//搜到的大致的地名
    private String detailLocationName;//搜到的详细的地名

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(double location_longitude) {
        this.location_longitude = location_longitude;
    }

    public double getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(double location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDetailLocationName() {
        return detailLocationName;
    }

    public void setDetailLocationName(String detailLocationName) {
        this.detailLocationName = detailLocationName;
    }
}
