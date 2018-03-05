package com.tastebychance.sonchance.util;

import com.tastebychance.sonchance.homepage.homeshoppingcart.bean.ShoppingCartBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final int RESULT_SUCCESS = 0;//请求服务器成功

	public static final boolean IS_LOCALDATA = false;//是否是本地数据
	public static final boolean IS_DEVELOPING = false;//是否是开发阶段

	public static final int GETADDRESS_RETURNCODE = 0x00000;//获取地址
	public static final int GETSEARCHADDRESS_RETURNCODE = 0x00005;//获取搜索到的地址
	public static final int WHAT_REFRESH = 0X00001;
	public static final int WHAT_LOADMORE = 0X00002;
	public static final int BIND_MOBILE_SUCCESS = 0X00003;

	public static final String TEMP = "TEMP";//保存SharedPreference数据
	public static final String TAG = "SonChance";//Log的标签
	public static final String LOGINSUCCESS_ACTION = "LOGINSUCCESS_ACTION";//登录成功提示
	public static final String CHOSEADDRESS_ACTION = "CHOSEADDRESS_ACTION";//选择收货地址
	public static final String CHANGETOHOME_ACTION = "CHANGETOHOME_ACTION";//切换到首页
	public static final String USE_COUPON = "USE_COUPON";//使用优惠券

	public static final String PAY_SUCCESS_ACTION = "PAY_SUCCESS_ACTION";//支付成功
	public static final String PAY_CANCEL_ACTION = "PAY_CANCEL_ACTION";//取消支付
	public static final String PAY_ERROR_ACTION = "PAY_ERROR_ACTION";//支付错误
	public static final String PAYACTIVITY_FINISH_ACTION = "PAYACTIVITY_FINISH_ACTION";//

	public static final String RECHARGE_SUCCESS_ACTION = "RECHARGE_SUCCESS_ACTION";//支付成功
	public static final String RECHARGE_CANCEL_ACTION = "RECHARGE_CANCEL_ACTION";//取消支付
	public static final String RECHARGE_ERROR_ACTION = "RECHARGE_ERROR_ACTION";//支付错误
	public static final String BALANCE_FINISH_ACTION = "BALANCE_FINISH_ACTION";//

	//跳转到哪个界面
	public static final String TO_PERSON = "TO_PERSON";//我的
	public static final String TO_PERSONCENTER = "TO_PERSONCENTER";//个人中心
	public static final String TO_BALANCE = "TO_BALANCE";//余额
	public static final String TO_BINDBANKCARD = "TO_BINDBANKCARD";//绑定银行卡
	public static final String TO_CREDITCARD = "TO_CREDITCARD";//信用卡
	public static final String TO_MYCOUPON = "TO_MYCOUPON";//我的优惠券
	public static final String TO_CEIVEADDRESS = "TO_CEIVEADDRESS";//地址管理
	public static final String TO_MEMBERCENTER ="TO_MEMBERCENTER";//会员中心
	public static final String TO_MYEVALUATE ="TO_MYEVALUATE";//我的评论
	public static final String TO_SCAN ="TO_SCAN";//扫一扫
	public static final String TO_CHECKVERSION ="TO_CHECKVERSION";//版本更新
	public static final String TO_ORDERFORM = "TO_ORDERFORM";//配送
	public static final String TO_CATERINGSERVICE = "TO_CATERINGSERVICE";//配餐服务
	public static final String TO_OPINIONFEEDBACK = "TO_OPINIONFEEDBACK";//意见反馈
	public static final String TO_HEALTHYLIFE = "TO_HEALTHYLIFE";//健康生活
	public static final String TO_MYORDER = "TO_MYORDER";//我的订单
	public static final String TO_DISHSEARCH = "TO_DISHSEARCH";//搜索菜品
	public static final String DO_NOTHING = "DO_NOTHING";//取消

	public static final String WXAPPID = "wx76ec42464d54a739";//微信开放平台appid
	public static final String MCHID = "101510136180";//威富通商户号
	public static final String SIGNKEY = "b9d41b59230abaf8cbfdf2ab5e0240f7";//威富通密钥

	//测试的demo
//	public static final String WXAPPID = "wx2a5538052969956e";//微信开放平台appid
//	public static final String MCHID = "755437000006";//威富通商户号
//	public static final String SIGNKEY = "7daa4babae15ae17eee90c9e";//威富通密钥

	//购物车中的菜品
	//TODO: 需要保存到本地or提交到服务器保存
	public static ArrayList<Map<String,Object>> orderedDishes = new ArrayList<Map<String,Object>>();

//	public static HashMap<Integer,ShoppingCartBean> shoppingCartHashMap = new HashMap<Integer, ShoppingCartBean>();


}
