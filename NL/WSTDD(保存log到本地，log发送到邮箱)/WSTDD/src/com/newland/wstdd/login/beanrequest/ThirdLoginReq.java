package com.newland.wstdd.login.beanrequest;
/**
 * 第三方登录的请求信息
 * @author Administrator
 * qq:“0”
 * 微信：“1”
 *  新浪微博 “2”
 *
 */
public class ThirdLoginReq {
	private String platForm;//平台
	private String openId;//获取到的平台的id
	public String getPlatForm() {
		return platForm;
	}
	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
	
}
