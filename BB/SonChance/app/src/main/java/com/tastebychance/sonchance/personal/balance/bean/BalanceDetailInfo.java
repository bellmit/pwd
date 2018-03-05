package com.tastebychance.sonchance.personal.balance.bean;

import java.io.Serializable;

/**
 * 项目名称：SonChance 账户余额明细
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/25 18:05
 * 修改人：Administrator
 * 修改时间：2017/9/25 18:05
 * 修改备注：
 */

public class BalanceDetailInfo  implements Serializable {
    private int id	;//	明细ID
    private int uid	;//	用户ID
    private String order_sn	;//	充值订单
    private int pay_type	;//	支付类型 1 微信支付 2 支付宝支付
    private float money	;//	充值金额
    private int status	;//	0 充值失败 ，1 充值成功
    private String create_time	;//	充值时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
