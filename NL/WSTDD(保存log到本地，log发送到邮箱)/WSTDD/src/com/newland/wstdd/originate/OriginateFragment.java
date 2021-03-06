package com.newland.wstdd.originate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.bean.TddAdvCfgVo;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.tools.UiToUiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.managerpage.MyActivitiesListAcitivity;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.OriginateFragmentReq;
import com.newland.wstdd.originate.beanresponse.OriginateFragmentRes;
import com.newland.wstdd.originate.handle.OriginateFragmentHandle;
import com.newland.wstdd.originate.origateactivity.OriginateChairActivity;
import com.newland.wstdd.originate.richscan.RichScanActivity;
import com.newland.wstdd.originate.search.OriginateSearchActivity;

/**
 * ???????????????
 * 
 * @author H81 2015-10-31
 * 
 */
public class OriginateFragment extends BaseFragment implements OnClickListener, OnPostListenerInterface {
	private boolean isTest = true;// ?????????????????????
	private View view;
	private boolean initBoolean = false;// ????????????boolean ???????????????????????????
	private android.support.v4.view.ViewPager mFragment_originate_vp;// ??????viewpager

	private EditText mOriginagte_search_edt;// ?????????
	private ImageView mOriginagte_search_iv;// ????????????
	private ImageView mOriginagte_qrcode_iv;// ???+??? ?????????/?????????/????????????

	// ??????
	private PopupWindow popupWindow;// ???????????? popwindow
	private TextView mOriginate_add_addtour;// ????????????
	private TextView mOriginate_add_scan;// ?????????
	private TextView mOriginate_add_myqrcode;// ???????????????

	// ?????????????????????
	private int imageIds[];
	private ArrayList<ImageView> images = new ArrayList<ImageView>();;
	private List<TddAdvCfgVo> adsList = new ArrayList<TddAdvCfgVo>();// ?????????????????????
																		// ????????????
	private ArrayList<View> dots = new ArrayList<View>();;
	private ViewPager mViewPager;
	private ViewPagerAdapter adapter;
	private int oldPosition = 0;// ???????????????????????????
	private int currentItem; // ????????????

	// ????????????
	private HorizontalScrollView mOriginate_genernal_hscorllview;
	private LinearLayout mOriginate_genernal_ll;// ????????????-??????
	private Button mOriginate_genernal_btn1;
	private Button mOriginate_genernal_btn2;
	private Button mOriginate_genernal_btn3;
	private Button mOriginate_genernal_btn4;
	private Button mOriginate_genernal_btn5;
	private Button mOriginate_genernal_btn6;
	private Button mOriginate_genernal_btn7;
	private Button mOriginate_genernal_btn8;
	private Button mOriginate_genernal_btn9;
	private Button mOriginate_genernal_btn10;
	private TextView mOriginate_genernal_left_tv;// ????????????
	private TextView mOriginate_genernal_right_tv;
	private ImageView mOriginate_general_focus_iv;// ?????????
	private ImageView mOriginate_general_focus_iv1;

	// ????????????
	private HorizontalScrollView mOriginate_home_hscorllview;
	private LinearLayout mOriginate_home_ll;// ????????????-??????
	private Button mOriginate_home_btn1;
	private Button mOriginate_home_btn2;
	private Button mOriginate_home_btn3;
	private Button mOriginate_home_btn4;
	private Button mOriginate_home_btn5;
	private Button mOriginate_home_btn6;
	private Button mOriginate_home_btn7;
	private Button mOriginate_home_btn8;
	private Button mOriginate_home_btn9;
	private Button mOriginate_home_btn10;
	private TextView mOriginate_home_left_tv;
	private TextView mOriginate_home_right_tv;
	private ImageView mOriginate_home_focus_iv;// ?????????
	private ImageView mOriginate_home_focus_iv1;

	// ????????????
	private HorizontalScrollView mOriginate_corporation_hscorllview;
	private LinearLayout mOriginate_corporation_ll;// ????????????-??????
	private Button mOriginate_corporation_btn1;
	private Button mOriginate_corporation_btn2;
	private Button mOriginate_corporation_btn3;
	private Button mOriginate_corporation_btn4;
	private Button mOriginate_corporation_btn5;
	private Button mOriginate_corporation_btn6;
	private Button mOriginate_corporation_btn7;
	private Button mOriginate_corporation_btn8;
	private Button mOriginate_corporation_btn9;
	private Button mOriginate_corporation_btn10;
	private TextView mOriginate_corporation_left_tv;
	private TextView mOriginate_corporation_right_tv;
	private ImageView mOriginate_corporation_focus_iv;// ?????????
	private ImageView mOriginate_corporation_focus_iv1;

	// ????????????
	private TextView mOriginate_myactivity_content_tv;
	private TextView mOriginate_myactivity_tv;
	private RelativeLayout mOriginate_myactivity_rl;
	// ????????????
	private TextView mOriginate_minejoin_content_tv;
	private TextView mOriginate_minejoin_tv;
	private RelativeLayout mOriginate_minejoin_rl;

	private int blockWidth;// ????????????????????????
	int mPosX, mPosY, mCurrentPosX, mCurrentPosY;// ?????????x,y??????????????????x,y??????

	List<Map<String, Object>> list;

	// ???????????????
	OriginateFragmentRes originateFragmentRes;
	OriginateFragmentHandle handler = new OriginateFragmentHandle(this);

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.out.println("ExampleFragment--onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_originate, container, false);
		return view;
	}

	// ?????????setUserVisibleHint ???????????????onStart();
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		if (initBoolean) {
			initBoolean = false;
			if (getUserVisibleHint()) {
				setUserVisibleHint(true);
			}

		} else {
			initBoolean = true;
		}
		super.onStart();
	}

	// ?????????Oncreate()???????????? ??????????????????????????????false
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// ????????????????????? ???????????????this.isVisible??????
		if (this.isVisible()) {
			if (isVisibleToUser) {
				// UiHelper.ShowOneToast(getActivity(), "orginate");
				if (isAdded()) {
					if("true".equals(AppContext.getAppContext().getIsLogin())){
						refresh();// ???????????????????????????
					}else{
						if(AppContext.getAppContext().getMyAcNum()!=null&&!"".equals(AppContext.getAppContext().getMyAcNum())){
							mOriginate_myactivity_content_tv.setText(AppContext.getAppContext().getMyAcNum());
						}else{
							mOriginate_myactivity_content_tv.setText("0");
						}
						 if(AppContext.getAppContext().getMySignAcNum()!=null&&!"".equals(AppContext.getAppContext().getMySignAcNum())){
							 mOriginate_minejoin_content_tv.setText(AppContext.getAppContext().getMySignAcNum());
						 }else{
							 mOriginate_minejoin_content_tv.setText("0");
						 }
						 
					}
					// mOriginate_myactivity_content_tv.setText(AppContext.getAppContext().getMyAcNum());
					// mOriginate_minejoin_content_tv.setText(AppContext.getAppContext().getMySignAcNum());
					
					initBoolean = true;
				}

			}
		}
		super.setUserVisibleHint(isVisibleToUser);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // ??????????????????

		// setLoginSignInDialog();

		if (view != null && isTest) {
			// getViewPagerImgFromService();
			initViewPager();
			isTest = false;
		}
		initView();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		mFragment_originate_vp = (android.support.v4.view.ViewPager) view.findViewById(R.id.fragment_originate_vp);
		mOriginagte_search_edt = (EditText) view.findViewById(R.id.originagte_search_edt);
		mOriginagte_search_iv = (ImageView) view.findViewById(R.id.originagte_search_iv);
		mOriginagte_qrcode_iv = (ImageView) view.findViewById(R.id.originagte_qrcode_iv);
		mOriginagte_qrcode_iv.setOnClickListener(this);
		mOriginagte_search_edt.setOnClickListener(this);
		mOriginagte_search_edt.setFocusable(false);
		mOriginagte_search_edt.requestFocus();
		blockWidth = AppContext.getAppContext().getScreenWidth() / 3;
		initGenernalView();
		initHomeView();
		initCorporationView();
		initMyActivity();// ????????????
		initMineJoin();// ????????????
		refresh();// ???????????????????????????
	}

	
		
	@Override
	public void refresh() {
		// TODO Auto-generated method stub

		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					OriginateFragmentReq reqInfo = new OriginateFragmentReq();
					reqInfo.setUserId(AppContext.getAppContext().getUserId());
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<OriginateFragmentRes> ret = mgr.getOriginateFragmentInfo(reqInfo);// ????????????
					Message message = new Message();
					message.what = OriginateFragmentHandle.ORIGINATE_FRAGMENT;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						if(ret.getObj()!=null){
							originateFragmentRes = (OriginateFragmentRes) ret.getObj();
							message.obj = originateFragmentRes;
						}else{
							originateFragmentRes = new OriginateFragmentRes();
							message.obj = originateFragmentRes;
						}
						
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

	
	/**
	 * ????????????
	 * 
	 */
	private void initGenernalView() {
		mOriginate_genernal_hscorllview = (HorizontalScrollView) view.findViewById(R.id.originate_genernal_hscorllview);
		mOriginate_genernal_ll = (LinearLayout) view.findViewById(R.id.originate_genernal_ll);
		mOriginate_genernal_btn1 = (Button) view.findViewById(R.id.originate_genernal_btn1);
		mOriginate_genernal_btn2 = (Button) view.findViewById(R.id.originate_genernal_btn2);
		mOriginate_genernal_btn3 = (Button) view.findViewById(R.id.originate_genernal_btn3);
		mOriginate_genernal_btn4 = (Button) view.findViewById(R.id.originate_genernal_btn4);
		mOriginate_genernal_btn5 = (Button) view.findViewById(R.id.originate_genernal_btn5);
		mOriginate_genernal_btn6 = (Button) view.findViewById(R.id.originate_genernal_btn6);
		mOriginate_genernal_btn7 = (Button) view.findViewById(R.id.originate_genernal_btn7);
		mOriginate_genernal_btn8 = (Button) view.findViewById(R.id.originate_genernal_btn8);
		mOriginate_genernal_btn9 = (Button) view.findViewById(R.id.originate_genernal_btn9);
		mOriginate_genernal_btn10 = (Button) view.findViewById(R.id.originate_genernal_btn10);
		mOriginate_genernal_left_tv = (TextView) view.findViewById(R.id.originate_genernal_left_tv);
		mOriginate_genernal_right_tv = (TextView) view.findViewById(R.id.originate_genernal_right_tv);
		mOriginate_general_focus_iv = (ImageView) view.findViewById(R.id.originate_general_focus_iv);
		mOriginate_general_focus_iv1 = (ImageView) view.findViewById(R.id.originate_general_focus_iv1);
		mOriginate_general_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focused));
		mOriginate_general_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focus));

		if (mOriginate_genernal_left_tv != null) {
			mOriginate_genernal_left_tv.setOnClickListener(this);
		}
		if (mOriginate_genernal_right_tv != null) {
			mOriginate_genernal_right_tv.setOnClickListener(this);
		}
		if (mOriginate_genernal_ll != null) {
			mOriginate_genernal_ll.setOnClickListener(this);
			mOriginate_genernal_ll.setMinimumWidth(blockWidth);
		}
		if (mOriginate_genernal_btn1 != null) {
			mOriginate_genernal_btn1.setOnClickListener(this);
			mOriginate_genernal_btn1.setText("??????");
			mOriginate_genernal_btn1.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn2 != null) {
			mOriginate_genernal_btn2.setOnClickListener(this);
			mOriginate_genernal_btn2.setText("??????");
			mOriginate_genernal_btn2.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn3 != null) {
			mOriginate_genernal_btn3.setOnClickListener(this);
			mOriginate_genernal_btn3.setText("??????");
			mOriginate_genernal_btn3.setWidth(blockWidth);

		}
		if (mOriginate_genernal_btn4 != null) {
			mOriginate_genernal_btn4.setOnClickListener(this);
			mOriginate_genernal_btn4.setText("??????");
			mOriginate_genernal_btn4.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn5 != null) {
			mOriginate_genernal_btn5.setOnClickListener(this);
			mOriginate_genernal_btn5.setText("??????");
			mOriginate_genernal_btn5.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn6 != null) {
			mOriginate_genernal_btn6.setOnClickListener(this);
			mOriginate_genernal_btn6.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn7 != null) {
			mOriginate_genernal_btn7.setOnClickListener(this);
			mOriginate_genernal_btn7.setText("??????");
			mOriginate_genernal_btn7.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn8 != null) {
			mOriginate_genernal_btn8.setOnClickListener(this);
			mOriginate_genernal_btn8.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn9 != null) {
			mOriginate_genernal_btn9.setOnClickListener(this);
			mOriginate_genernal_btn9.setWidth(blockWidth);
		}
		if (mOriginate_genernal_btn10 != null) {
			mOriginate_genernal_btn10.setOnClickListener(this);
			mOriginate_genernal_btn10.setWidth(blockWidth);
		}

		mOriginate_genernal_hscorllview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					mPosX = (int) event.getX();
					mPosY = (int) event.getY();
				}
				if (MotionEvent.ACTION_MOVE == event.getAction()) {
					mCurrentPosX = (int) event.getX() - mPosX;
					mCurrentPosY = (int) event.getY() - mPosY;
					mPosX = (int) event.getX();
					mPosY = (int) event.getY();
				}
				if (mCurrentPosX > 10 && Math.abs(mCurrentPosY) < 10) {
					mOriginate_genernal_left_tv.setVisibility(View.GONE);
					mOriginate_genernal_right_tv.setVisibility(View.VISIBLE);
					mOriginate_genernal_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
					mOriginate_general_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focused));
					mOriginate_general_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focus));

				} else if (mCurrentPosX < -10 && Math.abs(mCurrentPosY) < 10) {
					mOriginate_genernal_left_tv.setVisibility(View.VISIBLE);
					mOriginate_genernal_right_tv.setVisibility(View.GONE);
					mOriginate_genernal_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
					mOriginate_general_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focus));
					mOriginate_general_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focused));
				}
				return true;
			}
		});
	}

	/**
	 * ????????????
	 */
	private void initHomeView() {
		mOriginate_home_hscorllview = (HorizontalScrollView) view.findViewById(R.id.originate_home_hscorllview);
		mOriginate_home_ll = (LinearLayout) view.findViewById(R.id.originate_home_ll);
		mOriginate_home_btn1 = (Button) view.findViewById(R.id.originate_home_btn1);
		mOriginate_home_btn2 = (Button) view.findViewById(R.id.originate_home_btn2);
		mOriginate_home_btn3 = (Button) view.findViewById(R.id.originate_home_btn3);
		mOriginate_home_btn4 = (Button) view.findViewById(R.id.originate_home_btn4);
		mOriginate_home_btn5 = (Button) view.findViewById(R.id.originate_home_btn5);
		mOriginate_home_btn6 = (Button) view.findViewById(R.id.originate_home_btn6);
		mOriginate_home_btn7 = (Button) view.findViewById(R.id.originate_home_btn7);
		mOriginate_home_btn8 = (Button) view.findViewById(R.id.originate_home_btn8);
		mOriginate_home_btn9 = (Button) view.findViewById(R.id.originate_home_btn9);
		mOriginate_home_btn10 = (Button) view.findViewById(R.id.originate_home_btn10);
		mOriginate_home_left_tv = (TextView) view.findViewById(R.id.originate_home_left_tv);
		mOriginate_home_right_tv = (TextView) view.findViewById(R.id.originate_home_right_tv);
		mOriginate_home_focus_iv = (ImageView) view.findViewById(R.id.originate_home_focus_iv);
		mOriginate_home_focus_iv1 = (ImageView) view.findViewById(R.id.originate_home_focus_iv1);
		mOriginate_home_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focused));
		mOriginate_home_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focus));

		if (mOriginate_home_left_tv != null) {
			mOriginate_home_left_tv.setOnClickListener(this);
		}
		if (mOriginate_home_right_tv != null) {
			mOriginate_home_right_tv.setOnClickListener(this);
		}
		if (mOriginate_home_ll != null) {
			mOriginate_home_ll.setOnClickListener(this);
			mOriginate_home_ll.setMinimumWidth(blockWidth);
		}
		if (mOriginate_home_btn1 != null) {
			mOriginate_home_btn1.setOnClickListener(this);
			mOriginate_home_btn1.setText("??????");
			mOriginate_home_btn1.setWidth(blockWidth);
		}
		if (mOriginate_home_btn2 != null) {
			mOriginate_home_btn2.setOnClickListener(this);
			mOriginate_home_btn2.setText("??????");
			mOriginate_home_btn2.setWidth(blockWidth);
		}
		if (mOriginate_home_btn3 != null) {
			mOriginate_home_btn3.setOnClickListener(this);
			mOriginate_home_btn3.setText("??????");
			mOriginate_home_btn3.setWidth(blockWidth);

		}
		if (mOriginate_home_btn4 != null) {
			mOriginate_home_btn4.setOnClickListener(this);
			mOriginate_home_btn4.setText("??????");
			mOriginate_home_btn4.setWidth(blockWidth);
		}
		if (mOriginate_home_btn5 != null) {
			mOriginate_home_btn5.setOnClickListener(this);
			mOriginate_home_btn5.setText("??????");
			mOriginate_home_btn5.setWidth(blockWidth);
		}
		if (mOriginate_home_btn6 != null) {
			mOriginate_home_btn6.setOnClickListener(this);
			mOriginate_home_btn6.setWidth(blockWidth);
			mOriginate_home_btn6.setText("??????");
		}
		if (mOriginate_home_btn7 != null) {
			mOriginate_home_btn7.setOnClickListener(this);
			mOriginate_home_btn7.setWidth(blockWidth);
			mOriginate_home_btn7.setText("??????");
		}
		if (mOriginate_home_btn8 != null) {
			mOriginate_home_btn8.setOnClickListener(this);
			mOriginate_home_btn8.setWidth(blockWidth);
			mOriginate_home_btn8.setText("??????");
		}
		if (mOriginate_home_btn9 != null) {
			mOriginate_home_btn9.setOnClickListener(this);
			mOriginate_home_btn9.setWidth(blockWidth);
			mOriginate_home_btn9.setText("??????");
		}
		if (mOriginate_home_btn10 != null) {
			mOriginate_home_btn10.setOnClickListener(this);
			mOriginate_home_btn10.setWidth(blockWidth);
		}

		mOriginate_home_hscorllview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					mPosX = (int) event.getX();
					mPosY = (int) event.getY();
				}
				if (MotionEvent.ACTION_MOVE == event.getAction()) {
					mCurrentPosX = (int) event.getX() - mPosX;
					mCurrentPosY = (int) event.getY() - mPosY;
					mPosX = (int) event.getX();
					mPosY = (int) event.getY();
				}
				if (mCurrentPosX > 10 && Math.abs(mCurrentPosY) < 10) {
					mOriginate_home_left_tv.setVisibility(View.GONE);
					mOriginate_home_right_tv.setVisibility(View.VISIBLE);
					mOriginate_home_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
					mOriginate_home_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focused));
					mOriginate_home_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focus));
				} else if (mCurrentPosX < -10 && Math.abs(mCurrentPosY) < 10) {
					mOriginate_home_left_tv.setVisibility(View.VISIBLE);
					mOriginate_home_right_tv.setVisibility(View.GONE);
					mOriginate_home_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
					mOriginate_home_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focus));
					mOriginate_home_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focused));
				}
				return true;
			}
		});
	}

	/**
	 * ????????????
	 */
	private void initCorporationView() {
		mOriginate_corporation_hscorllview = (HorizontalScrollView) view.findViewById(R.id.originate_corporation_hscorllview);
		mOriginate_corporation_ll = (LinearLayout) view.findViewById(R.id.originate_corporation_ll);
		mOriginate_corporation_btn1 = (Button) view.findViewById(R.id.originate_corporation_btn1);
		mOriginate_corporation_btn2 = (Button) view.findViewById(R.id.originate_corporation_btn2);
		mOriginate_corporation_btn3 = (Button) view.findViewById(R.id.originate_corporation_btn3);
		mOriginate_corporation_btn4 = (Button) view.findViewById(R.id.originate_corporation_btn4);
		mOriginate_corporation_btn5 = (Button) view.findViewById(R.id.originate_corporation_btn5);
		mOriginate_corporation_btn6 = (Button) view.findViewById(R.id.originate_corporation_btn6);
		mOriginate_corporation_btn7 = (Button) view.findViewById(R.id.originate_corporation_btn7);
		mOriginate_corporation_btn8 = (Button) view.findViewById(R.id.originate_corporation_btn8);
		mOriginate_corporation_btn9 = (Button) view.findViewById(R.id.originate_corporation_btn9);
		mOriginate_corporation_btn10 = (Button) view.findViewById(R.id.originate_corporation_btn10);
		mOriginate_corporation_left_tv = (TextView) view.findViewById(R.id.originate_corporation_left_tv);
		mOriginate_corporation_right_tv = (TextView) view.findViewById(R.id.originate_corporation_right_tv);
		mOriginate_corporation_focus_iv = (ImageView) view.findViewById(R.id.originate_corporation_focus_iv);
		mOriginate_corporation_focus_iv1 = (ImageView) view.findViewById(R.id.originate_corporation_focus_iv1);
		mOriginate_corporation_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focused));
		mOriginate_corporation_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focus));
		if (mOriginate_corporation_left_tv != null) {
			mOriginate_corporation_left_tv.setOnClickListener(this);
		}
		if (mOriginate_corporation_right_tv != null) {
			mOriginate_corporation_right_tv.setOnClickListener(this);
		}
		if (mOriginate_corporation_ll != null) {
			mOriginate_corporation_ll.setOnClickListener(this);
			mOriginate_corporation_ll.setMinimumWidth(blockWidth);
		}
		if (mOriginate_corporation_btn1 != null) {
			mOriginate_corporation_btn1.setOnClickListener(this);
			mOriginate_corporation_btn1.setText("??????");
			mOriginate_corporation_btn1.setWidth(blockWidth);
		}
		if (mOriginate_corporation_btn2 != null) {
			mOriginate_corporation_btn2.setOnClickListener(this);
			mOriginate_corporation_btn2.setText("??????");
			mOriginate_corporation_btn2.setWidth(blockWidth);
		}
		if (mOriginate_corporation_btn3 != null) {
			mOriginate_corporation_btn3.setOnClickListener(this);
			mOriginate_corporation_btn3.setText("??????");
			mOriginate_corporation_btn3.setWidth(blockWidth);

		}
		if (mOriginate_corporation_btn4 != null) {
			mOriginate_corporation_btn4.setOnClickListener(this);
			mOriginate_corporation_btn4.setText("??????");
			mOriginate_corporation_btn4.setWidth(blockWidth);
		}
		if (mOriginate_corporation_btn5 != null) {
			mOriginate_corporation_btn5.setOnClickListener(this);
			mOriginate_corporation_btn5.setText("??????");
			mOriginate_corporation_btn5.setWidth(blockWidth);
		}
		if (mOriginate_corporation_btn6 != null) {
			mOriginate_corporation_btn6.setOnClickListener(this);
			mOriginate_corporation_btn6.setWidth(blockWidth);
			mOriginate_corporation_btn6.setText("??????");
		}
		if (mOriginate_corporation_btn7 != null) {
			mOriginate_corporation_btn7.setOnClickListener(this);
			mOriginate_corporation_btn7.setWidth(blockWidth);
			mOriginate_corporation_btn7.setText("??????");
		}
		if (mOriginate_corporation_btn8 != null) {
			mOriginate_corporation_btn8.setOnClickListener(this);
			mOriginate_corporation_btn8.setWidth(blockWidth);
			mOriginate_corporation_btn8.setText("??????");
			mOriginate_corporation_btn8.setVisibility(View.INVISIBLE);
		}
		if (mOriginate_corporation_btn9 != null) {
			mOriginate_corporation_btn9.setOnClickListener(this);
			mOriginate_corporation_btn9.setWidth(blockWidth);
			mOriginate_corporation_btn9.setText("??????");
			mOriginate_corporation_btn9.setVisibility(View.VISIBLE);
		}
		if (mOriginate_corporation_btn10 != null) {
			mOriginate_corporation_btn10.setOnClickListener(this);
			mOriginate_corporation_btn10.setWidth(blockWidth);
			mOriginate_corporation_btn10.setVisibility(View.INVISIBLE);
		}

		mOriginate_corporation_hscorllview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					mPosX = (int) event.getX();
					mPosY = (int) event.getY();
				}
				if (MotionEvent.ACTION_MOVE == event.getAction()) {
					mCurrentPosX = (int) event.getX() - mPosX;
					mCurrentPosY = (int) event.getY() - mPosY;
					mPosX = (int) event.getX();
					mPosY = (int) event.getY();
				}
				if (mCurrentPosX > 10 && Math.abs(mCurrentPosY) < 10) {
					mOriginate_corporation_left_tv.setVisibility(View.GONE);
					mOriginate_corporation_right_tv.setVisibility(View.VISIBLE);
					mOriginate_corporation_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
					mOriginate_corporation_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focused));
					mOriginate_corporation_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focus));
				} else if (mCurrentPosX < -10 && Math.abs(mCurrentPosY) < 10) {
					mOriginate_corporation_left_tv.setVisibility(View.VISIBLE);
					mOriginate_corporation_right_tv.setVisibility(View.GONE);
					mOriginate_corporation_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
					mOriginate_corporation_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focus));
					mOriginate_corporation_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focused));
				}
				return true;
			}
		});
	}

	/**
	 * ????????????
	 */
	private void initMyActivity() {
		mOriginate_myactivity_rl = (RelativeLayout) view.findViewById(R.id.originate_myactivity_rl);
		mOriginate_myactivity_content_tv = (TextView) view.findViewById(R.id.originate_myactivity_content_tv);
		mOriginate_myactivity_tv = (TextView) view.findViewById(R.id.originate_myactivity_tv);
		if(AppContext.getAppContext().getMyAcNum()!=null&&!"".equals(AppContext.getAppContext().getMyAcNum())){
			mOriginate_myactivity_content_tv.setText(AppContext.getAppContext().getMyAcNum() + "");
		}else{
			mOriginate_myactivity_content_tv.setText("0");
		}
		
		mOriginate_myactivity_tv.setText("????????????");
		mOriginate_myactivity_rl.setOnClickListener(this);
	}

	/**
	 * ????????????
	 */
	private void initMineJoin() {
		mOriginate_minejoin_rl = (RelativeLayout) view.findViewById(R.id.originate_minejoin_rl);
		mOriginate_minejoin_content_tv = (TextView) view.findViewById(R.id.originate_minejoin_content_tv);
		mOriginate_minejoin_tv = (TextView) view.findViewById(R.id.originate_minejoin_tv);
		if(AppContext.getAppContext().getMySignAcNum()!=null&&!"".equals(AppContext.getAppContext().getMySignAcNum())){
			mOriginate_minejoin_content_tv.setText(AppContext.getAppContext().getMySignAcNum() + "");
		}else{
			mOriginate_minejoin_content_tv.setText("0");
		}
		mOriginate_minejoin_tv.setText("????????????");
		mOriginate_minejoin_rl.setOnClickListener(this);
	}

	/**
	 * ???????????????????????????
	 */
	private void setLoginSignInDialog() {
		final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
		View mView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_originate_loginsignindialog, null);
		builder.setView(mView);
		ImageView close_img = (ImageView) mView.findViewById(R.id.close_img);
		close_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder.cancel();
			}
		});
		builder.setCanceledOnTouchOutside(false);
		builder.show();

	}

	private void getPopupWindow() {
		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}

	private void getServicePicture() {
		list = new ArrayList<Map<String, Object>>();
		list.clear();
		// if (StringUtil.isNotEmpty(userInfo.getBanner_img())) {
		// String[] img_arr = userInfo.getBanner_img().split(",");
		// for (int i = 0; i < img_arr.length; i++) {
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("focusImage", HttpUtils.URL_ROOT + img_arr[i]);
		// map1.put("intro", "");
		// list.add(map1);
		//
		// }
		// while (list.size() < 5) { // ??????????????????5???
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("focusImage", "xxx.png");
		// map.put("intro", "");
		// list.add(map);
		// }
		//
		// } else {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("focusImage", "http://c.hiphotos.baidu.com/image/pic/item/4afbfbedab64034f8a24f516aec379310a551d30.jpg");
		map1.put("intro", "");

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("focusImage", "http://c.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1710cedc3b4890f603738de943.jpg");
		map2.put("intro", "");

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("focusImage", "http://a.hiphotos.baidu.com/image/pic/item/7dd98d1001e93901c9dc2a3379ec54e737d196e5.jpg");
		map3.put("intro", "");

		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("focusImage", "http://d.hiphotos.baidu.com/image/pic/item/0dd7912397dda144969476a6b0b7d0a20cf48600.jpg");
		map4.put("intro", "");

		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("focusImage", "http://a.hiphotos.baidu.com/image/pic/item/03087bf40ad162d962eec41113dfa9ec8a13cdbf.jpg");
		map5.put("intro", "");

		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		// }
	}

	/**
	 * 
	 * ???????????????viewpager
	 */
	private void initViewPager() {

		getServicePicture();

		// ??????ID
		imageIds = new int[] { R.drawable.test_item01, R.drawable.test_item02, R.drawable.test_item03, R.drawable.test_item04, R.drawable.test_item05 };

		// ???????????????

		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
			// ????????????
			dots.add(view.findViewById(R.id.dot_0));
		}

		mViewPager = (ViewPager) view.findViewById(R.id.fragment_originate_vp);
		adapter = new ViewPagerAdapter();
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int position) {
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;
				currentItem = position;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	// ????????????
	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.size();
		}

		// ????????????????????????
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(images.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(images.get(position));
			return images.get(position);
		}
	}

	protected void initPopuptWindow() {
		View popupWindow_view = getActivity().getLayoutInflater().inflate(R.layout.fragment_originate_addpopwindow, null, false);

		popupWindow = new PopupWindow(popupWindow_view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.showAsDropDown(mOriginagte_qrcode_iv, 0, 0);

		popupWindow_view.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});

		mOriginate_add_addtour = (TextView) popupWindow_view.findViewById(R.id.originate_add_addtour);
		mOriginate_add_scan = (TextView) popupWindow_view.findViewById(R.id.originate_add_scan);
		mOriginate_add_myqrcode = (TextView) popupWindow_view.findViewById(R.id.originate_add_myqrcode);
		// if (warning != null) {
		// mOriginate_add_addtour.setText("???????????????" +
		// StringUtil.noNull(warning.hw_warn, "0"));
		// mOriginate_add_scan.setText("????????????" +
		// StringUtil.noNull(warning.fault_warn, "0"));
		// mOriginate_add_myqrcode.setText("??????????????????" +
		// StringUtil.noNull(warning.complaint, "0"));
		mOriginate_add_addtour.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Intent intent = new Intent(getActivity(),
				// IndicatorWarningActivity.class);
				// intent.putExtra("quarter_type", currentDateType);
				// intent.putExtra("time", time);
				// intent.putExtra("type", "hw_warn");
				// startActivity(intent);
			}
		});

		mOriginate_add_scan.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Intent intent = new Intent(getActivity(),
				// IndicatorWarningActivity.class);
				// intent.putExtra("quarter_type", currentDateType);
				// intent.putExtra("time", time);
				// intent.putExtra("type", "fault_warn");
				// startActivity(intent);
			}
		});

		mOriginate_add_myqrcode.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// Intent intent = new Intent(getActivity(),
				// IndicatorWarningActivity.class);
				// intent.putExtra("quarter_type", currentDateType);
				// intent.putExtra("time", time);
				// intent.putExtra("type", "complaint");
				// startActivity(intent);
			}
		});
	}

	// }

	// ????????????
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.originagte_qrcode_iv:// ??????
			getActivity().startActivity(new Intent(getActivity(), RichScanActivity.class));
			// ???????????????????????????id

			// getPopupWindow();
			// popupWindow.showAsDropDown(v);
			break;
		case R.id.originagte_search_edt:// ??????
			if (null != AppContext.getAppContext() && "".equals(AppContext.getAppContext().getIsLogin()) || "false".equals(AppContext.getAppContext().getIsLogin())) {
				// UiHelper.ShowOneToast(getActivity(), "?????????????????????????????????");
				UiToUiHelper.showLogin(getActivity());
				//TODO ??????
			} else if (null != AppContext.getAppContext() && "true".equals(AppContext.getAppContext().getIsLogin())) {
				
				intent = new Intent(getActivity(), OriginateSearchActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.originate_genernal_left_tv:// ????????????--????????????
			mOriginate_genernal_left_tv.setVisibility(View.GONE);
			mOriginate_genernal_right_tv.setVisibility(View.VISIBLE);
			mOriginate_general_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focused));
			mOriginate_general_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focus));
			mOriginate_genernal_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			mOriginate_genernal_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			break;
		case R.id.originate_genernal_right_tv:// ????????????--????????????
			mOriginate_genernal_left_tv.setVisibility(View.VISIBLE);
			mOriginate_genernal_right_tv.setVisibility(View.GONE);
			mOriginate_general_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focus));
			mOriginate_general_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_red_focused));
			mOriginate_genernal_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			mOriginate_genernal_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			break;
		case R.id.originate_home_left_tv:// ????????????--????????????
			mOriginate_home_left_tv.setVisibility(View.GONE);
			mOriginate_home_right_tv.setVisibility(View.VISIBLE);
			mOriginate_home_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focused));
			mOriginate_home_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focus));
			mOriginate_home_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			mOriginate_home_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			break;
		case R.id.originate_home_right_tv:// ????????????--????????????
			mOriginate_home_left_tv.setVisibility(View.VISIBLE);
			mOriginate_home_right_tv.setVisibility(View.GONE);
			mOriginate_home_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focus));
			mOriginate_home_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_green_focused));
			mOriginate_home_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			mOriginate_home_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			break;
		case R.id.originate_corporation_left_tv:// ????????????--????????????
			mOriginate_corporation_left_tv.setVisibility(View.GONE);
			mOriginate_corporation_right_tv.setVisibility(View.VISIBLE);
			mOriginate_corporation_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focused));
			mOriginate_corporation_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focus));
			mOriginate_corporation_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			mOriginate_corporation_hscorllview.fullScroll(HorizontalScrollView.FOCUS_LEFT);
			break;
		case R.id.originate_corporation_right_tv:// ????????????--????????????
			mOriginate_corporation_left_tv.setVisibility(View.VISIBLE);
			mOriginate_corporation_right_tv.setVisibility(View.GONE);
			mOriginate_corporation_focus_iv.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focus));
			mOriginate_corporation_focus_iv1.setImageDrawable(getResources().getDrawable(R.drawable.originate_blue_focused));
			mOriginate_corporation_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			mOriginate_corporation_hscorllview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
			break;
		/**
		 * ????????????
		 */
		case R.id.originate_genernal_ll:// ??????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 1);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
		
			break;
		case R.id.originate_genernal_btn1:// ??????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 4);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
			break;
		case R.id.originate_genernal_btn2:// ??????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 9);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
			
			break;
		case R.id.originate_genernal_btn3:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_genernal_btn4:// ??????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 6);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
			break;
		case R.id.originate_genernal_btn5:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_genernal_btn6://
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_genernal_btn7:// ??????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 2);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
			break;
		case R.id.originate_genernal_btn8://??????    ?????????????????????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 0);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
			break;
		case R.id.originate_genernal_btn9://
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_genernal_btn10://
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;

		/**
		 * ????????????
		 */
		case R.id.originate_home_ll:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn1:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn2:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn3:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn4:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn5:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn6:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn7:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn8:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn9:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_home_btn10://
			break;
		/**
		 * ????????????
		 * 
		 */
		case R.id.originate_corporation_ll:// ??????
			if("true".equals(AppContext.getAppContext().getIsLogin())){
				intent = new Intent(getActivity(), OriginateChairActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("activity_type", 3);
				bundle.putString("activity_action", "publish");
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}else{
				UiToUiHelper.showLogin(getActivity());
			}
			break;
		case R.id.originate_corporation_btn1:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn2:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn3:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn4:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn5:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;

		case R.id.originate_corporation_btn6:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn7:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn8://
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn9:// ??????
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		case R.id.originate_corporation_btn10://
			UiHelper.ShowOneToast(getActivity(), "????????????????????????,????????????");
			break;
		/**
		 * ????????????
		 */
		case R.id.originate_myactivity_rl:
			if (null != AppContext.getAppContext() && "".equals(AppContext.getAppContext().getIsLogin()) || "false".equals(AppContext.getAppContext().getIsLogin())) {
				// UiHelper.ShowOneToast(getActivity(), "?????????????????????????????????");
				UiToUiHelper.showLogin(getActivity());
			} else if (null != AppContext.getAppContext() && "true".equals(AppContext.getAppContext().getIsLogin())) {
				intent = new Intent(getActivity(), MyActivitiesListAcitivity.class);
				intent.putExtra("activitybtn", "originate");
				startActivity(intent);
			}

			break;
		/**
		 * ?????????
		 */
		case R.id.originate_minejoin_rl:
			if (null != AppContext.getAppContext() && "".equals(AppContext.getAppContext().getIsLogin()) || "false".equals(AppContext.getAppContext().getIsLogin())) {
				// UiHelper.ShowOneToast(getActivity(), "?????????????????????????????????");
				UiToUiHelper.showLogin(getActivity());
			} else if (null != AppContext.getAppContext() && "true".equals(AppContext.getAppContext().getIsLogin())) {
				intent = new Intent(getActivity(), MyActivitiesListAcitivity.class);
				intent.putExtra("activitybtn", "participation");
				startActivity(intent);
			}

			break;
		default:
			break;
		}
	}
	
	
	// ???????????????
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		// TODO Auto-generated method stub
		try {
			if (progressDialog != null) {
				progressDialog.setContinueDialog(false);
			}
			switch (responseId) {

			case OriginateFragmentHandle.ORIGINATE_FRAGMENT:
				originateFragmentRes = (OriginateFragmentRes) obj;
				if (originateFragmentRes != null) {
					adsList = originateFragmentRes.getHomeAds();
					for (int i = 0; i < adsList.size(); i++) {
						ImageView imageView = new ImageView(getActivity());
						ImageDownLoad.getDownLoadImg(adsList.get(i).getAdvImg(), imageView);
						images.add(imageView);
						dots.add(view.findViewById(R.id.dot_0));
					}
					// ????????????
					mViewPager = (ViewPager) view.findViewById(R.id.fragment_originate_vp);
					adapter = new ViewPagerAdapter();
					mViewPager.setAdapter(adapter);
					mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

						public void onPageSelected(int position) {
							dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
							dots.get(position).setBackgroundResource(R.drawable.dot_focused);
							oldPosition = position;
							currentItem = position;
						}

						public void onPageScrolled(int arg0, float arg1, int arg2) {

						}

						public void onPageScrollStateChanged(int arg0) {

						}
					});
					mOriginate_myactivity_content_tv.setText(originateFragmentRes.getMyAcNum() + "");
					mOriginate_minejoin_content_tv.setText(originateFragmentRes.getMySignAcNum() + "");
					AppContext.getAppContext().setMyAcNum(originateFragmentRes.getMyAcNum() + "");
					AppContext.getAppContext().setMySignAcNum(originateFragmentRes.getMySignAcNum() + "");
				}

				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void OnFailResultListener(String mess) {
		if (progressDialog != null) {
			progressDialog.setContinueDialog(false);
			UiHelper.ShowOneToast(getActivity(), mess);
		}
		if (progressDialog != null) {
			progressDialog.setContinueDialog(false);
		}
		if(AppContext.getAppContext().getMyAcNum()!=null&&!"".equals(AppContext.getAppContext().getMyAcNum())){
			mOriginate_myactivity_content_tv.setText(AppContext.getAppContext().getMyAcNum() + "");
		}else{
			mOriginate_myactivity_content_tv.setText("0");
		}
		if(AppContext.getAppContext().getMySignAcNum()!=null&&!"".equals(AppContext.getAppContext().getMySignAcNum())){
			mOriginate_minejoin_content_tv.setText(AppContext.getAppContext().getMySignAcNum() + "");
		}else{
			mOriginate_minejoin_content_tv.setText("0");
		}
		

	}

	@Override
	protected View createAndInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}

}
