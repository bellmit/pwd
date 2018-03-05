package com.newland.wstdd.find.categorylist.bean;

import java.io.Serializable;

import android.widget.ImageView;

public class FindCategoryListViewInfo implements Serializable{
	private String activityId;//活动id
	private int activityType	;//活动分类
	private String image1	;//图片一，封面图片
	private String activityTitle	;//标题
	private String friendActivityTime;//
	private String 	activityAddress	;//活动地址
	private int signCount	;//报名人数
	private int needPay	;//是否需要缴费 1.缴费 2.不缴费
	public int getActivityType() {
		return activityType;
	}
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
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
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
}
