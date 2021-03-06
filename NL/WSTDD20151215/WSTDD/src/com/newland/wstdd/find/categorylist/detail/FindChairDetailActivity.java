package com.newland.wstdd.find.categorylist.detail;

import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.find.categorylist.detail.bean.CollectReq;
import com.newland.wstdd.find.categorylist.detail.bean.CollectRes;
import com.newland.wstdd.find.categorylist.detail.bean.IsLikeAndCollectReq;
import com.newland.wstdd.find.categorylist.detail.bean.IsLikeAndCollectRes;
import com.newland.wstdd.find.categorylist.detail.bean.LikeReq;
import com.newland.wstdd.find.categorylist.detail.bean.LikeRes;
import com.newland.wstdd.find.categorylist.detail.handle.SingleActivityDetailHandle;
import com.newland.wstdd.find.categorylist.registrationedit.editregistration.EditRegistrationEditActivity;
import com.newland.wstdd.find.categorylist.registrationedit.registration.RegistrationSubmitActivity;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
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
 * ??????-- 8????????????????????????????????????????????????
 * 
 * @author H81 2015-11-6
 * 
 */
public class FindChairDetailActivity extends BaseFragmentActivity implements OnClickListener, OnPostListenerInterface, IWXAPIEventHandler {
	/**
	 * ???????????????
	 */
	
	// ??????
	private static final String appid = "wx1b84c30d9f380c89";// ?????????appid
	private IWXAPI wxApi;// ?????????API
	// QQ
	private Tencent mTencent;
	private static final String APP_ID = "1104957952";
	// // ??????
	// private LinearLayout mHor_lin;
	// private LinearLayout mLl_detail;
	// private TextView mTv_detail;
	// private ImageView mIv_detail;
	private PopupWindow popupWindow;
	// // ??????
	// private LinearLayout mLl_discuss;
	// private TextView mTv_discuss;
	// private ImageView mIv_discuss;
	// private ViewPager mViewPager;
	// private List<BaseFragment> listFragments;
	// private BaseFragment currentFragment;// ???????????????Fragment
	// FindChairDetailFragment findChairDetailFragment;// ??????-????????????-??????
	// FindChairDetailFragment findChairDetailFragment1;// ??????-????????????-??????
	private AppContext appContext;
	private RelativeLayout mLayout;
	private TextView mActivity_find_apply_title_tv;
	private TextView mActivity_find_apply_originatename_tv;
	private TextView mActivity_find_apply_originatetime_tv;
	private ImageView mActivity_find_apply_img_iv;
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_chairtime_ptv;//??????
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_chairaddress_ptv;//??????
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_chairsigncount_ptv;//??????
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_chairsign_limitcount_ptv;//????????????
	
	private TextView mActivity_find_chairdetail_detail_tv;
	private ImageView[] mActivity_find_chairdetail_detail_iv = new ImageView[7];
	private TextView mActivity_find_chairdetail_detail_readingquantity_tv;
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_td;
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_collect;
	private com.newland.wstdd.common.widget.PengTextView mActivity_find_like;
	private Button mActivity_find_register_btn;// ????????????
	ImageButton rightBtn;//??????

	int maxImgWidth;
	int maxImgHeight;

	// ????????????????????????
	private IsLikeAndCollectRes isLikeAndCollectRes;
	private IsLikeAndCollectHandle isLikeAndCollectHandle = new IsLikeAndCollectHandle(this);
	private String isCollectString;// ????????????
	private String isLikeString;// ????????????

	// ??????
	private CollectRes collectRes;
	private String collectType = "1";// ?????????0 ?????? 1 ?????????
	private CollectHandle collecthandler = new CollectHandle(this);
	private String whichQuest;// ??????????????? ?????????/??????/??????
	// ??????
	private LikeRes likeRes;
	private String likeType = "1";// ?????????0 ?????? 1 ?????????
	private LikeHandle likehandler = new LikeHandle(this);

	/** --------------???????????? ------------- */
	// 8????????????????????????
	TddActivity tddActivity;

	/** --------------???????????? ------------- */
	
	/**
	 * ???????????????????????????????????????????????????????????????   ?????????????????????????????????
	 */
	private IntentFilter filter;// ??????????????????????????????
	//???????????????
	SingleActivityRes singleActivityRes;//????????????????????????
	SingleActivityDetailHandle handler=new SingleActivityDetailHandle(this);
	
	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext=AppContext.getAppContext();
		getIntentData();//??????????????????????????? tddactivity
		setContentView(R.layout.activity_find_chairdetail);
		initView();
		/**
		 * ??????
		 */
		// QQ
		final Context ctxContext = this.getApplicationContext();
		mTencent = Tencent.createInstance(APP_ID, ctxContext);
		mHandler = new Handler();
		// weixin
		wxApi = WXAPIFactory.createWXAPI(this, appid);
		wxApi.registerApp(appid);
		// initData();
		// initFragment();
		// ????????????
		filter = new IntentFilter("Refresh_FindChairDetailActivity");//??????????????????????????????????????????????????????tddactivity
		registerReceiver(broadcastReceiver, filter);// ?????????broadcastReceiver??????????????????????????????
		setTitle();//???????????????
		whichQuest = "isLikeAndCollect";
		refresh();
	}

	/**
	 * ??????????????????   ????????????   ??????????????????   ?????????????????? ??????
	 */
	private void singleActivitySearch() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					SingleActivityReq reqInfo = new SingleActivityReq();
					reqInfo.setActivityId(tddActivity.getActivityId());
				
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SingleActivityRes> ret = mgr.getSingleActivityInfo(reqInfo);// ????????????																
					Message message = new Message();
					message.what = SingleActivityDetailHandle.SINGLE_ACTIVITY;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						singleActivityRes = (SingleActivityRes) ret.getObj();
						message.obj = singleActivityRes;
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

	private void getIntentData() {
		Intent intent = getIntent();
		tddActivity = (TddActivity) intent.getSerializableExtra("tddActivity");
	}

	/**
	 * ????????????
	 */
	private void setTitle() {
		ImageView leftBtn = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		rightBtn = (ImageButton) findViewById(R.id.head_right_btn);
		leftBtn.setVisibility(View.VISIBLE);
		centerTitle.setText(StringUtil.intType2Str(tddActivity.getActivityType()) + "??????");
		// rightTv.setText("??????");
		rightTv.setVisibility(View.GONE);
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setImageDrawable(getResources().getDrawable(R.drawable.test_share));
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
	}

	public void initView() {
		// mHor_lin = (LinearLayout) findViewById(R.id.hor_lin);
		// mLl_detail = (LinearLayout) findViewById(R.id.ll_detail);
		// mTv_detail = (TextView) findViewById(R.id.tv_detail);
		// mIv_detail = (ImageView) findViewById(R.id.iv_detail);
		// mLl_discuss = (LinearLayout) findViewById(R.id.ll_discuss);
		// mTv_discuss = (TextView) findViewById(R.id.tv_discuss);
		// mIv_discuss = (ImageView) findViewById(R.id.iv_discuss);
		// mViewPager = (ViewPager) findViewById(R.id.mViewPager);



		mActivity_find_apply_title_tv = (TextView) findViewById(R.id.activity_find_apply_title_tv);
		mActivity_find_apply_originatename_tv = (TextView) findViewById(R.id.activity_find_apply_originatename_tv);
		mActivity_find_apply_originatetime_tv = (TextView) findViewById(R.id.activity_find_apply_originatetime_tv);
		mActivity_find_apply_img_iv = (ImageView) findViewById(R.id.activity_find_apply_img_iv);
		mActivity_find_chairtime_ptv = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_find_chairtime_ptv);
		mActivity_find_chairaddress_ptv = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_find_chairaddress_ptv);
		mActivity_find_chairsigncount_ptv = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_find_chairsigncount_ptv);
		mActivity_find_chairsign_limitcount_ptv = (PengTextView) findViewById(R.id.activity_find_chairsign_limitcount_ptv);
		mActivity_find_chairdetail_detail_tv = (TextView) findViewById(R.id.activity_find_chairdetail_detail_tv);
		mActivity_find_chairdetail_detail_iv[0] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv1);
		mActivity_find_chairdetail_detail_iv[1] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv2);
		mActivity_find_chairdetail_detail_iv[2] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv3);
		mActivity_find_chairdetail_detail_iv[3] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv4);
		mActivity_find_chairdetail_detail_iv[4] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv5);
		mActivity_find_chairdetail_detail_iv[5] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv6);
		mActivity_find_chairdetail_detail_iv[6] = (ImageView) findViewById(R.id.activity_find_chairdetail_detail_iv7);
		mActivity_find_chairdetail_detail_readingquantity_tv = (TextView) findViewById(R.id.activity_find_chairdetail_detail_readingquantity_tv);
		mActivity_find_td = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_find_td);
		mActivity_find_collect = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_find_collect);
		mActivity_find_like = (com.newland.wstdd.common.widget.PengTextView) findViewById(R.id.activity_find_like);

		mActivity_find_register_btn = (Button) findViewById(R.id.activity_find_register_btn);

		mActivity_find_collect.setOnClickListener(this);
		mActivity_find_like.setOnClickListener(this);
		mActivity_find_register_btn.setOnClickListener(this);
		
	
		initActivityData();
		initDetailData();
	}

	/**
	 * ?????????????????????
	 * 
	 */
	private void initActivityData() {
		mActivity_find_apply_title_tv.setText(StringUtil.noNull(tddActivity.getActivityTitle()));
		mActivity_find_apply_originatename_tv.setText(StringUtil.noNull(tddActivity.getSponsor()));
		mActivity_find_apply_originatetime_tv.setText(StringUtil.noNull(tddActivity.getFriendActivityTime()));
		if (StringUtil.isNotEmpty(tddActivity.getImage1())) {
		
			ImageDownLoad.getDownLoadImg(tddActivity.getImage1(), mActivity_find_apply_img_iv);
		}
		mActivity_find_chairtime_ptv.setText(StringUtil.noNull(tddActivity.getActivityTime()));
		mActivity_find_chairaddress_ptv.setText(StringUtil.noNull(tddActivity.getActivityAddress()));
		mActivity_find_chairsigncount_ptv.setText(StringUtil.noNull("??????"+tddActivity.getSignCount()+"???"));
		mActivity_find_chairsign_limitcount_ptv.setText(StringUtil.noNull("("+tddActivity.getLimitPerson())+")");
	}

	/**
	 * ????????? ?????????????????????
	 */
	private void initIsLikeAndCollectData() {
		// TODO
		mActivity_find_td.setTextColor(getResources().getColor(R.color.textgray));
		mActivity_find_td.setDrawableTop(getResources().getDrawable(R.drawable.test_detailphone));
		mActivity_find_td.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_td.getDrawableTop(), null, null);
		mActivity_find_td.invalidate();
		if (isCollectString.equals("1")) {// 1 ????????? 0?????????
			mActivity_find_collect.setTextColor(getResources().getColor(R.color.red));
			mActivity_find_collect.setDrawableTop(getResources().getDrawable(R.drawable.test_detailcollect_red));
			mActivity_find_collect.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_collect.getDrawableTop(), null, null);
			mActivity_find_collect.invalidate();
		} else if (isCollectString.equals("0")) {
			mActivity_find_collect.setTextColor(getResources().getColor(R.color.textgray));
			mActivity_find_collect.setDrawableTop(getResources().getDrawable(R.drawable.test_detailcollect));
			mActivity_find_collect.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_collect.getDrawableTop(), null, null);
		}
		if (isLikeString.equals("1")) {// 1 ????????? 0?????????
			mActivity_find_like.setTextColor(getResources().getColor(R.color.red));
			mActivity_find_like.setDrawableTop(getResources().getDrawable(R.drawable.test_detaillike_red));
			mActivity_find_like.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_like.getDrawableTop(), null, null);
		} else if (isLikeString.equals("0")) {
			mActivity_find_like.setTextColor(getResources().getColor(R.color.textgray));
			mActivity_find_like.setDrawableTop(getResources().getDrawable(R.drawable.test_detaillike));
			mActivity_find_like.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_like.getDrawableTop(), null, null);
			mActivity_find_like.invalidate();
		}
	}

	/**
	 * ????????????????????????????????????,????????????,??????ArrayList<String>??????
	 * 
	 * @param imgUrls
	 * @return
	 */
	private String[] getImageList(String imgUrls) {
		String[] strs = imgUrls.split(",");
		return strs;
	}

	/**
	 * ?????????????????????
	 */
	private void initDetailData() {
		String[] imgs = null;
		if (StringUtil.isNotEmpty(tddActivity.getImage())) {
			imgs = getImageList(tddActivity.getImage());
		}
		mActivity_find_chairdetail_detail_tv.setText(StringUtil.noNull(tddActivity.getActivityTitle()));
		mActivity_find_chairdetail_detail_readingquantity_tv.setText("?????? " + tddActivity.getViewCount());

		int i = 0;
		try {
			if (imgs != null) {
				for (; i < mActivity_find_chairdetail_detail_iv.length; i++) {
					if (StringUtil.isNotEmpty(imgs[i])) {
						ImageDownLoad.getDownLoadImg(imgs[i], mActivity_find_chairdetail_detail_iv[i]);
						mActivity_find_chairdetail_detail_iv[i].setVisibility(View.VISIBLE);
					}
				}
			}
		} catch (Exception e) {
			return;
		} finally {
			for (; i < mActivity_find_chairdetail_detail_iv.length; i++) {
				mActivity_find_chairdetail_detail_iv[i].setVisibility(View.GONE);
			}
		}

	}

	/**
	 * ?????????viewpager??????
	 */
	// private void initData() {
	// mIv_detail.setVisibility(View.VISIBLE);
	// mIv_discuss.setVisibility(View.INVISIBLE);
	//
	// mTv_detail.setTextColor(this.getResources().getColor(R.color.originate_darkgreen));
	// mTv_discuss.setTextColor(this.getResources().getColor(R.color.black));
	// }

	/**
	 * ?????????Fragment
	 */
	// private void initFragment() {
	// listFragments = new ArrayList<BaseFragment>();
	// findChairDetailFragment =
	// FindChairDetailFragment.newInstance(FindChairDetailActivity.this);
	// findChairDetailFragment1 =
	// FindChairDetailFragment.newInstance(FindChairDetailActivity.this);
	//
	// listFragments.add(findChairDetailFragment);
	// listFragments.add(findChairDetailFragment1);
	//
	// BaseFragmentPagerAdapter mAdapetr = new
	// BaseFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
	// mViewPager.setAdapter(mAdapetr);
	// mViewPager.setOnPageChangeListener(pageListener);
	// mViewPager.setOffscreenPageLimit(2);
	// currentFragment = findChairDetailFragment;
	// }

	/**
	 * ViewPager??????????????????
	 * */
	// public OnPageChangeListener pageListener = new OnPageChangeListener() {
	//
	// public void onPageScrollStateChanged(int arg0) {
	// }
	//
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	// }
	//
	// public void onPageSelected(int position) {
	// clearPress();
	// mViewPager.setCurrentItem(position);
	// currentFragment = listFragments.get(position);
	// switch (position) {
	// case 0:
	// mIv_detail.setVisibility(View.VISIBLE);
	// mTv_detail.setTextColor(FindChairDetailActivity.this.getResources().getColor(R.color.originate_darkgreen));
	// UiHelper.ShowOneToast(FindChairDetailActivity.this, "0");
	//
	// break;
	// case 1:
	// mIv_discuss.setVisibility(View.VISIBLE);
	// mTv_discuss.setTextColor(FindChairDetailActivity.this.getResources().getColor(R.color.originate_darkgreen));
	// UiHelper.ShowOneToast(FindChairDetailActivity.this, "1");
	// break;
	// default:
	// break;
	// }
	// selectTab(position);
	// }
	// };

	// private void selectTab(int tab_postion) {
	// for (int i = 0; i < mHor_lin.getChildCount(); i++) {
	// View checkView = mHor_lin.getChildAt(tab_postion);
	// int k = checkView.getMeasuredWidth();
	// int l = checkView.getLeft();
	// }
	// }

	// private void clearPress() {
	//
	// mIv_detail.setVisibility(View.INVISIBLE);
	// mIv_discuss.setVisibility(View.INVISIBLE);
	//
	// mTv_detail.setTextColor(this.getResources().getColor(R.color.black));
	// mTv_discuss.setTextColor(this.getResources().getColor(R.color.black));
	// }

	private void getPopWindow() {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
		} else {
			initPopupWindow();
		}
	}

	protected void initPopupWindow() {
		TextView weixinShareTv,qqShareTv;//??????  qq??????
		TextView cancelTextView;//??????  ???????????????
		View popupWindow_view = this.getLayoutInflater().inflate(R.layout.activity_find_chairdetail_popwindow, null, false);

		popupWindow = new PopupWindow(popupWindow_view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

//		popupWindow_view.setOnTouchListener(new OnTouchListener() {
//
//			public boolean onTouch(View v, MotionEvent event) {
//				if (popupWindow != null && popupWindow.isShowing()) {
//					popupWindow.dismiss();
//					popupWindow = null;
//				}
//				return false;
//			}
//		});

		weixinShareTv = (TextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin);
		qqShareTv = (TextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_qq);
		cancelTextView = (TextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_cancel);
		cancelTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		//????????????
		weixinShareTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				friend(v);
			}
		});
		//qq??????
		qqShareTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickShareToQQ();
			}
		});
		// ???????????????
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);

			}
		});
		
		// ????????????
		popupWindow.showAtLocation(this.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ??????layout???PopupWindow??????????????????

	}
	//????????????
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_left_iv:// ??????
			sendBroadToManagerPageActivity();
			finish();
			break;
		case R.id.head_right_btn:
			getPopWindow();
			popupWindow.showAsDropDown(v);
			break;
		// case R.id.ll_detail:// ??????
		// mIv_detail.setVisibility(View.VISIBLE);
		// mViewPager.setCurrentItem(0);
		// break;
		// case R.id.ll_discuss:// ??????
		// mIv_discuss.setVisibility(View.VISIBLE);
		// mViewPager.setCurrentItem(1);
		// break;

		case R.id.activity_find_collect:// ??????
			if("".equals(appContext.getIsLogin())||"false".equals(appContext.getIsLogin())){
				UiHelper.ShowOneToast(FindChairDetailActivity.this, "?????????????????????????????????");
			}else if("true".equals(appContext.getIsLogin())){
			
			whichQuest = "collect";
			refresh();
			}
			break;
		case R.id.activity_find_like:// ??????
			if("".equals(appContext.getIsLogin())||"false".equals(appContext.getIsLogin())){
				UiHelper.ShowOneToast(FindChairDetailActivity.this, "?????????????????????????????????");
			}else if("true".equals(appContext.getIsLogin())){
			UiHelper.ShowOneToast(FindChairDetailActivity.this, "??????");
			whichQuest = "like";
			refresh();
			}
			break;
		case R.id.activity_find_register_btn:// ????????????
			if("".equals(appContext.getIsLogin())||"false".equals(appContext.getIsLogin())){
				UiHelper.ShowOneToast(FindChairDetailActivity.this, "?????????????????????????????????");
			}else if("true".equals(appContext.getIsLogin())){
				if("?????????".equals(mActivity_find_register_btn.getText().toString())){
						//??????????????????????????????????????????????????????????????????????????????
				Intent intent = new Intent(FindChairDetailActivity.this,EditRegistrationEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("singleActivity", singleActivityRes);
				intent.putExtras(bundle);
				startActivity(intent);	
				
				}else {
			Intent intent = new Intent(FindChairDetailActivity.this, RegistrationSubmitActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("tddActivity", tddActivity);
			intent.putExtras(bundle);
			startActivity(intent);
				}
			
			
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void refresh() {
		// targetId ??????????????????Id type ?????????0 ?????? 1 ????????? target_title ????????????????????????
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					if (whichQuest.equals("isLikeAndCollect")) {// ????????????????????????
						IsLikeAndCollectReq isLikeAndCollectReq = new IsLikeAndCollectReq();
						isLikeAndCollectReq.setTargetId(tddActivity.getActivityId());
						BaseMessageMgr mgr = new HandleNetMessageMgr();
						RetMsg<IsLikeAndCollectRes> ret = mgr.getIsLikeAndCollectInfo(isLikeAndCollectReq);
						Message message = new Message();
						message.what = IsLikeAndCollectHandle.ISLIKEANDCOLLECT;
						// ?????????????????????1???????????????????????????
						if (ret.getCode() == 1) {
							isLikeAndCollectRes = (IsLikeAndCollectRes) ret.getObj();
							message.obj = isLikeAndCollectRes;
						} else {
							message.obj = ret.getMsg();
						}
						isLikeAndCollectHandle.sendMessage(message);
					} else if (whichQuest.equals("collect")) {// ????????????
						CollectReq collectReq = new CollectReq();
						// collectReq.setTargetId(targetId);
						// collectReq.setTarget_title(target_title);
						collectReq.setTargetId(tddActivity.getActivityId());
						collectReq.setTarget_title(tddActivity.getActivityTitle());
						// if (collectType.equals("0")) {
						// collectType = "1";
						// } else if (collectType.equals("1")) {
						// collectType = "0";
						// }
						collectType = "0";
						collectReq.setType(collectType);

						BaseMessageMgr mgr = new HandleNetMessageMgr();
						RetMsg<CollectRes> ret = mgr.getCollectInfo(collectReq);// ????????????
						Message message = new Message();
						message.what = CollectHandle.COLLECT;// ?????????
						// ????????????????????? 1 ???????????????????????????
						if (ret.getCode() == 1) {
							collectRes = (CollectRes) ret.getObj();
							message.obj = collectRes;
						} else {
							message.obj = ret.getMsg();
						}
						collecthandler.sendMessage(message);
					} else if (whichQuest.equals("like")) {// ????????????
						LikeReq likeReq = new LikeReq();
						// collectReq.setTargetId(targetId);
						// collectReq.setTarget_title(target_title);
						likeReq.setTargetId(tddActivity.getActivityId());
						// if (likeType.equals("0")) {
						// likeType = "1";
						// } else if (likeType.equals("1")) {
						// likeType = "0";
						// }
						likeType = "0";
						likeReq.setType(likeType);

						BaseMessageMgr mgr = new HandleNetMessageMgr();
						RetMsg<LikeRes> ret = mgr.getLikeInfo(likeReq);// ????????????
						Message message = new Message();
						message.what = LikeHandle.LIKE;// ?????????
						// ????????????????????? 1 ???????????????????????????
						if (ret.getCode() == 1) {
							likeRes = (LikeRes) ret.getObj();
							message.obj = likeRes;
						} else {
							message.obj = ret.getMsg();
						}
						likehandler.sendMessage(message);
					}
				}
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		switch (responseId) {
		case IsLikeAndCollectHandle.ISLIKEANDCOLLECT:
			if (dialog != null) {
				dialog.dismiss();
			}
			isLikeAndCollectRes = (IsLikeAndCollectRes) obj;
			if (isLikeAndCollectRes != null) {
				/**
				 * ??????????????????
				 */
//				UiHelper.ShowOneToast(this, "??????????????????");
				isCollectString = isLikeAndCollectRes.getIsCollect();
				isLikeString = isLikeAndCollectRes.getIsLike();
				initIsLikeAndCollectData();
			}
			break;
		case CollectHandle.COLLECT:
			if (dialog != null) {
				dialog.dismiss();
			}
			collectRes = (CollectRes) obj;
			if (collectRes != null) {
				/**
				 * ??????????????????
				 */
//				UiHelper.ShowOneToast(this, "??????????????????");
				if (collectRes.getBack().equals("0")) {// 0 ???????????? 1????????????
					mActivity_find_collect.setTextColor(getResources().getColor(R.color.red));
					mActivity_find_collect.setDrawableTop(getResources().getDrawable(R.drawable.test_detailcollect_red));
					mActivity_find_collect.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_collect.getDrawableTop(), null, null);
				} else if (collectRes.getBack().equals("1")) {
					mActivity_find_collect.setTextColor(getResources().getColor(R.color.textgray));
					mActivity_find_collect.setDrawableTop(getResources().getDrawable(R.drawable.test_detailcollect));
					mActivity_find_collect.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_collect.getDrawableTop(), null, null);
				}
				mActivity_find_collect.invalidate();
			}
			break;
		case LikeHandle.LIKE:
			if (dialog != null) {
				dialog.dismiss();
			}
			likeRes = (LikeRes) obj;
			if (likeRes != null) {
				/**
				 * ??????????????????
				 */
//				UiHelper.ShowOneToast(this, "??????????????????");
				if (likeRes.getBack().equals("0")) {// 0 ???????????? 1????????????
					mActivity_find_like.setTextColor(getResources().getColor(R.color.red));
					mActivity_find_like.setDrawableTop(getResources().getDrawable(R.drawable.test_detaillike_red));
					mActivity_find_like.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_like.getDrawableTop(), null, null);

				} else if (likeRes.getBack().equals("1")) {
					mActivity_find_like.setTextColor(getResources().getColor(R.color.textgray));
					mActivity_find_like.setDrawableTop(getResources().getDrawable(R.drawable.test_detaillike));
					mActivity_find_like.setCompoundDrawablesWithIntrinsicBounds(null, mActivity_find_like.getDrawableTop(), null, null);
				}
				mActivity_find_like.invalidate();
			}
			break;
			
		case SingleActivityDetailHandle.SINGLE_ACTIVITY:
			if (dialog != null) {
				dialog.dismiss();
			}
			singleActivityRes=(SingleActivityRes) obj;
			if(singleActivityRes!=null)
			{
				//?????????????????????????????????????????????????????????????????????
				if("Sign".equals(singleActivityRes.getUserSignstate())){
					mActivity_find_register_btn.setText("?????????");
					mActivity_find_register_btn.setBackgroundColor(getResources().getColor(R.color.textgray));	
				}
//				UiHelper.ShowOneToast(this, "??????????????????????????????");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(this, mess);
	}
	
	
	@Override
	public void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
		//??????  Bitmap
		try {
			
		
		mActivity_find_apply_img_iv.getDrawingCache().recycle();
		for (int i = 0; i < mActivity_find_chairdetail_detail_iv.length; i++) {
			mActivity_find_chairdetail_detail_iv[i].getDrawingCache().recycle();
		}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	/**
	 * ?????????????????????????????????activity
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if("?????????".equals(intent.getStringExtra("registration_state"))){
				mActivity_find_register_btn.setText("?????????");
				mActivity_find_register_btn.setBackgroundColor(getResources().getColor(R.color.textgray));
			}else if ("????????????".equals(intent.getStringExtra("registration_state"))){
				mActivity_find_register_btn.setText("????????????");
				mActivity_find_register_btn.setBackgroundColor(getResources().getColor(R.color.text_red));
			}
			if(!"Sign".equals(singleActivityRes.getUserSignstate())){
				mActivity_find_chairsigncount_ptv.setText(StringUtil.noNull("??????"+(tddActivity.getSignCount()+1)+"???"));
			}
			singleActivitySearch();//??????????????????   ????????????   ??????????????????   ?????????????????? ??????
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			sendBroadToManagerPageActivity();
			finish();
			return true;
		}
		return false;
	}
	
	/**
	 * ?????????????????????
	 * @param v
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
		// TODO Auto-generated method stub
		if (tddActivity.getShareContent() != null
				&& tddActivity.getShareImg() != null
				&& tddActivity.getShareUrl() != null) {

			ImageLoader.getInstance().loadImage(tddActivity.getShareImg(),
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							// TODO Auto-generated method stub
							// ????????????
							WXWebpageObject webpage = new WXWebpageObject();
							webpage.webpageUrl = tddActivity.getShareUrl();
							WXMediaMessage msg = new WXMediaMessage(webpage);
							msg.title = tddActivity.getActivityTitle();
							msg.description = tddActivity
									.getActivityDescription();
							// ??????ImgUrl?????????????????????????????????bitmap??????
							// ????????????????????????????????????????????????
							Bitmap thumb = BitmapFactory.decodeResource(
									getResources(), R.drawable.ic_launcher);
							msg.setThumbImage(thumb);
							SendMessageToWX.Req req = new SendMessageToWX.Req();
							req.transaction = buildTransaction("webpage");
							req.message = msg;
							req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
									: SendMessageToWX.Req.WXSceneTimeline;
							boolean fla = wxApi.sendReq(req);
							System.out.println("fla=" + fla);
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap bitmap) {
							// TODO Auto-generated method stub
							// ?????????????????????
							WXWebpageObject webpage = new WXWebpageObject();
							webpage.webpageUrl = tddActivity.getShareUrl();
							WXMediaMessage msg = new WXMediaMessage(webpage);
							msg.title = tddActivity.getActivityTitle();
							msg.description = tddActivity
									.getActivityDescription();
							// ??????ImgUrl?????????????????????????????????bitmap??????
							// ????????????????????????????????????????????????
							Bitmap thumb = bitmap;
							msg.setThumbImage(thumb);
							SendMessageToWX.Req req = new SendMessageToWX.Req();
							req.transaction = buildTransaction("webpage");
							req.message = msg;
							req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
									: SendMessageToWX.Req.WXSceneTimeline;
							boolean fla = wxApi.sendReq(req);
							System.out.println("fla=" + fla);
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							// TODO Auto-generated method stub

						}
					});
		} else {
			UiHelper.ShowOneToast(FindChairDetailActivity.this,
					"?????????????????????????????????????????????");
			finish();
		}
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp arg0) {
		// TODO Auto-generated method stub
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
		mTencent.shareToQQ(FindChairDetailActivity.this, params,
				new BaseUiListener() {
					protected void doComplete(JSONObject values) {
						showResult("shareToQQ:", "????????????");
					}

					@Override
					public void onError(UiError e) {
						showResult("shareToQQ:", "????????????");
					}

					@Override
					public void onCancel() {
						showResult("shareToQQ", "????????????");
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
			showResult("onError:", "code:" + e.errorCode + ", msg:"
					+ e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			doComplete(arg0);
		}
	}

	private Handler mHandler;

	// qq?????????????????????
	private void showResult(final String base, final String msg) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				UiHelper.ShowOneToast(FindChairDetailActivity.this, msg);
				popupWindow.dismiss();
				finish();
			}
		});
	}

	public void sendBroadToManagerPageActivity(){
		Intent intent = new Intent();
		intent.setAction("ManagerPageActivityRefresh");
	/*	Bundle bundle  = new Bundle();
		if(singleActivityRes!=null&&singleActivityRes.getTddActivity()!=null)
		bundle.putSerializable("activityId", singleActivityRes.getTddActivity().getActivityId());
		intent.putExtras(bundle);*/
		sendBroadcast(intent);
	}
}
