package com.newland.wstdd.find.categorylist.registrationedit.beanrequest;

import java.util.List;

/**
 * 随行人员儿童信息
 * @author Administrator
 *
 */
public class KidInfo {
	private int kidPersonType;//人员类型
	private String kidUserName;//随行儿童姓名
	private List<MainSignAttr> kidSignAttr;//必选项
	public int getKidPersonType() {
		return kidPersonType;
	}
	public void setKidPersonType(int kidPersonType) {
		this.kidPersonType = kidPersonType;
	}
	public String getKidUserName() {
		return kidUserName;
	}
	public void setKidUserName(String kidUserName) {
		this.kidUserName = kidUserName;
	}
	public List<MainSignAttr> getKidSignAttr() {
		return kidSignAttr;
	}
	public void setKidSignAttr(List<MainSignAttr> kidSignAttr) {
		this.kidSignAttr = kidSignAttr;
	}
	
}
