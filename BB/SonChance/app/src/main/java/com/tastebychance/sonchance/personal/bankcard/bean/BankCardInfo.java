package com.tastebychance.sonchance.personal.bankcard.bean;

import java.io.Serializable;
import java.util.Date;

/**银行卡列表接口
 * Created by shenbh on 2017/9/5.
 */

public class BankCardInfo  implements Serializable {

    private int id	            ;//	银行卡ID
    private int uid	            ;//	用户ID
    private String user_name	;//	持卡人姓名
    private String user_number	;//	身份证号
    private String bank_name	;//	银行名称
    private String bank_number	;//	银行卡号
    private String mobile	    ;//	手机号
    private Date create_time	;//	绑定时间
    private String letter	;//	银行字母代号
    private String bank_type	;//	银行卡类型

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }
}
