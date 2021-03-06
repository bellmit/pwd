/**
 * @fields ManagerApplyListActivity.java
 */
package com.newland.wstdd.mine.applyList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.newland.wstdd.R;
import com.newland.wstdd.common.adapter.BaseFragmentPagerAdapter;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.EditTextUtil;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.applyList.bean.RegistrationListReq;
import com.newland.wstdd.mine.applyList.bean.RegistrationListRes;
import com.newland.wstdd.mine.applyList.bean.TddActivitySignVo;
import com.newland.wstdd.mine.applyList.bean.TddActivitySignVoInfo;
import com.newland.wstdd.mine.applyList.bean.UpdateRegistrationListReq;
import com.newland.wstdd.mine.applyList.bean.UpdateRegistrationListRes;
import com.newland.wstdd.mine.applyList.beanreq.MailUrlReq;
import com.newland.wstdd.mine.applyList.beanres.MailUrlRes;
import com.newland.wstdd.mine.applyList.dialog.MailDialog;
import com.newland.wstdd.mine.applyList.handle.RegistrationHandle;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.SelectMustItemInfo;
import com.newland.wstdd.originate.origateactivity.OriginateChairActivity;

/**
 * ????????????
 * 
 * @author H81 2015-11-28
 * 
 */
public class ManagerApplyListActivity extends BaseFragmentActivity implements OnClickListener, OnPostListenerInterface {
	private static final String TAG = "ManagerApplyListActivity";//??????????????????tag
	final String FLAG = "right_tv_change";

	private String activityId;// ??????????????????
	public int signRole = 9;// ?????????????????? 1.?????? 2.?????? 9.??????
	private String isLeader;// ???????????????

	private LinearLayout applylist_bottom_ll;// ?????????????????????????????????
	private TextView exportListTv;// ????????????
	private TextView onlineListTv;// ????????????
	public MailDialog mailDialog;// ??????????????????????????????
	private List<BaseFragment> listFragments;
	private BaseFragment currentFragment;// ???????????????fragment
	private PassListFragment passListFragment;// ??????
	private NoPassListFragment noPassListFragment;// ?????????

	private LinearLayout mPassLayout;
	private LinearLayout mNoPassLayout;
	private TextView mPassTextView;
	private ImageView mPassImageView;
	private TextView mNoPassTextView;
	private ImageView mNoPassImageView;
	private android.support.v4.view.ViewPager mViewPager;

	TextView right_tv;// ???????????????

	// ????????????????????????
	RegistrationListRes registrationListRes;
	UpdateRegistrationListRes updateRegistrationListRes;
	MailUrlRes mailUrlRes;// ?????????????????????????????????
	RegistrationHandle handler = new RegistrationHandle(this);
	RegistrationHandle handlerUpdate = new RegistrationHandle(this);
	List<TddActivitySignVoInfo> tddActivitySigns = new ArrayList<TddActivitySignVoInfo>();
	static List<TddActivitySignVoInfo> allList = new ArrayList<TddActivitySignVoInfo>();
	private List<TddActivitySignVo> allTddActivitySignVos = new ArrayList<TddActivitySignVo>();// ??????????????????????????????

	private List<TddActivitySignVoInfo> signNoPassList = new ArrayList<TddActivitySignVoInfo>();// ?????????
	private List<TddActivitySignVoInfo> passAllList = new ArrayList<TddActivitySignVoInfo>();// ?????????????????????
	TddActivitySignVo tdTddActivitySignVo;// ??????

	public boolean hasTD = false;// ???????????????
	public int passNum = 0;// ???????????????
	public int noPassNum = 0;// ???????????????

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ????????????
		AppManager.getAppManager().addActivity(this);// ????????????Activity??????????????????
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// ??????????????????
		activityId = getIntent().getStringExtra("activityId");
		signRole = getIntent().getIntExtra("signRole", 0);
		isLeader = getIntent().getStringExtra("isLeader");
		setContentView(R.layout.activity_applylist);
		
		 /**??????????????????*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //????????????????????????????????????????????????????????????????????????????????????????????????
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**??????????????????*/
        
		setTitle();
		bindViews();
		// initFragment();
		getRegistrationListInfos();

		// ????????????
		// getData();
	}

	/**
	 * ?????????Fragment
	 * 
	 * @param passAllList2
	 *            ???????????????&??????
	 * @param signNoPassList2
	 *            ?????????&?????????
	 * @param tdTddActivitySignVo2
	 *            ??????
	 */
	private void initFragment(List<TddActivitySignVoInfo> signNoPassList2, List<TddActivitySignVoInfo> passAllList2, TddActivitySignVo tdTddActivitySignVo) {
		listFragments = new ArrayList<BaseFragment>();
		passListFragment = PassListFragment.newInstance(ManagerApplyListActivity.this);
		Bundle bundle2 = new Bundle();
		bundle2.putSerializable("passAllList", (Serializable) passAllList2);
		/*
		 * bundle2.putSerializable("tdTddActivitySignVo", (Serializable)
		 * tdTddActivitySignVo);
		 */
		bundle2.putSerializable("signRole", signRole);
		bundle2.putSerializable("isLeader", isLeader);
		passListFragment.setArguments(bundle2);

		noPassListFragment = NoPassListFragment.newInstance(ManagerApplyListActivity.this);
		Bundle bundle = new Bundle();
		bundle.putSerializable("signNoPassList", (Serializable) signNoPassList2);
		noPassListFragment.setArguments(bundle);

		listFragments.add(passListFragment);
		listFragments.add(noPassListFragment);

		// ?????? ??????????????????
		BaseFragmentPagerAdapter mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(pageListener);
		mViewPager.setOffscreenPageLimit(1);

		currentFragment = passListFragment;
	}

	/**
	 * ????????????
	 */
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null)
			center_tv.setText("????????????");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.left_expandsion));
			left_btn.setOnClickListener(this);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setText("??????");
			right_tv.setTextColor(getResources().getColor(R.color.originate_darkred));
			right_tv.setVisibility(View.VISIBLE);
			right_tv.setOnClickListener(this);
		}
	}

	private void bindViews() {
		onlineListTv = (TextView) findViewById(R.id.applist_online_tv);
		onlineListTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UiHelper.ShowOneToast(ManagerApplyListActivity.this, "????????????????????????,????????????");
			}
		});
		exportListTv = (TextView) findViewById(R.id.applist_exportlist_tv);
		exportListTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ?????????????????????

				showMailDialog();

			}
		});
		mPassLayout = (LinearLayout) findViewById(R.id.pass_ll);
		mNoPassLayout = (LinearLayout) findViewById(R.id.nopass_ll);
		mPassTextView = (TextView) findViewById(R.id.pass_tv);
		mPassImageView = (ImageView) findViewById(R.id.pass_iv);
		mNoPassTextView = (TextView) findViewById(R.id.nopass_tv);
		mNoPassImageView = (ImageView) findViewById(R.id.nopass_iv);
		mViewPager = (android.support.v4.view.ViewPager) findViewById(R.id.viewpager);
		mPassLayout.setOnClickListener(this);
		mNoPassLayout.setOnClickListener(this);

		applylist_bottom_ll = (LinearLayout) findViewById(R.id.applylist_bottom_ll);
	}

	/**
	 * ViewPager??????????????????
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {
			clearPress();
			mViewPager.setCurrentItem(position);
			currentFragment = listFragments.get(position);
			switch (position) {
			case 0:
				mPassImageView.setVisibility(View.VISIBLE);
				mPassTextView.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
				break;
			case 1:
				mNoPassImageView.setVisibility(View.VISIBLE);
				mNoPassTextView.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
				break;
			default:
				break;
			}
			currentFragment.refresh();
			// right_tv.setText("??????");
		}

	};

	private void clearPress() {
		mPassImageView.setVisibility(View.INVISIBLE);
		mNoPassImageView.setVisibility(View.INVISIBLE);

		mPassTextView.setTextColor(this.getResources().getColor(R.color.textgray));
		mNoPassTextView.setTextColor(this.getResources().getColor(R.color.textgray));

	}

	/**
	 * ???????????????viewpager??????????????????
	 * 
	 * @param num
	 */
	public void setPassTextViewData(int num) {
		mPassTextView.setText("?????????(" + num + ")");

	}

	/**
	 * ???????????????viewpager??????????????????
	 * 
	 * @param num
	 */
	public void setNoPassTextViewData(int num) {
		mNoPassTextView.setText("?????????(" + num + ")");
	}

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	public void refresh() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_left_iv:// ??????
			finish();
			break;
		case R.id.head_right_tv:// ??????
			if ("??????".equals(right_tv.getText().toString())) {
				right_tv.setText("??????");
				applylist_bottom_ll.setVisibility(View.GONE);
			} else if ("??????".equals(right_tv.getText().toString())) {
				right_tv.setText("??????");
				applylist_bottom_ll.setVisibility(View.VISIBLE);
				getUpdateRegistrationListInfos();
			}
			Intent intent = new Intent();
			intent.setAction(FLAG);
			sendBroadcast(intent);
			break;
		case R.id.pass_ll:
			clearPress();
			mPassImageView.setVisibility(View.VISIBLE);
			mPassTextView.setTextColor(this.getResources().getColor(R.color.red));
			mViewPager.setCurrentItem(0);
			break;
		case R.id.nopass_ll:
			clearPress();
			mNoPassImageView.setVisibility(View.VISIBLE);
			mNoPassTextView.setTextColor(this.getResources().getColor(R.color.red));
			mViewPager.setCurrentItem(1);
			break;
		default:
			break;
		}
	}

	/**
	 * 35. ????????????????????????
	 */
	private void getRegistrationListInfos() {
		super.refreshDialog();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					RegistrationListReq reqInfo = new RegistrationListReq();
					reqInfo.setActivityId(activityId);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<RegistrationListRes> ret = mgr.getRegistrationListInfo(reqInfo);// ????????????
					Message message = new Message();
					message.what = RegistrationHandle.REGISTRATION_LIST;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						registrationListRes = (RegistrationListRes) ret.getObj();
						message.obj = registrationListRes;
					} else {
						message.obj = ret.getMsg();
					}
					handlerUpdate.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 36. ??????????????????
	 */
	private void getUpdateRegistrationListInfos() {
		super.refreshDialog();
		try {
			new Thread() {
				public void run() {
					UpdateRegistrationListReq reqInfo = new UpdateRegistrationListReq();
					if (tdTddActivitySignVo != null) {// ????????????????????????????????????
						allTddActivitySignVos.add(tdTddActivitySignVo);
					}

					for (int i = 0; i < allList.size(); i++) {
						TddActivitySignVo tddActivitySignVo = new TddActivitySignVo();
						tddActivitySignVo = allList.get(i).getTddActivitySignVo();
						allTddActivitySignVos.add(tddActivitySignVo);
					}
					reqInfo.setTddActivitySigns(allTddActivitySignVos);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<UpdateRegistrationListRes> ret = mgr.getUpdateRegistrationListInfo(reqInfo);// ????????????
					Message message = new Message();
					message.what = RegistrationHandle.UPDATE_REGISTRATION_LIST;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						updateRegistrationListRes = new UpdateRegistrationListRes();
						updateRegistrationListRes.setGetResMess(StringUtil.noNull(ret.getMsg()));
						message.obj = updateRegistrationListRes;
					} else {
						message.obj = ret.getMsg();
					}
					handlerUpdate.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/***
	 * ????????????????????????
	 */
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			switch (responseId) {
			case RegistrationHandle.REGISTRATION_LIST:// ????????????????????????
				if (progressDialog != null) {
					progressDialog.setContinueDialog(false);

				}
				registrationListRes = (RegistrationListRes) obj;
				if (registrationListRes != null) {
					// ???????????????
					/*
					 * List<TddActivitySignVo>
					 * tempSignNoPassList=registrationListRes
					 * .getSignNoPassList(); for (int i = 0; i <
					 * tempSignNoPassList.size(); i++) { TddActivitySignVoInfo
					 * inActivitySignVoInfo = new TddActivitySignVoInfo();
					 * inActivitySignVoInfo
					 * .setTddActivitySignVo(tempSignNoPassList.get(i));
					 * inActivitySignVoInfo.setSelected(false);
					 * signNoPassList.add(inActivitySignVoInfo); }
					 * 
					 * //???????????? List<TddActivitySignVo>
					 * tempSignPassList=registrationListRes.getSignPassList();
					 * 
					 * for (int i = 0; i < tempSignPassList.size(); i++) {
					 * TddActivitySignVoInfo inActivitySignVoInfo = new
					 * TddActivitySignVoInfo();
					 * inActivitySignVoInfo.setTddActivitySignVo
					 * (tempSignPassList.get(i));
					 * inActivitySignVoInfo.setSelected(false);
					 * passAllList.add(inActivitySignVoInfo); }
					 */

					for (int i = 0, size = registrationListRes.getAllSign().size(); i < size; i++) {
						if (registrationListRes.getAllSign().get(i).getAuditStatus() == 2) {
							TddActivitySignVoInfo inActivitySignVoInfo = new TddActivitySignVoInfo();
							inActivitySignVoInfo.setTddActivitySignVo(registrationListRes.getAllSign().get(i));
							inActivitySignVoInfo.setSelected(false);
							passAllList.add(inActivitySignVoInfo);// ????????????
							if (registrationListRes.getAllSign().get(i).getSignRole() == 1) {
								tdTddActivitySignVo = registrationListRes.getAllSign().get(i);
							}
						} else {
							TddActivitySignVoInfo inActivitySignVoInfo = new TddActivitySignVoInfo();
							inActivitySignVoInfo.setTddActivitySignVo(registrationListRes.getAllSign().get(i));
							inActivitySignVoInfo.setSelected(false);
							signNoPassList.add(inActivitySignVoInfo);// ???????????????
						}

						/*
						 * if
						 * (registrationListRes.getAllSign().get(i).getSignRole
						 * ()==1){ tdTddActivitySignVo =
						 * registrationListRes.getAllSign().get(i); }else if
						 * (registrationListRes
						 * .getAllSign().get(i).getSignRole()==2 ||
						 * registrationListRes
						 * .getAllSign().get(i).getSignRole()==9) {//signRole
						 * ?????????????????? 1.?????? 2.?????? 9.?????? int TddActivitySignVoInfo
						 * inActivitySignVoInfo = new TddActivitySignVoInfo();
						 * inActivitySignVoInfo
						 * .setTddActivitySignVo(registrationListRes
						 * .getAllSign().get(i));
						 * inActivitySignVoInfo.setSelected(false);
						 * passAllList.add(inActivitySignVoInfo);//???????????? }else {
						 * TddActivitySignVoInfo inActivitySignVoInfo = new
						 * TddActivitySignVoInfo();
						 * inActivitySignVoInfo.setTddActivitySignVo
						 * (registrationListRes.getAllSign().get(i));
						 * inActivitySignVoInfo.setSelected(false);
						 * signNoPassList.add(inActivitySignVoInfo);//??????????????? }
						 */
					}

					allList = new ArrayList<TddActivitySignVoInfo>();
					for (int i = 0; i < registrationListRes.getAllSign().size(); i++) {
						TddActivitySignVoInfo tddActivitySignVoInfo = new TddActivitySignVoInfo();
						tddActivitySignVoInfo.setTddActivitySignVo(registrationListRes.getAllSign().get(i));
						tddActivitySignVoInfo.setSelected(false);
						allList.add(tddActivitySignVoInfo);
					}
					for (int i = 0; i < allList.size(); i++) {

						if (allList.get(i).getTddActivitySignVo().getAuditStatus() == 2 && allList.get(i).getTddActivitySignVo().getSignRole() == 1) {
							// tdTddActivitySignVo =
							// allList.get(i).getTddActivitySignVo();
							allList.remove(i);
						}
					}
					initFragment(signNoPassList, passAllList, tdTddActivitySignVo);

					setPassTextViewData(registrationListRes.getSignPassCount());
					setNoPassTextViewData(registrationListRes.getSignNoPassCount());
				}
				break;
			case RegistrationHandle.UPDATE_REGISTRATION_LIST:// ??????????????????
				if (progressDialog != null) {
					progressDialog.setContinueDialog(false);
				}
				updateRegistrationListRes = (UpdateRegistrationListRes) obj;
				if (updateRegistrationListRes != null) {
					UiHelper.ShowOneToast(this, "????????????");
				}
				break;

			case RegistrationHandle.MAIL_URL:// ??????????????????
				if (progressDialog != null) {
					progressDialog.setContinueDialog(false);
				}
				mailUrlRes = (MailUrlRes) obj;
				if (mailUrlRes != null) {
					mailDialog.dismiss();
					UiHelper.ShowOneToast(this, "????????????");
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void OnFailResultListener(String mess) {
		if (progressDialog != null) {
			progressDialog.setContinueDialog(false);
			UiHelper.ShowOneToast(this, mess);
		}
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	// ??????
	@SuppressLint("NewApi")
	private void showMailDialog() {
		final EditText editText = new EditText(ManagerApplyListActivity.this);
		if (AppContext.getAppContext().getEmail() != null && "".equals(AppContext.getAppContext().getEmail())) {
			editText.setText(AppContext.getAppContext().getEmail());
		}
//-------------------------------------------------------------
		editText.setBackground(null);
		AlertDialog dialog = new AlertDialog.Builder(ManagerApplyListActivity.this).setTitle("????????????").setMessage("?????????????????????").setView(editText)
				.setPositiveButton("??????", new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (EditTextUtil.checkEmail(editText.getText().toString())) {
							commitMailUrl(editText.getText().toString());// ????????????????????????
						} else {
							UiHelper.ShowOneToast(ManagerApplyListActivity.this, "?????????????????????!");
						}
					}
				}).setNegativeButton("??????", new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
		dialog.setCanceledOnTouchOutside(true);
		
	}

	/**
	 * ????????????????????????
	 */
	private void commitMailUrl(final String mailString) {
		// TODO Auto-generated method stub
		try {
			new Thread() {
				public void run() {

					// ??????????????????request?????????????????????
					MailUrlReq reqInfo = new MailUrlReq();
					reqInfo.setMailto(mailString);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<MailUrlRes> ret = mgr.getMailUrlInfo(reqInfo, activityId);// ????????????
					Message message = new Message();
					message.what = RegistrationHandle.MAIL_URL;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						if (ret.getObj() != null) {
							mailUrlRes = (MailUrlRes) ret.getObj();
							message.obj = mailUrlRes;
						} else {
							mailUrlRes = new MailUrlRes();
							message.obj = mailUrlRes;
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

	@Override
	protected void onDestroy() {
		/** ?????????????????? */
		LogManager.getManager(getApplicationContext()).unregisterActivity(this);
		/** ?????????????????? */
		System.out.println("onDestroy-----------------");
		super.onDestroy();
	}
}
