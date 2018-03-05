package com.newland.wstdd.login.beanrequest;
/**
 * 登录请求信息
 * @author Administrator
 *
 */
public class LoginReq {

	private String phoneNum;
	private String pwd;
	
	

	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
