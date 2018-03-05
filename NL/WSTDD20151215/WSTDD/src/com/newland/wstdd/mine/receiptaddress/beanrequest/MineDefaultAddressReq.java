package com.newland.wstdd.mine.receiptaddress.beanrequest;

import java.util.List;

/**
 * 默认地址的请求信息
 * @author Administrator
 *
 */
public class MineDefaultAddressReq {
	private String oldId;//原来默认地址
	private String newId;//新的默认地址的id
	public String getOldId() {
		return oldId;
	}
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
	public String getNewId() {
		return newId;
	}
	public void setNewId(String newId) {
		this.newId = newId;
	}
	
}
