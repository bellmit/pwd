package com.newland.wstdd.find.find.bean;
/**
 * 发现主页
 * @author Administrator
 *
 */
public class FindReq {

	private String province;//数据所属的省份
	private String city;//数据所属的市
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
