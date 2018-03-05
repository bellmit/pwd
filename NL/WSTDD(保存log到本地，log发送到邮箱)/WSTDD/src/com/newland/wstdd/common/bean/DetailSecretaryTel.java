package com.newland.wstdd.common.bean;

import java.io.Serializable;

/**
 * 团秘 对象
 * @author Administrator
 *
 */
public class DetailSecretaryTel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//姓名
	private String tel;//电话
	private String headurl;//头像
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	
}
