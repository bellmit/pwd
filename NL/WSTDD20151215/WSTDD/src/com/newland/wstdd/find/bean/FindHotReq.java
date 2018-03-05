package com.newland.wstdd.find.bean;
/**
 * 热门活动列表
 * @author Administrator
 *
 */
public class FindHotReq {
	private String province;//省份  默认为全国
	private String city;//市  默认为签过
	private String page;//页码
	private String row;//显示的行数   0开始
	private String searchText;//搜索字符
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
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
}
