package com.newland.wstdd.originate.beanresponse;

import java.util.List;

import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.bean.TddAdvCfgVo;

/**
 * userId的登入接口 获取发起页面的相关信息
 * 
 * @author Administrator
 * 
 */
public class OriginateFragmentRes {
	private List<String> tags; // 标签数组ps:[登山、电影]
	private int myAcNum; // 我发起的活动数
	private String headImgUrl; // 头像地址
	private List<TddAdvCfgVo> homeAds; // 首页轮播广告数组对象TddAdvCfgVo
	private String nickName; // 昵称
	private String userId; // 用户id
	private String mySignAcNum; // 我参加的活动数
	private String myFavAcNum; // 我收藏的活动数
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public int getMyAcNum() {
		return myAcNum;
	}
	public void setMyAcNum(int myAcNum) {
		this.myAcNum = myAcNum;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public List<TddAdvCfgVo> getHomeAds() {
		return homeAds;
	}
	public void setHomeAds(List<TddAdvCfgVo> homeAds) {
		this.homeAds = homeAds;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMySignAcNum() {
		return mySignAcNum;
	}
	public void setMySignAcNum(String mySignAcNum) {
		this.mySignAcNum = mySignAcNum;
	}
	public String getMyFavAcNum() {
		return myFavAcNum;
	}
	public void setMyFavAcNum(String myFavAcNum) {
		this.myFavAcNum = myFavAcNum;
	}

}
