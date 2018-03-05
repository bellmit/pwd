package com.newland.wstdd.originate.beanresponse;

import java.util.List;

import com.newland.wstdd.common.bean.TddActivity;

/**
 * 首页返回的信息
 * @author Administrator
 *
 */
public class OriginateSearchRes {

	private List<TddActivity> acList;//返回的列表对象

	public List<TddActivity> getAcList() {
		return acList;
	}

	public void setAcList(List<TddActivity> acList) {
		this.acList = acList;
	}
	
}
