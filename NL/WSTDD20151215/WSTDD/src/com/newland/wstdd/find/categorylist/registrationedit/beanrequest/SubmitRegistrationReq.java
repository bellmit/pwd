package com.newland.wstdd.find.categorylist.registrationedit.beanrequest;

import java.util.List;

import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.AdultInfo;
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.KidInfo;
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.MainSignAttr;

import android.R.integer;

/**
 * 提交报名信息的请求消息
 * @author Administrator
 *
 */
public class SubmitRegistrationReq {

	private List<MainSignAttr> mainSignAttr;//报名信息map  MainSignAttr
	private int personType;// 1
	private List<AdultInfo> adultInfos;
	private List<KidInfo> kidInfos;
	public List<MainSignAttr> getMainSignAttr() {
		return mainSignAttr;
	}
	public void setMainSignAttr(List<MainSignAttr> mainSignAttr) {
		this.mainSignAttr = mainSignAttr;
	}
	public int getPersonType() {
		return personType;
	}
	public void setPersonType(int personType) {
		this.personType = personType;
	}
	public List<AdultInfo> getAdultInfos() {
		return adultInfos;
	}
	public void setAdultInfos(List<AdultInfo> adultInfos) {
		this.adultInfos = adultInfos;
	}
	public List<KidInfo> getKidInfos() {
		return kidInfos;
	}
	public void setKidInfos(List<KidInfo> kidInfos) {
		this.kidInfos = kidInfos;
	}
	
}
