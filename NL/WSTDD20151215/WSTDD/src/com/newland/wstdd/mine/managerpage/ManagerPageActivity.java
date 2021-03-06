/**
 * 
 */
package com.newland.wstdd.mine.managerpage;


import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.find.categorylist.detail.FindChairDetailActivity;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.applyList.ManagerApplyListActivity;
import com.newland.wstdd.mine.beanresponse.TddUserCertificate;
import com.newland.wstdd.mine.managerpage.activitycode.ActivityCodeActivity;
import com.newland.wstdd.mine.managerpage.beanrequest.OnTddRecommendReq;
import com.newland.wstdd.mine.managerpage.beanrequest.OpenActivityPeoplesReq;
import com.newland.wstdd.mine.managerpage.beanrequest.RegistrationCheckReq;
import com.newland.wstdd.mine.managerpage.beanrequest.RegistrationStateReq;
import com.newland.wstdd.mine.managerpage.beanresponse.OnTddRecommendRes;
import com.newland.wstdd.mine.managerpage.beanresponse.OpenActivityPeoplesRes;
import com.newland.wstdd.mine.managerpage.beanresponse.RegistrationCheckRes;
import com.newland.wstdd.mine.managerpage.beanresponse.RegistrationStateRes;
import com.newland.wstdd.mine.managerpage.handle.ManagerPagerSingleActivityHandle;
import com.newland.wstdd.mine.managerpage.handle.ManagerpageHandle;
import com.newland.wstdd.mine.managerpage.ilike.LikeListActivity;
import com.newland.wstdd.mine.managerpage.multitext.MultiTextActivity;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
import com.newland.wstdd.originate.origateactivity.OriginateChairActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * ????????????
 * 
 * @author H81 2015-11-10
 * 
 */
/**
 * @author H81 2015-11-12
 * 
 */

public class ManagerPageActivity extends BaseFragmentActivity implements OnPostListenerInterface, OnCheckedChangeListener, IWXAPIEventHandler {
	private AppContext appContext = AppContext.getAppContext();

	/**
	 * ??????
	 */
	private PopupWindow popupWindow;// ????????????
	/**
	 * ???????????????
	 */
	// ??????
	private static final String appid = "wx1b84c30d9f380c89";// ?????????appid
	private IWXAPI wxApi;// ?????????API
	// QQ
	private Tencent mTencent;
	private static final String APP_ID = "1104957952";


	//????????????
	private IntentFilter filter;// ??????????????????????????????
	private IntentFilter filter2;//???????????????????????????????????????????????????
	private TextView activity_managerpage_activityType_tv;//????????????
	private TextView activity_managerpage_activityTitle_tv;//????????????

	private com.newland.wstdd.common.widget.PengTextView mActivity_managerpage_edit;// ????????????
	private com.newland.wstdd.common.widget.PengTextView mActivity_managerpage_detail;// ????????????
	private com.newland.wstdd.common.widget.PengTextView mActivity_managerpage_signlist;// ????????????
	private com.newland.wstdd.common.widget.PengTextView mActivity_managerpage_share;// ??????
	private com.newland.wstdd.common.widget.PengTextView mActivity_managerpage_groupsend;// ????????????
	private com.newland.wstdd.common.widget.PengTextView mActivity_managerpage_activitycode;// ???????????????
	private LinearLayout fouritem_ll;//???????????????LinearLayout
	
	// ??????
	private LinearLayout mActivity_managerpage_signup_ll;
	private TextView mActivity_managerpage_signup_content_tv;
	private TextView mActivity_managerpage_signup_tv;
	// ??????
	private LinearLayout mActivity_managerpage_comment_ll;
	private TextView mActivity_managerpage_comment_content_tv;
	private TextView mActivity_managerpage_comment_tv;
	// ??????
	private LinearLayout mActivity_managerpage_like_ll;
	private TextView mActivity_managerpage_like_content_tv;
	private TextView mActivity_managerpage_like_tv;
	// ??????
	private TextView mActivity_managerpage_read_content_tv;
	// // ????????????
	// private RelativeLayout mActivity_managerpage_payment_rl;
	// private LinearLayout mActivity_managerpage_payment_content_ll;
	// private TextView mActivity_managerpage_payment_cash_tv;// ????????????
	// private TextView mActivity_managerpage_payment_online_tv;// ????????????
	// private ImageView mActivity_managerpage_payment_line_iv;// ?????????
	// private ImageView mActivity_managerpage_payment_extendable_iv;// ????????????
	// // ????????????
	// private RelativeLayout mActivity_managerpage_distributionmethod_rl;
	// private LinearLayout mActivity_managerpage_distributionmethod_content_ll;
	// private TextView mActivity_managerpage_distributionmethod_self_tv;// ??????
	// private TextView mActivity_managerpage_distributionmethod_safe_tv;// ????????????
	// private TextView mActivity_managerpage_distributionmethod_mail_tv;// ??????
	// private ImageView mActivity_managerpage_distributionmethod_line_iv;// ?????????
	// private ImageView mActivity_managerpage_distributionmethod_extendable_iv;

	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_share_penttv;// ??????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_invite_penttv;// ????????????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_cover_penttv;// ????????????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_secretary_penttv;// ????????????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_originateagain_penttv;// ????????????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_exportlist_penttv;// ????????????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_mass_penttv;// ????????????
	// private com.newland.wstdd.common.widget.PengTextView
	// mActivity_managerpage_delete_penttv;// ????????????

	private CheckBox mActivity_managerpage_isneedcheck_cb;// ??????????????????
	private CheckBox mActivity_managerpage_ispublishsignnum_cb;// ??????????????????
	private CheckBox mActivity_managerpage_isnotification_cb;// ??????????????????
	private CheckBox mActivity_managerpage_isrecommend_cb;// ?????????????????????????????????
	/** ???????????????????????????*/
	private LinearLayout mActivity_managerpage_activitystate_ll;
	private TextView mActivity_managerpage_activitystate_tv;
	private ImageView mActivity_managerpage_activitystate_iv;
	private LinearLayout isTD_ll;//?????????or??????
	private LinearLayout botttom_ll;//???????????????????????????????????????
	
	private PopupWindow statePopupWindow;
	/** ???????????????????????????*/
	// // ????????????
	// private LinearLayout mActivity_managerpage_publicrange_ll;
	// private TextView mActivity_managerpage_publicrange_tv;
	// private ImageView mActivity_managerpage_publicrange_iv;

	// ??????????????????????????????
	OpenActivityPeoplesRes openActivityPeoplesRes;// ??????????????????????????????
	OnTddRecommendRes onTddRecommendRes;// ??????????????????????????????
	RegistrationStateRes registrationStateRes;// ??????????????????
	RegistrationCheckRes registrationCheckRes;// ??????????????????????????????
	ManagerpageHandle handler = new ManagerpageHandle(this);
	//???????????????????????????????????????????????????????????????
	SingleActivityRes singleActivityRes = new SingleActivityRes();//???????????????????????????
	ManagerPagerSingleActivityHandle singleActivityHandle = new ManagerPagerSingleActivityHandle(this);
	private int signRole = 9 ;//?????????????????? 1.?????? 2.?????? 9.??????
	private String isLeader = "false";//???????????????
	private TddActivity tddActivity;// ????????????
	

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_managerpage);
		// ??????
		// QQ
		final Context ctxContext = this.getApplicationContext();
		mTencent = Tencent.createInstance(APP_ID, ctxContext);
		mHandler = new Handler();

		// weixin
		wxApi = WXAPIFactory.createWXAPI(this, appid);
		wxApi.registerApp(appid);
		getIntentData();

		setTitle();// ???????????????
		initView();// ???????????????
		
		//
		// ????????????
		filter = new IntentFilter("RECEIVE_TDDACTIVITY");// ??????????????????????????????????????????????????????tddactivity
		registerReceiver(broadcastReceiver, filter);// ?????????broadcastReceiver??????????????????????????????
		filter2 = new IntentFilter("ManagerPageActivityRefresh");
		registerReceiver(broadcastReceiver2, filter2);
		// ??????????????????????????????
		// openActivityPeoples();// ?????????????????????????????????
		// onTddRecommend();// ????????????????????????
		// setRegistrationState();// ?????????????????????
		// setRegistrationCheck();// ??????????????????
	}

	/**
	 * ?????????????????????tddActivity??????
	 * 
	 */
	private void getIntentData() {
		singleActivityRes = (SingleActivityRes) getIntent().getSerializableExtra("singleActivityRes");
		tddActivity = singleActivityRes.getTddActivity();
		
		/*tddActivity = (TddActivity) getIntent().getSerializableExtra("tddActivity");*/
//		judgeRole(tddActivity.getActivityId());
	}

	/**
	 * ??????????????????(?????????????????????????????????)
	 * @param activityId
	 * @return signRole	?????????????????? 1.?????? 2.?????? 9.??????	Int,???userSignstate?????????????????????null
	 */
	/*private void judgeRole(final String activityId){
		super.refresh();
		try {
			new Thread(){
				public void run() {
					//??????????????????request??????????????????
					SingleActivityReq singleActivityReq = new SingleActivityReq();
					singleActivityReq.setActivityId(activityId);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SingleActivityRes> retMsg = mgr.getSingleActivityInfo(singleActivityReq);
					Message message = new Message();
					message.what  = ManagerPagerSingleActivityHandle.SINGLE_ACTIVITY;
					//?????????????????????1??????????????????????????????
					if (retMsg.getCode() == 1) {
						singleActivityRes = (SingleActivityRes) retMsg.getObj();
						message.obj = singleActivityRes;
					}else {
						message.obj = retMsg.getMsg();
					}
					singleActivityHandle.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}*/
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null)
			center_tv.setText("????????????");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setOnClickListener(this);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.VISIBLE);
			right_tv.setText("??????");
			right_tv.setTextColor(getResources().getColor(R.color.red));
			right_tv.setOnClickListener(this);
		}
	}

	public void initView() {
		activity_managerpage_activityType_tv = (TextView) findViewById(R.id.activity_managerpage_activityType_tv);
		activity_managerpage_activityTitle_tv = (TextView) findViewById(R.id.activity_managerpage_activityTitle_tv);
		mActivity_managerpage_edit = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_managerpage_edit);
		mActivity_managerpage_detail = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_managerpage_detail);
		mActivity_managerpage_signlist = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_managerpage_signlist);
		mActivity_managerpage_share = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_managerpage_share);
		mActivity_managerpage_groupsend = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_managerpage_groupsend);
		mActivity_managerpage_activitycode = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_managerpage_activitycode);
		mActivity_managerpage_signup_ll = (LinearLayout) findViewById(R.id.activity_managerpage_signup_ll);
		mActivity_managerpage_signup_content_tv = (TextView) findViewById(R.id.activity_managerpage_signup_content_tv);
		mActivity_managerpage_signup_tv = (TextView) findViewById(R.id.activity_managerpage_signup_tv);
		mActivity_managerpage_comment_ll = (LinearLayout) findViewById(R.id.activity_managerpage_comment_ll);
		mActivity_managerpage_comment_content_tv = (TextView) findViewById(R.id.activity_managerpage_comment_content_tv);
		mActivity_managerpage_comment_tv = (TextView) findViewById(R.id.activity_managerpage_comment_tv);
		mActivity_managerpage_like_ll = (LinearLayout) findViewById(R.id.activity_managerpage_like_ll);
		mActivity_managerpage_like_content_tv = (TextView) findViewById(R.id.activity_managerpage_like_content_tv);
		mActivity_managerpage_like_tv = (TextView) findViewById(R.id.activity_managerpage_like_tv);
		mActivity_managerpage_read_content_tv = (TextView) findViewById(R.id.activity_managerpage_read_content_tv);
		mActivity_managerpage_activitystate_ll = (LinearLayout) findViewById(R.id.activity_managerpage_activitystate_ll);
		mActivity_managerpage_activitystate_tv = (TextView) findViewById(R.id.activity_managerpage_activitystate_tv);
		mActivity_managerpage_activitystate_iv = (ImageView) findViewById(R.id.activity_managerpage_activitystate_iv);
		mActivity_managerpage_isneedcheck_cb = (CheckBox) findViewById(R.id.activity_managerpage_isneedcheck_cb);
		mActivity_managerpage_ispublishsignnum_cb = (CheckBox) findViewById(R.id.activity_managerpage_ispublishsignnum_cb);
		mActivity_managerpage_isnotification_cb = (CheckBox) findViewById(R.id.activity_managerpage_isnotification_cb);
		mActivity_managerpage_isrecommend_cb = (CheckBox) findViewById(R.id.activity_managerpage_isrecommend_cb);
		fouritem_ll = (LinearLayout) findViewById(R.id.fouritem_ll);
		isTD_ll = (LinearLayout) findViewById(R.id.isTD_ll);
		botttom_ll = (LinearLayout) findViewById(R.id.botttom_ll);
		
		fouritem_ll.getLayoutParams().height = AppContext.getAppContext().getScreenWidth()/4;
		
		initData();
		setClickListener();

		mActivity_managerpage_isneedcheck_cb.setOnCheckedChangeListener(this);
		mActivity_managerpage_ispublishsignnum_cb.setOnCheckedChangeListener(this);
		mActivity_managerpage_isnotification_cb.setOnCheckedChangeListener(this);
		mActivity_managerpage_isrecommend_cb.setOnCheckedChangeListener(this);
		
		initViewData();
	}

	/**
	 * ?????????????????????
	 */
	private void initData() {
		activity_managerpage_activityType_tv.setText(StringUtil.intType2Str(tddActivity.getActivityType()));
		activity_managerpage_activityTitle_tv.setText(tddActivity.getActivityTitle());
		mActivity_managerpage_signup_content_tv.setText(tddActivity.getSignCount() + "");// ????????????
		mActivity_managerpage_comment_content_tv.setText(tddActivity.getCommentCount() + "");// ??????
		mActivity_managerpage_like_content_tv.setText(tddActivity.getLikeCount() + "");// ????????????
		mActivity_managerpage_read_content_tv.setText(tddActivity.getViewCount() + "");// ????????????

		if (tddActivity.getStatus()==1) {//status??? 2 ???????????????1????????????
			mActivity_managerpage_activitystate_tv.setText("????????????");
		}else if (tddActivity.getStatus() == 2) {
			mActivity_managerpage_activitystate_tv.setText("????????????");
		}
		if (tddActivity.getNeedAudit() == 1) {// ??????????????????1.?????? 2.?????????
			mActivity_managerpage_isneedcheck_cb.setSelected(true);
		} else if (tddActivity.getNeedAudit() == 2) {
			mActivity_managerpage_isneedcheck_cb.setSelected(false);
		} else if (tddActivity.getNeedPublicSigninfo() == 1) {// ????????????????????????
																// 1.??????2?????????
			mActivity_managerpage_ispublishsignnum_cb.setSelected(true);
		} else if (tddActivity.getNeedPublicSigninfo() == 2) {
			mActivity_managerpage_ispublishsignnum_cb.setSelected(false);
		}
		// TODO ??????????????????????????????

		else if (StringUtil.isNotEmpty(tddActivity.getActivityId()) && StringUtil.isNotEmpty(tddActivity.getShareScope() + "") && StringUtil.isNotEmpty(tddActivity.getPublicWeight() + "")) {// ?????????????????????????????????
			// ??????
			mActivity_managerpage_isrecommend_cb.setSelected(true);
		} else {
			mActivity_managerpage_isrecommend_cb.setSelected(false);
		}
	}

	private void setClickListener() {
		mActivity_managerpage_edit.setOnClickListener(this);
		mActivity_managerpage_detail.setOnClickListener(this);
		mActivity_managerpage_signlist.setOnClickListener(this);
		mActivity_managerpage_share.setOnClickListener(this);
		mActivity_managerpage_groupsend.setOnClickListener(this);
		mActivity_managerpage_activitycode.setOnClickListener(this);
		// mActivity_managerpage_signup_ll.setOnClickListener(this);
		// mActivity_managerpage_comment_ll.setOnClickListener(this);
		 mActivity_managerpage_like_ll.setOnClickListener(this);
		mActivity_managerpage_activitystate_ll.setOnClickListener(this);
		mActivity_managerpage_activitystate_tv.setOnClickListener(this);
		mActivity_managerpage_activitystate_iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.activity_managerpage_edit:// ????????????

			intent = new Intent(this, OriginateChairActivity.class);
			Bundle bundle1 = new Bundle();
			bundle1.putSerializable("tddActivity", tddActivity);
			bundle1.putString("activity_action", "edit");
			intent.putExtras(bundle1);
			startActivity(intent);
			break;
		case R.id.activity_managerpage_detail:// ????????????
			intent = new Intent(ManagerPageActivity.this, FindChairDetailActivity.class);// ???????????????
			Bundle bundle2 = new Bundle();
			if (singleActivityRes != null) {
				bundle2.putSerializable("tddActivity", singleActivityRes.getTddActivity());
			}
			intent.putExtras(bundle2);
			startActivity(intent);
			break;
		// TODO ????????????
		case R.id.activity_managerpage_signlist:// ????????????
			// intent = new Intent(this, MineRegistrationListActivity.class);
			// startActivity(intent);
			intent = new Intent(this, ManagerApplyListActivity.class);
			intent.putExtra("activityId", tddActivity.getActivityId());
			intent.putExtra("signRole", signRole);
			intent.putExtra("isLeader", isLeader);
			startActivity(intent);
			break;
		case R.id.activity_managerpage_share:// ??????
			UiHelper.ShowOneToast(this, "?????????");
			getPopWindow();

			break;
		case R.id.activity_managerpage_groupsend:// ????????????
			intent = new Intent(this, MultiTextActivity.class);
			intent.putExtra("activityId", tddActivity.getActivityId());
			intent.putExtra("nickName", tddActivity.getNickName());
			startActivity(intent);
			break;
		case R.id.activity_managerpage_activitycode:// ???????????????
			intent = new Intent(ManagerPageActivity.this, ActivityCodeActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("tddActivity", tddActivity);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		// /**
		// * ??????
		// */
		// case R.id.activity_managerpage_signup_ll:
		//
		// break;
		// /**
		// * ??????
		// */
		// case R.id.activity_managerpage_comment_ll:
		// intent = new Intent(this, MineRegistrationListActivity.class);
		// startActivity(intent);
		// break;
		// /**
		// * ??????
		// */
		 case R.id.activity_managerpage_like_ll:
		 intent = new Intent(this, LikeListActivity.class);
		 Bundle bundle3=new Bundle();
		 bundle3.putSerializable("tddactivity", tddActivity);
		 intent.putExtras(bundle3);
		 startActivity(intent);
		 break;
		/**
		 * ????????????
		 */
		// case R.id.activity_managerpage_payment_rl:
		// UiHelper.ShowOneToast(this, "?????????");
		// if (mActivity_managerpage_payment_content_ll.getVisibility() ==
		// View.VISIBLE) {
		// mActivity_managerpage_payment_line_iv.setVisibility(View.GONE);
		// mActivity_managerpage_payment_content_ll.setVisibility(View.GONE);
		// mActivity_managerpage_payment_extendable_iv.setImageDrawable(getResources().getDrawable(R.drawable.right_expansion));
		// } else if (mActivity_managerpage_payment_content_ll.getVisibility()
		// == View.GONE) {
		// mActivity_managerpage_payment_line_iv.setVisibility(View.VISIBLE);
		// mActivity_managerpage_payment_content_ll.setVisibility(View.VISIBLE);
		// mActivity_managerpage_payment_extendable_iv.setImageDrawable(getResources().getDrawable(R.drawable.down_expansion));
		// }
		// break;
		// case R.id.activity_managerpage_payment_cash_tv:
		// setPaymentColor(R.id.activity_managerpage_payment_cash_tv);
		// break;
		// case R.id.activity_managerpage_payment_online_tv:
		// setPaymentColor(R.id.activity_managerpage_payment_online_tv);
		// break;
		/**
		 * ????????????
		 */
		// case R.id.activity_managerpage_distributionmethod_rl:
		// if
		// (mActivity_managerpage_distributionmethod_content_ll.getVisibility()
		// == View.GONE) {
		// mActivity_managerpage_distributionmethod_content_ll.setVisibility(View.VISIBLE);
		// mActivity_managerpage_distributionmethod_line_iv.setVisibility(View.VISIBLE);
		// mActivity_managerpage_distributionmethod_extendable_iv.setImageDrawable(getResources().getDrawable(R.drawable.down_expansion));
		// } else if
		// (mActivity_managerpage_distributionmethod_content_ll.getVisibility()
		// == View.VISIBLE) {
		// mActivity_managerpage_distributionmethod_content_ll.setVisibility(View.GONE);
		// mActivity_managerpage_distributionmethod_line_iv.setVisibility(View.GONE);
		// mActivity_managerpage_distributionmethod_extendable_iv.setImageDrawable(getResources().getDrawable(R.drawable.right_expansion));
		// }
		// break;
		// case R.id.activity_managerpage_distributionmethod_self_tv:
		// setDistributionMethodColor(R.id.activity_managerpage_distributionmethod_self_tv);
		// break;
		// case R.id.activity_managerpage_distributionmethod_safe_tv:
		// setDistributionMethodColor(R.id.activity_managerpage_distributionmethod_safe_tv);
		// break;
		// case R.id.activity_managerpage_distributionmethod_mail_tv:
		// setDistributionMethodColor(R.id.activity_managerpage_distributionmethod_mail_tv);
		// break;
		// case R.id.activity_managerpage_share_penttv:// ??????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_invite_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_cover_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_secretary_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_originateagain_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_exportlist_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_mass_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_delete_penttv:// ????????????
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		/**
		 * ????????????
		 */
		case R.id.activity_managerpage_activitystate_ll:
		case R.id.activity_managerpage_activitystate_tv:
		case R.id.activity_managerpage_activitystate_iv:
			getStatePopWind();//???????????????popwindow
			statePopupWindow.showAsDropDown(v);
			break;
		/**
		 * ????????????
		 */
		// case R.id.activity_managerpage_publicrange_ll:
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_publicrange_tv:
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		// case R.id.activity_managerpage_publicrange_iv:
		// UiHelper.ShowOneToast(this, "?????????");
		// break;
		case R.id.head_left_iv:// ??????
			finish();
			break;
		case R.id.head_right_tv:// ??????
			UiHelper.ShowOneToast(this, "??????");
			break;
		default:
			break;
		}
	}

	/**
	 * ??????????????????popwindow
	 */
	private void getStatePopWind() {
		if (statePopupWindow != null) {
			statePopupWindow.dismiss();
			return;
		} else {
			initStatePopupWindow();
		}
	}

	/**
	 * ????????????????????????popwindow
	 */
	TextView closeApplyTextView,openApplyTextView;//?????????????????????
	private void initStatePopupWindow() {
		//TODO
		View popupWindow_view1 = this.getLayoutInflater().inflate(R.layout.activity_manager_activitystate_popwindow, null, false);
		statePopupWindow = new PopupWindow(popupWindow_view1, AppContext.getAppContext().getScreenWidth() , LayoutParams.WRAP_CONTENT, true);
		statePopupWindow.setOutsideTouchable(true);// ????????????????????????
		statePopupWindow.setTouchable(true);
		popupWindow_view1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (statePopupWindow != null && statePopupWindow.isShowing()) {
					statePopupWindow.dismiss();
					statePopupWindow = null;
				}
				return false;
			}
		});
		closeApplyTextView=(TextView) popupWindow_view1.findViewById(R.id.close_apply_tv);
		openApplyTextView = (TextView) popupWindow_view1.findViewById(R.id.open_apply_tv);
		closeApplyTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openApplyTextView.setTextColor(getResources().getColor(R.color.grey_text));
				closeApplyTextView.setTextColor(getResources().getColor(R.color.black));
				closeApplyTextView.setTextSize(18);
				openApplyTextView.setTextSize(14);
				//(activityId ??? status???????????????status??? 2 ???????????????1????????????)
				tddActivity.setStatus(1);
				mActivity_managerpage_activitystate_tv.setText("????????????");
			}
		});
		openApplyTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openApplyTextView.setTextColor(getResources().getColor(R.color.black));
				closeApplyTextView.setTextColor(getResources().getColor(R.color.grey_text));
				closeApplyTextView.setTextSize(14);
				openApplyTextView.setTextSize(18);
				tddActivity.setStatus(2);
				mActivity_managerpage_activitystate_tv.setText("????????????");
			}
		});
		// ???????????????
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		statePopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				setRegistrationState();
			}
		});
//		statePopupWindow.setFocusable(true);
		
		statePopupWindow.update();
		// ???????????? ??????
		statePopupWindow.showAtLocation(this.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ??????layout???PopupWindow??????????????????
	}

	@Override
	public void refresh() {

	}

	/**
	 * ???????????????????????????????????????
	 * 
	 * @param id
	 *            ??????id
	 */
	// private void setPaymentColor(int id) {
	// switch (id) {
	// case R.id.activity_managerpage_payment_cash_tv:// ????????????
	// mActivity_managerpage_payment_cash_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectangleredboderstyle));
	// mActivity_managerpage_payment_cash_tv.setTextColor(getResources().getColor(R.color.red));
	// mActivity_managerpage_payment_online_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_payment_online_tv.setTextColor(getResources().getColor(R.color.black));
	// break;
	// case R.id.activity_managerpage_payment_online_tv:// ????????????
	// mActivity_managerpage_payment_cash_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_payment_cash_tv.setTextColor(getResources().getColor(R.color.black));
	// mActivity_managerpage_payment_online_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectangleredboderstyle));
	// mActivity_managerpage_payment_online_tv.setTextColor(getResources().getColor(R.color.red));
	// break;
	// default:
	// break;
	// }
	// }

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param id
	 */
	// private void setDistributionMethodColor(int id) {
	// switch (id) {
	// case R.id.activity_managerpage_distributionmethod_self_tv:// ????????????
	// mActivity_managerpage_distributionmethod_self_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectangleredboderstyle));
	// mActivity_managerpage_distributionmethod_self_tv.setTextColor(getResources().getColor(R.color.red));
	// mActivity_managerpage_distributionmethod_safe_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_distributionmethod_safe_tv.setTextColor(getResources().getColor(R.color.black));
	// mActivity_managerpage_distributionmethod_mail_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_distributionmethod_mail_tv.setTextColor(getResources().getColor(R.color.black));
	// break;
	// case R.id.activity_managerpage_distributionmethod_safe_tv:// ????????????
	// mActivity_managerpage_distributionmethod_self_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_distributionmethod_self_tv.setTextColor(getResources().getColor(R.color.black));
	// mActivity_managerpage_distributionmethod_safe_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectangleredboderstyle));
	// mActivity_managerpage_distributionmethod_safe_tv.setTextColor(getResources().getColor(R.color.red));
	// mActivity_managerpage_distributionmethod_mail_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_distributionmethod_mail_tv.setTextColor(getResources().getColor(R.color.black));
	// break;
	// case R.id.activity_managerpage_distributionmethod_mail_tv:
	// mActivity_managerpage_distributionmethod_self_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_distributionmethod_self_tv.setTextColor(getResources().getColor(R.color.black));
	// mActivity_managerpage_distributionmethod_safe_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectanglegrayboderstyle));
	// mActivity_managerpage_distributionmethod_safe_tv.setTextColor(getResources().getColor(R.color.black));
	// mActivity_managerpage_distributionmethod_mail_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_rectangleredboderstyle));
	// mActivity_managerpage_distributionmethod_mail_tv.setTextColor(getResources().getColor(R.color.red));
	// break;
	// default:
	// break;
	// }
	// }

	/***
	 * ???????????????????????????
	 */
	// ?????????????????????????????????
	private void openActivityPeoples() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					OpenActivityPeoplesReq reqInfo = new OpenActivityPeoplesReq();
					TddUserCertificate tddUserCertificate = new TddUserCertificate();

					if (mActivity_managerpage_ispublishsignnum_cb.isChecked()) {
						tddActivity.setNeedPublicSigninfo(1);// ??????
					} else {
						tddActivity.setNeedPublicSigninfo(2);// ??????
					}
					tddUserCertificate.setUserId(tddActivity.getActivityId());
					tddUserCertificate.setUserName(tddActivity.getUserName());
					tddUserCertificate.setHeadimgurl(tddActivity.getHeadimgurl());
					tddUserCertificate.setNickName(tddActivity.getNickName());
					tddUserCertificate.setMobilePhone(tddActivity.getUserMobilePhone());
					// tddUserCertificate.setSex(tddActivity.gets);
					// tddUserCertificate.setEmail(tddActivity.get);
					// tddUserCertificate.setIdentity(identity);
					// tddUserCertificate.setCerStatus(cerStatus);

					reqInfo.setTddActivity(tddActivity);
					reqInfo.setTddUserCertificate(tddUserCertificate);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<OpenActivityPeoplesRes> ret = mgr.getOpenActivityPeoplesInfo(reqInfo, ManagerPageActivity.this.tddActivity.getActivityId());// ????????????
					Message message = new Message();
					message.what = ManagerpageHandle.OPENACTIVITY_PEOPLES;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						openActivityPeoplesRes = (OpenActivityPeoplesRes) ret.getObj();
						message.obj = openActivityPeoplesRes;
					} else {
						message.obj = ret.getMsg();
					}
					handler.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ??????????????????????????????
	private void onTddRecommend() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					OnTddRecommendReq reqInfo = new OnTddRecommendReq();
					TddUserCertificate tddUserCertificate = new TddUserCertificate();

					if (mActivity_managerpage_isrecommend_cb.isChecked()) {
						tddActivity.setNeedAudit(1);
					} else {
						tddActivity.setNeedAudit(2);
					}
					tddUserCertificate.setUserId(tddActivity.getActivityId());
					tddUserCertificate.setUserName(tddActivity.getUserName());
					tddUserCertificate.setHeadimgurl(tddActivity.getHeadimgurl());
					tddUserCertificate.setNickName(tddActivity.getNickName());
					tddUserCertificate.setMobilePhone(tddActivity.getUserMobilePhone());
					// tddUserCertificate.setSex(tddActivity.gets);
					// tddUserCertificate.setEmail(tddActivity.get);
					// tddUserCertificate.setIdentity(identity);
					// tddUserCertificate.setCerStatus(cerStatus);
					reqInfo.setTddActivity(tddActivity);
					reqInfo.setTddUserCertificate(tddUserCertificate);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<OnTddRecommendRes> ret = mgr.getOnTddRecommendInfo(reqInfo, tddActivity.getActivityId());// ????????????
					Message message = new Message();
					message.what = ManagerpageHandle.ONTDD_RECOMMENT;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						onTddRecommendRes = (OnTddRecommendRes) ret.getObj();
						message.obj = onTddRecommendRes;
					} else {
						message.obj = ret.getMsg();
					}
					handler.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ?????????????????????
	private void setRegistrationState() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					RegistrationStateReq reqInfo = new RegistrationStateReq();
					TddUserCertificate tddUserCertificate = new TddUserCertificate();
					
					tddUserCertificate.setUserId(tddActivity.getActivityId());
					tddUserCertificate.setUserName(tddActivity.getUserName());
					tddUserCertificate.setHeadimgurl(tddActivity.getHeadimgurl());
					tddUserCertificate.setNickName(tddActivity.getNickName());
					tddUserCertificate.setMobilePhone(tddActivity.getUserMobilePhone());
					// tddUserCertificate.setSex(tddActivity.gets);
					// tddUserCertificate.setEmail(tddActivity.get);
					// tddUserCertificate.setIdentity(identity);
					// tddUserCertificate.setCerStatus(cerStatus);
					
					reqInfo.setTddActivity(tddActivity);
					reqInfo.setTddUserCertificate(tddUserCertificate);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<RegistrationStateRes> ret = mgr.getRegistrationStateInfo(reqInfo,tddActivity.getActivityId());// ????????????
					Message message = new Message();
					message.what = ManagerpageHandle.REGISTRATION_STATE;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						registrationStateRes = (RegistrationStateRes) ret.getObj();
						message.obj = registrationStateRes;
					} else {
						message.obj = ret.getMsg();
					}
					handler.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ??????????????????
	private void setRegistrationCheck() {
		super.refresh();
		// try {
		new Thread() {
			public void run() {
				// ??????????????????request?????????????????????
				RegistrationCheckReq reqInfo = new RegistrationCheckReq();
				TddUserCertificate tddUserCertificate = new TddUserCertificate();
				// ??????????????????1.?????? 2.?????????
				if (mActivity_managerpage_isneedcheck_cb.isChecked()) {
					tddActivity.setNeedAudit(1);
				} else {
					tddActivity.setNeedAudit(2);
				}
				tddUserCertificate.setUserId(tddActivity.getActivityId());
				tddUserCertificate.setUserName(tddActivity.getUserName());
				tddUserCertificate.setHeadimgurl(tddActivity.getHeadimgurl());
				tddUserCertificate.setNickName(tddActivity.getNickName());
				tddUserCertificate.setMobilePhone(tddActivity.getUserMobilePhone());
				// tddUserCertificate.setSex(tddActivity.gets);
				// tddUserCertificate.setEmail(tddActivity.get);
				// tddUserCertificate.setIdentity(identity);
				// tddUserCertificate.setCerStatus(cerStatus);

				reqInfo.setTddActivity(tddActivity);
				reqInfo.setTddUserCertificate(tddUserCertificate);
				BaseMessageMgr mgr = new HandleNetMessageMgr();
				RetMsg<RegistrationCheckRes> ret = mgr.getRegistrationCheckInfo(reqInfo, tddActivity.getActivityId());// ????????????
				Message message = new Message();
				message.what = ManagerpageHandle.REGISTRATION_CHECK;// ?????????
				// ????????????????????? 1 ???????????????????????????
				if (ret.getCode() == 1) {
					registrationCheckRes = (RegistrationCheckRes) ret.getObj();
					message.obj = registrationCheckRes;
				} else {
					message.obj = ret.getMsg();
				}
				handler.sendMessage(message);
			};
		}.start();
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.activity_managerpage_isneedcheck_cb:
			System.out.println("??????????????????---" + isChecked);
			setRegistrationCheck();
			break;
		case R.id.activity_managerpage_ispublishsignnum_cb:
			System.out.println("??????????????????---" + isChecked);
			openActivityPeoples();
			break;
		case R.id.activity_managerpage_isnotification_cb:
			System.out.println("??????????????????----" + isChecked);
			break;
		case R.id.activity_managerpage_isrecommend_cb:
			System.out.println("?????????????????????????????????----" + isChecked);
			onTddRecommend();
			break;
		default:
			break;
		}
	}

	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {

			switch (responseId) {
			// ??????????????????????????????
			case ManagerpageHandle.OPENACTIVITY_PEOPLES:
				if (dialog != null) {
					dialog.dismiss();
				}
				openActivityPeoplesRes = (OpenActivityPeoplesRes) obj;
				if (openActivityPeoplesRes != null) {
//					UiHelper.ShowOneToast(this, "????????????????????????");
					tddActivity = openActivityPeoplesRes.getTddActivity();
				}

				break;
			// ????????????????????????
			case ManagerpageHandle.ONTDD_RECOMMENT:
				if (dialog != null) {
					dialog.dismiss();
				}
				onTddRecommendRes = (OnTddRecommendRes) obj;
				if (onTddRecommendRes != null) {
//					UiHelper.ShowOneToast(this, "????????????????????????????????????");
					tddActivity = onTddRecommendRes.getTddActivity();
				}
				break;
			// ??????????????????
			case ManagerpageHandle.REGISTRATION_CHECK:
				if (dialog != null) {
					dialog.dismiss();
				}
				registrationCheckRes = (RegistrationCheckRes) obj;
				if (registrationCheckRes != null) {
//					UiHelper.ShowOneToast(this, "??????????????????????????????");
					tddActivity = registrationCheckRes.getTddActivity();
				}
				break;
			case ManagerpageHandle.REGISTRATION_STATE://??????????????????
				if (dialog != null) {
					dialog.dismiss();
				}
				registrationStateRes = (RegistrationStateRes) obj;
				if (registrationStateRes != null) {
//					UiHelper.ShowOneToast(this, "??????????????????????????????");
					tddActivity = registrationStateRes.getTddActivity();
				}
				break;
			/*case ManagerPagerSingleActivityHandle.SINGLE_ACTIVITY://????????????????????????SignRole
				if (dialog != null) {
					dialog.dismiss();
				}
//				UiHelper.ShowOneToast(this, "??????????????????????????????");
				singleActivityRes = (SingleActivityRes) obj;
				if (singleActivityRes != null) {
					signRole = singleActivityRes.getSignRole();
					isLeader = singleActivityRes.getIsLeader();
					if(signRole == 2){
						isTD_ll.setVisibility(View.GONE);
						botttom_ll.setVisibility(View.GONE);
						mActivity_managerpage_detail.setDrawableTop(getResources().getDrawable(R.drawable.manager_activitydetail_gray));
						mActivity_managerpage_detail.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_detail.getDrawableTop(), null, null);
						mActivity_managerpage_detail.setTextColor(getResources().getColor(R.color.textgray));
						mActivity_managerpage_detail.setCompoundDrawablePadding(3);
						
						mActivity_managerpage_detail.setClickable(false);
						
						mActivity_managerpage_edit.setDrawableTop(getResources().getDrawable(R.drawable.manager_activityedit_gray));
						mActivity_managerpage_edit.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_edit.getDrawableTop(), null, null);
						mActivity_managerpage_edit.setTextColor(getResources().getColor(R.color.textgray));
						mActivity_managerpage_edit.setCompoundDrawablePadding(3);
						mActivity_managerpage_edit.setClickable(false);
						
						setData();
					}else
					if (isLeader.equals("true")) {
						isTD_ll.setVisibility(View.VISIBLE);
						botttom_ll.setVisibility(View.VISIBLE);
						
						mActivity_managerpage_detail.setDrawableTop(getResources().getDrawable(R.drawable.manager_activitydetail));
						mActivity_managerpage_detail.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_detail.getDrawableTop(), null, null);
						mActivity_managerpage_detail.setClickable(true);
						mActivity_managerpage_detail.setCompoundDrawablePadding(3);
						
						mActivity_managerpage_edit.setDrawableTop(getResources().getDrawable(R.drawable.manager_activityedit));
						mActivity_managerpage_edit.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_edit.getDrawableTop(), null, null);
						mActivity_managerpage_edit.setClickable(true);
						mActivity_managerpage_edit.setCompoundDrawablePadding(3);
						
						setData();
					}
					
					//????????????????????????????????????
					updateData(singleActivityRes);
				break;
				}*/
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	/**
	 * ?????????????????????????????????
	 */
	private void initViewData(){
		if (singleActivityRes != null) {
			signRole = singleActivityRes.getSignRole();
			isLeader = singleActivityRes.getIsLeader();
			if(signRole == 2){
				isTD_ll.setVisibility(View.GONE);
				botttom_ll.setVisibility(View.GONE);
				mActivity_managerpage_detail.setDrawableTop(getResources().getDrawable(R.drawable.manager_activitydetail_gray));
				mActivity_managerpage_detail.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_detail.getDrawableTop(), null, null);
				mActivity_managerpage_detail.setTextColor(getResources().getColor(R.color.textgray));
				mActivity_managerpage_detail.setCompoundDrawablePadding(3);
				
				mActivity_managerpage_detail.setClickable(false);
				
				mActivity_managerpage_edit.setDrawableTop(getResources().getDrawable(R.drawable.manager_activityedit_gray));
				mActivity_managerpage_edit.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_edit.getDrawableTop(), null, null);
				mActivity_managerpage_edit.setTextColor(getResources().getColor(R.color.textgray));
				mActivity_managerpage_edit.setCompoundDrawablePadding(3);
				mActivity_managerpage_edit.setClickable(false);
				
				setData();
			}else
			if (isLeader.equals("true")) {
				isTD_ll.setVisibility(View.VISIBLE);
				botttom_ll.setVisibility(View.VISIBLE);
				
				mActivity_managerpage_detail.setDrawableTop(getResources().getDrawable(R.drawable.manager_activitydetail));
				mActivity_managerpage_detail.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_detail.getDrawableTop(), null, null);
				mActivity_managerpage_detail.setClickable(true);
				mActivity_managerpage_detail.setCompoundDrawablePadding(3);
				
				mActivity_managerpage_edit.setDrawableTop(getResources().getDrawable(R.drawable.manager_activityedit));
				mActivity_managerpage_edit.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_edit.getDrawableTop(), null, null);
				mActivity_managerpage_edit.setClickable(true);
				mActivity_managerpage_edit.setCompoundDrawablePadding(3);
				
				setData();
			}
			
			//????????????????????????????????????
			updateData(singleActivityRes);
		}
	}
	private void setData(){
		mActivity_managerpage_signlist.setDrawableTop(getResources().getDrawable(R.drawable.manager_signlist));
		mActivity_managerpage_signlist.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_signlist.getDrawableTop(), null, null);
		mActivity_managerpage_signlist.setClickable(true);
		mActivity_managerpage_signlist.setCompoundDrawablePadding(3);
		
		mActivity_managerpage_share.setDrawableTop(getResources().getDrawable(R.drawable.manager_share));
		mActivity_managerpage_share.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_share.getDrawableTop(), null, null);
		mActivity_managerpage_share.setClickable(true);
		mActivity_managerpage_share.setCompoundDrawablePadding(3);
		
		mActivity_managerpage_groupsend.setDrawableTop(getResources().getDrawable(R.drawable.manager_groupsend));
		mActivity_managerpage_groupsend.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_groupsend.getDrawableTop(), null, null);
		mActivity_managerpage_groupsend.setClickable(true);
		mActivity_managerpage_groupsend.setCompoundDrawablePadding(3);
		
		mActivity_managerpage_activitycode.setDrawableTop(getResources().getDrawable(R.drawable.manager_activitycode));
		mActivity_managerpage_activitycode.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_managerpage_activitycode.getDrawableTop(), null, null);
		mActivity_managerpage_activitycode.setClickable(true);
		mActivity_managerpage_activitycode.setCompoundDrawablePadding(3);
	}
	
	/**???????????????????????????????????????????????????
	 * @param singleActivityRes2 
	 * 
	 */
	private void updateData(SingleActivityRes singleActivityRes2){
		if(singleActivityRes2.getTddActivity().getSignCount()!=0){
			mActivity_managerpage_signup_content_tv.setText(singleActivityRes2.getTddActivity().getSignCount()+"");
		}else{
			mActivity_managerpage_signup_content_tv.setText("0");
		}
		if(singleActivityRes2.getTddActivity().getCommentCount()!=0){
			mActivity_managerpage_comment_content_tv.setText(singleActivityRes2.getTddActivity().getCommentCount()+"");
		}else {
			mActivity_managerpage_comment_content_tv.setText("0");
		}
		if(singleActivityRes2.getTddActivity().getLikeCount()!=0){
			mActivity_managerpage_like_content_tv.setText(singleActivityRes2.getTddActivity().getLikeCount()+"");
		}else {
			mActivity_managerpage_like_content_tv.setText("0");
		}
		if(singleActivityRes2.getTddActivity().getViewCount()!=0){
			mActivity_managerpage_read_content_tv.setText(singleActivityRes2.getTddActivity().getViewCount()+"");
		}else {
			mActivity_managerpage_read_content_tv.setText("0");
		}
		
	}
	
	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(this, mess);
	}

	/**
	 * ?????????????????????
	 */
	public void friend(View v) {
		share(0);
	}

	public void friendline(View v) {
		share(1);
	}

	private void share(int flag) {
		downloadWeiXinImg(flag);

	}

	// ?????????????????? ??????????????????????????????????????????????????????
	private void downloadWeiXinImg(final int flag) {
		if (tddActivity.getShareContent() != null && tddActivity.getShareImg() != null && tddActivity.getShareUrl() != null) {

			ImageLoader.getInstance().loadImage(tddActivity.getShareImg(), new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {

				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// ????????????
					WXWebpageObject webpage = new WXWebpageObject();
					webpage.webpageUrl = tddActivity.getShareUrl();
					WXMediaMessage msg = new WXMediaMessage(webpage);
					msg.title = tddActivity.getActivityTitle();
					msg.description = tddActivity.getActivityDescription();
					// ??????ImgUrl?????????????????????????????????bitmap??????
					// ????????????????????????????????????????????????
					Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
					msg.setThumbImage(thumb);
					SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("webpage");
					req.message = msg;
					req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
					boolean fla = wxApi.sendReq(req);
					System.out.println("fla=" + fla);
				}

				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
					// ?????????????????????
					WXWebpageObject webpage = new WXWebpageObject();
					webpage.webpageUrl = tddActivity.getShareUrl();
					WXMediaMessage msg = new WXMediaMessage(webpage);
					msg.title = tddActivity.getActivityTitle();
					msg.description = tddActivity.getActivityDescription();
					// ??????ImgUrl?????????????????????????????????bitmap??????
					// ????????????????????????????????????????????????
					Bitmap thumb = bitmap;
					msg.setThumbImage(thumb);
					SendMessageToWX.Req req = new SendMessageToWX.Req();
					req.transaction = buildTransaction("webpage");
					req.message = msg;
					req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
					boolean fla = wxApi.sendReq(req);
					System.out.println("fla=" + fla);
				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {

				}
			});
		} else {
			UiHelper.ShowOneToast(ManagerPageActivity.this, "?????????????????????????????????????????????");
			finish();
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	@Override
	public void onReq(BaseReq arg0) {

	}

	@Override
	public void onResp(BaseResp arg0) {
		finish();
	}

	private void onClickShareToQQ() {
		Bundle b = getShareBundle();
		if (b != null) {
			shareParams = b;
			Thread thread = new Thread(shareThread);
			thread.start();
		}
	}

	private Bundle getShareBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("title", tddActivity.getActivityTitle());
		bundle.putString("imageUrl", tddActivity.getShareImg());
		bundle.putString("targetUrl", tddActivity.getShareUrl());
		bundle.putString("summary", tddActivity.getActivityDescription());
		bundle.putString("site", "1104957952");
		bundle.putString("appName", "??????TDD");
		return bundle;
	}

	Bundle shareParams = null;

	Handler shareHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		}
	};

	// ????????????????????????????????????????????????????????????
	Runnable shareThread = new Runnable() {

		public void run() {
			doShareToQQ(shareParams);
			Message msg = shareHandler.obtainMessage();

			// ???Message?????????????????????????????????
			shareHandler.sendMessage(msg);

		}
	};

	private void doShareToQQ(Bundle params) {
		mTencent.shareToQQ(ManagerPageActivity.this, params, new BaseUiListener() {
			protected void doComplete(JSONObject values) {
				showResult("shareToQQ:", "onComplete");
			}

			@Override
			public void onError(UiError e) {
				showResult("shareToQQ:", "????????????QQ");
			}

			@Override
			public void onCancel() {
				showResult("shareToQQ", "onCancel");
			}
		});
	}

	private class BaseUiListener implements IUiListener {

		// @Override
		// public void onComplete(JSONObject response) {
		// // mBaseMessageText.setText("onComplete:");
		// // mMessageText.setText(response.toString());
		// doComplete(response);
		// }

		protected void doComplete(Object values) {

		}

		@Override
		public void onError(UiError e) {
			showResult("onError:", "code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}

		@Override
		public void onComplete(Object arg0) {
			doComplete(arg0);
		}
	}

	private Handler mHandler;

	// qq?????????????????????
	private void showResult(final String base, final String msg) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// finish();//??????

				UiHelper.ShowOneToast(ManagerPageActivity.this, msg);
				popupWindow.dismiss();
				finish();
			}
		});
	}

	/**
	 * ????????????
	 */
	private void getPopWindow() {
		if (popupWindow != null) {
			popupWindow.dismiss();
			return;
		} else {
			initPopupWindow();
		}
	}

	/**
	 * ?????????????????????
	 */
	private void initPopupWindow() {
		View popupWindow_view = this.getLayoutInflater().inflate(R.layout.activity_originate_chair_popwindow, null, false);
		popupWindow = new PopupWindow(popupWindow_view, AppContext.getAppContext().getScreenWidth() * 4 / 5, LayoutParams.WRAP_CONTENT, true);
		popupWindow_view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
		PengTextView weixinFriend, weixinZone, qqFriend;// ????????????????????????
		weixinFriend = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin);
		weixinZone = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin_zone);
		qqFriend = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_qq);
		// ??????????????????
		weixinFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				friend(v);
			}
		});
		// ?????????????????????
		weixinZone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				friendline(v);
			}
		});
		// qq????????????
		qqFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickShareToQQ();
			}
		});
		// ???????????????
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		// ???????????? ??????
		popupWindow.showAtLocation(this.findViewById(R.id.layout), Gravity.CENTER, 0, 0);//
	}
	
	
	/**
	 * ?????????????????????????????????activity
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle=intent.getExtras();
			if((TddActivity) bundle.getSerializable("tddactivity")!=null)
			tddActivity = (TddActivity) bundle.getSerializable("tddactivity");
			initData();

		}
	};

	
	@Override
	public void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		unregisterReceiver(broadcastReceiver2);
		super.onDestroy();
	}

	/**??????????????????????????????????????????????????????????????????
	 * 
	 */
	BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			/*Bundle bundle = intent.getExtras();
			judgeRole((String)bundle.getSerializable("activityId"));*/
//			judgeRole(tddActivity.getActivityId());
		}
	};
	
}
