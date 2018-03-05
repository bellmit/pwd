package com.newland.wstdd.login.beanresponse;

import java.io.Serializable;

public class TddAdvCfgVo implements Serializable{
	/**
	 * 广告
	 */
	private static final long serialVersionUID = 1L;
	private String advId;//'广告标识',
	private String advName;//广告名称'
	private String advTitle;//广告标题
	private String advDesc;//广告说明',
	private String advLocation;// 广告位置  1001：app首页',
	private String advType;//广告类型  1.活动  2.网页',
	private String advImg;//广告图片地址
	private String advHref;//活动标识或者链接url',
	private String weight;// '权重',
	private String createTime;//创建时间',
	private String userId;//'创建人',
	public String getAdvId() {
		return advId;
	}
	public void setAdvId(String advId) {
		this.advId = advId;
	}
	public String getAdvName() {
		return advName;
	}
	public void setAdvName(String advName) {
		this.advName = advName;
	}
	public String getAdvTitle() {
		return advTitle;
	}
	public void setAdvTitle(String advTitle) {
		this.advTitle = advTitle;
	}
	public String getAdvDesc() {
		return advDesc;
	}
	public void setAdvDesc(String advDesc) {
		this.advDesc = advDesc;
	}
	public String getAdvLocation() {
		return advLocation;
	}
	public void setAdvLocation(String advLocation) {
		this.advLocation = advLocation;
	}
	public String getAdvType() {
		return advType;
	}
	public void setAdvType(String advType) {
		this.advType = advType;
	}
	public String getAdvImg() {
		return advImg;
	}
	public void setAdvImg(String advImg) {
		this.advImg = advImg;
	}
	public String getAdvHref() {
		return advHref;
	}
	public void setAdvHref(String advHref) {
		this.advHref = advHref;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
