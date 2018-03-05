package com.tastebychance.sonchance.homepage.orderform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：SonChance 收货时间实体类
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/11 17:03
 * 修改人：Administrator
 * 修改时间：2017/9/11 17:03
 * 修改备注：
 */

public class ReceiptTimeInfo  implements Serializable {
    private String datetime;//	显示的时间
    private List<String> times;//	改天所能配送的时间段

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
