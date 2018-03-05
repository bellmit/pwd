package com.newland.wstdd.login.beanresponse;
/**
 * 注册第一步的服务器返回值
 * @author Administrator
 *
 */
public class RegistFirstRes {
	private String userId;//用户id
	private String headImgUrl;//头像地址
	private String nickName;//昵称
	private String pwd;//密码
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
