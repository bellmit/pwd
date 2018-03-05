package com.newland.wstdd.mine.personalcenter.beanreq;
//绑定的请求信息
public class BindReq {
	private String platForm;//	qq:“0”  微信：“1”  新浪微博 “2”
	private String openId;//平台id
	private String phoneNum;//电话号码	
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
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	
	
}
