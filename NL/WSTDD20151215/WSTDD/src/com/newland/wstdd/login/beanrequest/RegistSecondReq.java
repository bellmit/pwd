package com.newland.wstdd.login.beanrequest;
/**
 * 注册的第二步
 * 请求bean
 * @author Administrator
 *
 */
public class RegistSecondReq {
	
	private String pwd;//密码
	private String headImgUrl;//头像图片地址
	private String nickName;//昵称
	private String platForm;//第三方平台
	private String openId;//绑定的
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
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
