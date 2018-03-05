package com.newland.wstdd.login.clearPersonInfo;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.widget.ShareActionProvider;
import com.newland.wstdd.common.common.AppContext;


/**
 * 退出当前用户信息，需要做的事情
 * 1.清除Appcontext里面的数据
 * 2.清除掉SharePreference里面的数据
 * @author Administrator
 *
 */
/**
 * 
 *  private String userId;//保存用户的ID 1
	private String userName;//真实姓名 2
	private String headImgUrl;//第三方头像 3
	private String nickName;//昵称 4
	private String mobilePhone;//手机号 5
	private String userPwd;//保存用户的密码 6
	private String sex;//性别 7
	private String email;//邮箱 8
	private String identity;//身份证 9
	private String cerStatus;//认证状态   1.未认证 30.初步认证  90.身份认证  10
	private String wechat_union_id;	//微信开放平台标识	没绑定 则为null  11
	private String qq_open_id;	//QQ统一认证标识	没绑定 则为null  12
	private String weibo_open_id;	//微博认证ID	没绑定 则为null  13
	private String myQucode;	//我的二维码字符串  14
	private List<String> tags = new ArrayList<String>();//标签数组ps:[登山、电影]  15
    private String myAcNum;	     //   我发起的活动数  16
    private String mySignAcNum;	//我参加的活动数  17
    private String myFavAcNum;	//我收藏的活动数  18
	//保留的全局变量
	private String openId;//第三方的ID  就是根据什么登录的时候   19
	private String platForm;//第三方平台  就是哪一个第三方的平台  20
 *
 */
public class ClearPersonInfo {
	private AppContext appContext;
	public ClearPersonInfo(){
		appContext=AppContext.getAppContext();
	}
	//清除掉AppContext里面的功能
	public  void clearAppcontextInfo(){
		appContext.setUserId("");
		appContext.setUserName("");
		appContext.setHeadImgUrl("");
		appContext.setNickName("");
		appContext.setMobilePhone("");
		appContext.setSex("");
		appContext.setEmail("");
		appContext.setIdentity("");
		appContext.setCerStatus("");
		appContext.setWechat_union_id("");
		appContext.setQq_open_id("");
		appContext.setWeibo_open_id("");
		appContext.setMyQucode("");
		appContext.setTags(null);
		appContext.setMyAcNum("0");
		appContext.setMySignAcNum("0");
		appContext.setMyFavAcNum("0");
		appContext.setOpenId("");
		appContext.setVersionCode("");
		
		
	};
	//清除掉SharePreference里面的信息
	@SuppressLint("NewApi")
	public  void clearPersonInfo(){
		new ShareActionProvider(appContext.getContext());
		
		
	};
	
}
