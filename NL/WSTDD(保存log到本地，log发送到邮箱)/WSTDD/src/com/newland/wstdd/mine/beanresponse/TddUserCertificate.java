package com.newland.wstdd.mine.beanresponse;

import java.io.Serializable;

/**
 * TddUserCertificate domain对象
 */
public class TddUserCertificate implements Serializable {

	/**
	 * @fields userId 用户标识
	 */
	private java.lang.String userId;
	/**
	 * @fields userName 真实姓名
	 */
	private java.lang.String userName;
	/**
	 * @fields headimgurl 用户头像
	 */
	private java.lang.String headimgurl;

	/**
	 * @fields nickName 昵称
	 */
	private java.lang.String nickName;
	/**
	 * @fields mobilePhone 手机
	 */
	private java.lang.String mobilePhone;
	/**
	 * @fields identity 身份证号
	 */
	private java.lang.String identity;
	/**
	 * @fields cerStatus 状态 1.未认证 30.初步认证 90.身份认证
	 */
	private java.lang.Integer cerStatus;
	/**
	 * @fields sex 性别 （1是男性，2是女性，0是未知）
	 */
	private java.lang.Integer sex;
	/**
	 * @fields email 邮箱
	 */
	private java.lang.String email;

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(java.lang.String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public java.lang.String getNickName() {
		return nickName;
	}

	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}

	public java.lang.String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(java.lang.String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public java.lang.String getIdentity() {
		return identity;
	}

	public void setIdentity(java.lang.String identity) {
		this.identity = identity;
	}

	public java.lang.Integer getCerStatus() {
		return cerStatus;
	}

	public void setCerStatus(java.lang.Integer cerStatus) {
		this.cerStatus = cerStatus;
	}

	public java.lang.Integer getSex() {
		return sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

}
