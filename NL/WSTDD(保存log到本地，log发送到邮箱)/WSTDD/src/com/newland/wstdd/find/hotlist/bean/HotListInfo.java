/**
 * 
 */
package com.newland.wstdd.find.hotlist.bean;

import java.io.Serializable;

/**发现-热门列表
 * @author H81 2015-11-17
 *
 */
public class HotListInfo implements Serializable{
	private String activityId;//活动的ID
	private int activityType	;//活动分类
	private String imgUrl	;//图片一，封面图片
	private String activityTitle	;//标题
	private String friendActivityTime;//
	private String 	activityAddress	;//活动地址
	private int signCount	;//报名人数
	private int needPay	;//是否需要缴费 1.缴费 2.不缴费
	private int status;//报名状态
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public int getActivityType() {
		return activityType;
	}
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	
	public String getFriendActivityTime() {
		return friendActivityTime;
	}
	public void setFriendActivityTime(String friendActivityTime) {
		this.friendActivityTime = friendActivityTime;
	}
	public String getActivityAddress() {
		return activityAddress;
	}
	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}
	public int getSignCount() {
		return signCount;
	}
	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}
	public int getNeedPay() {
		return needPay;
	}
	public void setNeedPay(int needPay) {
		this.needPay = needPay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
