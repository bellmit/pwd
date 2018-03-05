package com.tastebychance.sonchance.personal.bean;

import java.io.Serializable;
import java.util.Date;

/**获取用户信息接口
 * Created by shenbh on 2017/8/30.
 */

public class UserInfo implements Serializable{
    private int id	;//	用户ID号
    private String user_pass	;//	用户密码
    private String user_name	;//	用户名称
    private String user_nicename	;//	用户昵称
    private String user_email	;//	用户邮箱
    private String user_url	;//	用户个人主页地址
    private String avatar	;//	用户头像
    private int sex		;//o为保密，1为男，2为女
    private Date birthday	;//	用户生日
    private String signature	;//	用户个性签名
    private String last_login_ip	;//	用户最近一次登录的IP地址
    private String last_login_time	;//	用户最近一次登录的时间
    private String create_time	;//	用户注册时间
    private String user_activation_key	;//	激活码
    private int user_status	;//	用户状态 0：禁用； 1：正常 ；2：未验证
    private int score	;//	用户积分
    private int user_type	;//	用户类型，1:admin ;2:会员
    private float money	;//	账户余额
    private String mobile	;//	用户手机号码
    private String auth	;//	第三方登录方式，”qq”,”weixin”,”weibo”
    private String openid	;//	第三方登录的openid
    private int is_complete	;//	是否完善信息，活动用
    private int grade;//用户等级
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_activation_key() {
        return user_activation_key;
    }

    public void setUser_activation_key(String user_activation_key) {
        this.user_activation_key = user_activation_key;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(int is_complete) {
        this.is_complete = is_complete;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
