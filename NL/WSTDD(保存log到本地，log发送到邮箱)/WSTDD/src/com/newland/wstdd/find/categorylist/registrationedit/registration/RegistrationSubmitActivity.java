package com.newland.wstdd.find.categorylist.registrationedit.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.bean.TddActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.EditTextUtil;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.AdultInfo;
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.MainSignAttr;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.CancelRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.SubmitRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.CancelRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.EditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.SubmitRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.handle.CancelRegistrationHandle;
import com.newland.wstdd.find.categorylist.registrationedit.handle.SubmitRegistrationHandle;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
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
 * ??????????????????
 * 
 * @author Administrator
 * 
 */
public class RegistrationSubmitActivity extends BaseFragmentActivity implements OnPostListenerInterface, IWXAPIEventHandler {
	private static final String TAG = "RegistrationSubmitActivity";//??????????????????tag
	Intent intent;
	private String mainSignAttrs;// ?????????????????? ????????????????????????????????????
	TddActivity tddActivity;// ??????????????????????????????????????????????????? ????????????????????????
	// ???????????? ListView????????????
	SxRegistrationSubmitListViews sxListViews;
	SxRegistrationSubmitAdapter sxAdapter;
	List<SxRegistrationSubmitAdapterData> sxAdapterDatas = new ArrayList<SxRegistrationSubmitAdapterData>();
	// ????????????
	private List<MainSignAttr> mineAdapterDatas = new ArrayList<MainSignAttr>();
	RegistrationSubmitAdapter mineEditAdapter;// ????????????????????????????????????
	SxRegistrationSubmitListViews mineEditListViews;
	// ??????????????????
	TextView addTextView;// ?????????????????? ??????????????????
	TextView registrationActivityIcon, registrationActivityTitle;// ????????????????????????????????????
																	// ??? ????????????
	// ??????????????? ????????????????????????
	EditRegistrationRes submitRegistrationRes;
	SubmitRegistrationHandle handler = new SubmitRegistrationHandle(this);

	CancelRegistrationRes cancelRegistrationRes;
	CancelRegistrationHandle handlerCancel = new CancelRegistrationHandle(this);
	/**
	 * ???????????????
	 */
	private PopupWindow popupWindow;// ????????????
	// ??????
	private static final String appid = "wx1b84c30d9f380c89";// ?????????appid
	private IWXAPI wxApi;// ?????????API
	// QQ
	private Tencent mTencent;
	private static final String APP_ID = "1104957952";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ????????????
		AppManager.getAppManager().addActivity(this);// ????????????Activity??????????????????
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// ??????????????????
		setContentView(R.layout.activity_registration_submit);
		
		 /**??????????????????*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //????????????????????????????????????????????????????????????????????????????????????????????????
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**??????????????????*/
        
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		tddActivity = (TddActivity) bundle.getSerializable("tddActivity");
		initTitle();// ???????????????
		initView();// ???????????????
		initMustSelect();// ??????????????????
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
	}
	@Override
    protected void onDestroy() {
    	 /**??????????????????*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**??????????????????*/
    	super.onDestroy();
    }
	// ????????????????????????
	private void initMustSelect() {
		// TODO Auto-generated method stub
		if (tddActivity != null) {
			mainSignAttrs = tddActivity.getSignAttr();
			// ?????????????????????????????????????????????????????????
			if (mainSignAttrs.endsWith(",")) {
				mainSignAttrs = mainSignAttrs.substring(0, mainSignAttrs.length() - 1);
			}
			if (mainSignAttrs != null && !"".equals(mainSignAttrs)) {
				String[] strs = mainSignAttrs.split(",");
				// ???String????????????list
				for (String substr : strs) {
					MainSignAttr mainSignAttr = new MainSignAttr();
					mainSignAttr.setName(substr);
					if ("??????".equals(substr)) {
						mainSignAttr.setValue(tddActivity.getUserName());
					} else if ("??????".equals(substr)) {
						mainSignAttr.setValue(tddActivity.getUserMobilePhone());
					} else {
						mainSignAttr.setValue(null);
					}
					// mainSignAttr.setValue(null);
					mineAdapterDatas.add(mainSignAttr);
				}
				mineEditAdapter.setRegistrationData(mineAdapterDatas);
				mineEditListViews.setAdapter(mineEditAdapter);
				mineEditAdapter.notifyDataSetChanged();
			} else {
				MainSignAttr mainSignAttr = new MainSignAttr();
				mainSignAttr.setName("??????");
				mainSignAttr.setValue(tddActivity.getUserName());

				MainSignAttr mainSignAttr1 = new MainSignAttr();
				mainSignAttr1.setName("??????");
				mainSignAttr1.setValue(tddActivity.getUserMobilePhone());

				mineAdapterDatas.add(mainSignAttr);
				mineAdapterDatas.add(mainSignAttr1);
				mineEditAdapter.setRegistrationData(mineAdapterDatas);
				mineEditListViews.setAdapter(mineEditAdapter);
				mineEditAdapter.notifyDataSetChanged();
			}
		}

	}

	/**
	 * ????????????
	 * 
	 */
	private void initTitle() {
		ImageView leftBtn = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		centerTitle.setText("??????????????????");
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		ImageButton rightBtn = (ImageButton) findViewById(R.id.head_right_btn);
		leftBtn.setVisibility(View.VISIBLE);
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setTextColor(getResources().getColor(R.color.text_red));
		rightTv.setText("??????");
		rightBtn.setVisibility(View.GONE);
		rightBtn.setImageDrawable(getResources().getDrawable(R.drawable.find));
		leftBtn.setOnClickListener(this);
		rightTv.setOnClickListener(this);
	}


	public void initView() {
		// TODO Auto-generated method stub
		registrationActivityIcon = (TextView) findViewById(R.id.activity_mine_personalcenter_icon_tv);
		registrationActivityTitle = (TextView) findViewById(R.id.activity_mine_personalcenter_title_tv);
		registrationActivityIcon.setText(StringUtil.intType2Str(tddActivity.getActivityType()));
		registrationActivityTitle.setText(tddActivity.getActivityTitle());
		sxListViews = (SxRegistrationSubmitListViews) findViewById(R.id.registration_sx_listview);
		mineEditListViews = (SxRegistrationSubmitListViews) findViewById(R.id.registration_listview);
		sxAdapter = new SxRegistrationSubmitAdapter(this, sxAdapterDatas);
		sxListViews.setAdapter(sxAdapter);
		mineEditAdapter = new RegistrationSubmitAdapter(this, mineAdapterDatas);
		addTextView = (TextView) findViewById(R.id.registration_add_people);
		addTextView.setOnClickListener(this);
	}

	// ????????????
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.registration_add_people:
			// ???????????????????????????????????????????????????????????????????????????
			if (sxAdapterDatas.size() > 0) {
				if (sxAdapterDatas.get(sxAdapterDatas.size() - 1).getName() == null || sxAdapterDatas.get(sxAdapterDatas.size() - 1).getPhone() == null
						|| "".equals(sxAdapterDatas.get(sxAdapterDatas.size() - 1).getName()) || "".equals(sxAdapterDatas.get(sxAdapterDatas.size() - 1).getPhone())) {
					UiHelper.ShowOneToast(this, "??????????????????????????????????????????");
				} else {

					SxRegistrationSubmitAdapterData data = new SxRegistrationSubmitAdapterData();
					data.setName(null);
					data.setPhone(null);
					List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
					List<Map<String, String>> tempInputMaplist = new ArrayList<Map<String, String>>();
					List<Map<String, String>> tempLastMaplist = new ArrayList<Map<String, String>>();
					if (tddActivity != null) {
						mainSignAttrs = tddActivity.getSignAttr();
						// ?????????????????????????????????????????????????????????
						if (mainSignAttrs.endsWith(",")) {
							mainSignAttrs = mainSignAttrs.substring(0, mainSignAttrs.length() - 1);
						}
						if (mainSignAttrs != null && !"".equals(mainSignAttrs)) {
							String[] strs = mainSignAttrs.split(",");
							// ???String????????????list
							for (String substr : strs) {
								Map<String, String> map = new HashMap<String, String>();
								map.put(substr, null);
								maplist.add(map);
							}
							for (String substr : strs) {
								Map<String, String> map = new HashMap<String, String>();
								map.put(substr, null);
								tempInputMaplist.add(map);
							}
							for (String substr : strs) {
								Map<String, String> map = new HashMap<String, String>();
								map.put(substr, null);
								tempLastMaplist.add(map);
							}
						} else {
							Map<String, String> map0 = new HashMap<String, String>();
							map0.put("??????", null);
							Map<String, String> map1 = new HashMap<String, String>();
							map1.put("??????", null);
							maplist.add(map0);
							maplist.add(map1);

							Map<String, String> map2 = new HashMap<String, String>();
							map2.put("??????", null);
							Map<String, String> map3 = new HashMap<String, String>();
							map3.put("??????", null);
							tempInputMaplist.add(map2);
							tempInputMaplist.add(map3);

							Map<String, String> map4 = new HashMap<String, String>();
							map4.put("??????", null);
							Map<String, String> map5 = new HashMap<String, String>();
							map5.put("??????", null);
							tempLastMaplist.add(map4);
							tempLastMaplist.add(map5);

						}

					} else {
						UiHelper.ShowOneToast(RegistrationSubmitActivity.this, "?????????????????????????????????????????????");
					}
					data.setMap(maplist);
					data.setInputTempList(tempInputMaplist);
					data.setLastTempList(tempLastMaplist);
					data.setShowRl1(false);
					data.setShowRl2(true);
					data.setShowListView(true);
					data.setInParent(sxAdapterDatas.size());
					sxAdapterDatas.add(data);
					sxAdapter.setRegistrationData(sxAdapterDatas);
					sxAdapter.notifyDataSetChanged();
				}
			} else {
				// ????????????????????????????????????????????????0?????????
				SxRegistrationSubmitAdapterData data = new SxRegistrationSubmitAdapterData();
				data.setName(null);
				data.setPhone(null);
				List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
				List<Map<String, String>> tempInputMaplist = new ArrayList<Map<String, String>>();
				List<Map<String, String>> tempLastMaplist = new ArrayList<Map<String, String>>();
				if (tddActivity != null) {
					mainSignAttrs = tddActivity.getSignAttr();
					// ?????????????????????????????????????????????????????????
					if (mainSignAttrs.endsWith(",")) {
						mainSignAttrs = mainSignAttrs.substring(0, mainSignAttrs.length() - 1);
					}
					if (mainSignAttrs != null && !"".equals(mainSignAttrs)) {
						String[] strs = mainSignAttrs.split(",");
						// ???String????????????list
						for (String substr : strs) {
							Map<String, String> map = new HashMap<String, String>();
							map.put(substr, null);
							maplist.add(map);
						}
						for (String substr : strs) {
							Map<String, String> map = new HashMap<String, String>();
							map.put(substr, null);
							tempInputMaplist.add(map);
						}
						for (String substr : strs) {
							Map<String, String> map = new HashMap<String, String>();
							map.put(substr, null);
							tempLastMaplist.add(map);
						}
					} else {
						Map<String, String> map0 = new HashMap<String, String>();
						map0.put("??????", null);
						Map<String, String> map1 = new HashMap<String, String>();
						map1.put("??????", null);
						maplist.add(map0);
						maplist.add(map1);

						Map<String, String> map2 = new HashMap<String, String>();
						map2.put("??????", null);
						Map<String, String> map3 = new HashMap<String, String>();
						map3.put("??????", null);
						tempInputMaplist.add(map2);
						tempInputMaplist.add(map3);

						Map<String, String> map4 = new HashMap<String, String>();
						map4.put("??????", null);
						Map<String, String> map5 = new HashMap<String, String>();
						map5.put("??????", null);
						tempLastMaplist.add(map4);
						tempLastMaplist.add(map5);

					}

				} else {
					UiHelper.ShowOneToast(RegistrationSubmitActivity.this, "?????????????????????????????????????????????");
				}
				data.setMap(maplist);
				data.setInputTempList(tempInputMaplist);
				data.setLastTempList(tempLastMaplist);
				data.setShowRl1(false);
				data.setShowRl2(true);
				data.setShowListView(true);
				data.setInParent(0);// ????????????????????????????????????
				sxAdapterDatas.add(data);
				sxAdapter.setRegistrationData(sxAdapterDatas);
				sxAdapter.notifyDataSetChanged();

			}
			break;
		case R.id.head_right_tv:
			// ??????????????????????????????
			boolean isEmpty = false;
			mineAdapterDatas = mineEditAdapter.getRegistrationData();
			for (int i = 0; i < mineAdapterDatas.size(); i++) {
				if ("".equals(mineAdapterDatas.get(i).getValue())) {
					UiHelper.ShowOneToast(this, "??????????????????");
					isEmpty = true;
					break;
				} else if ("??????".equals(mineAdapterDatas.get(i).getName())) {
					if (!EditTextUtil.checkMobileNumber(mineAdapterDatas.get(i).getValue())) {
						UiHelper.ShowOneToast(this, "??????????????????????????????");
						isEmpty = true;
						break;
					}
				} else if ("??????".equals(mineAdapterDatas.get(i).getName())) {
					if (!EditTextUtil.checkEmail(mineAdapterDatas.get(i).getValue())) {
						UiHelper.ShowOneToast(this, "??????????????????????????????");
						isEmpty = true;
						break;
					}
				} else if ("?????????".equals(mineAdapterDatas.get(i).getName())) {
					if (!EditTextUtil.checkChinaIDCard(mineAdapterDatas.get(i).getValue())) {
						UiHelper.ShowOneToast(this, "?????????????????????????????????");
						isEmpty = true;
						break;
					}
				} else if ("??????".equals(mineAdapterDatas.get(i).getName())) {
					if (!"???".equals(mineAdapterDatas.get(i).getValue()) && !"???".equals(mineAdapterDatas.get(i).getValue())) {
						UiHelper.ShowOneToast(this, "????????????????????????????????????");
						isEmpty = true;
						break;
					}
				} else {
					continue;
				}

			}
			if (!isEmpty) {
				refreshSumit();
//				submitCheck();
			}

			break;
		default:
			break;
		}

	}

	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	public void refresh() {

	}

	// ?????????????????????????????????
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			if (progressDialog != null) {
				progressDialog.setContinueDialog(false);
			}
			switch (responseId) {
			case SubmitRegistrationHandle.SUBMIT_REGISTRATION:
				submitRegistrationRes = (EditRegistrationRes) obj;
				if (submitRegistrationRes != null) {
					String mess = null;
					mess = submitRegistrationRes.getGetResMess();
					/**
					 * ??????????????????????????????
					 */
					// ????????????????????????????????????
					Intent intent1 = new Intent();
					intent1.setAction("ManagerPageActivityRefresh_Cancel_Regist");
					intent1.putExtra("SignState", "Sign");
					sendBroadcast(intent1);
					if (null != AppContext.getAppContext())
						AppContext.getAppContext().setMySignAcNum(Integer.valueOf(AppContext.getAppContext().getMySignAcNum()) + 1 + "");
					getPopWindow();
					
					sendSignedNumAddBroadCast();
				}
				break;

			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ??????????????????????????????
	 */
	private void sendSignedNumAddBroadCast() {
		Intent intent = new Intent();
		intent.setAction("SignedNumChange");
		intent.putExtra("activityId", tddActivity.getActivityId());
		intent.putExtra("signNumType", "add");
		sendBroadcast(intent);
	}

	@Override
	public void OnFailResultListener(String mess) {
		if (progressDialog != null) {
			progressDialog.setContinueDialog(false);
			UiHelper.ShowOneToast(this, mess);
		}
	}

	/**
	 * ????????????????????? ????????????
	 */
	private void refreshSumit() {
		super.refreshDialog();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					SubmitRegistrationReq reqInfo = new SubmitRegistrationReq();
					// ???????????????
					mineAdapterDatas = mineEditAdapter.getRegistrationData();
					reqInfo.setMainSignAttr(mineAdapterDatas);
					// ????????????????????????
					List<AdultInfo> adultInfos = new ArrayList<AdultInfo>();
					for (int i = 0; i < sxAdapterDatas.size(); i++) {
						// ?????????adultInfos
						AdultInfo adultInfo = new AdultInfo();
						adultInfo.setAdultPersonType(2);
						adultInfo.setAdultUserName(sxAdapter.getRegistrationData().get(i).getName());
						List<MainSignAttr> mainSignAttrs = new ArrayList<MainSignAttr>();
						List<Map<String, String>> values = sxAdapter.getRegistrationData().get(i).getMap();
						for (Map<String, String> map : values) {
							for (Entry<String, String> entry : map.entrySet()) {
								Object key = entry.getKey();
								Object val = entry.getValue();
								MainSignAttr mainSignAttr = new MainSignAttr();
								if (!"".equals((String) key) && key != null) {
									mainSignAttr.setName((String) key);
								}
								if (!"".equals((String) val) && val != null) {
									mainSignAttr.setValue((String) val);
								}
								// ???????????????????????????????????????????????? ?????????
								if (!"".equals(mainSignAttr.getName()) && !"".equals(mainSignAttr.getValue()) && mainSignAttr.getValue() != null && mainSignAttr.getName() != null) {
									mainSignAttrs.add(mainSignAttr);
								} else {
									break;
								}

							}
						}
						if (mainSignAttrs.size() > 0) {
							adultInfo.setAdultSignAttr(mainSignAttrs);
							adultInfos.add(adultInfo);
						}

					}
					reqInfo.setAdultInfos(adultInfos);
					// ????????????
					reqInfo.setPersonType(1);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SubmitRegistrationRes> ret = mgr.getSubmitRegistrationInfo(reqInfo, tddActivity.getActivityId());// ????????????
					Message message = new Message();
					message.what = SubmitRegistrationHandle.SUBMIT_REGISTRATION;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						submitRegistrationRes = new EditRegistrationRes();
						submitRegistrationRes.setGetResMess(StringUtil.noNull(ret.getMsg()));
						message.obj = submitRegistrationRes;
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
	 */
	private void refreshCancelReg() {
		super.refreshDialog();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					CancelRegistrationReq reqInfo = new CancelRegistrationReq();
					reqInfo.setActivityId("activityID");
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<CancelRegistrationRes> ret = mgr.getCancelRegistrationInfo(reqInfo);// ????????????
					Message message = new Message();
					message.what = CancelRegistrationHandle.CANCEL_REGISTRATION;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						cancelRegistrationRes = new CancelRegistrationRes();
						cancelRegistrationRes.setGetResMess(StringUtil.noNull(ret.getMsg()));
						message.obj = cancelRegistrationRes;
					} else {
						message.obj = ret.getMsg();
					}
					handlerCancel.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ?????????????????????
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
		View popupWindow_view = this.getLayoutInflater().inflate(R.layout.popwindow_registration_shares, null, false);
		TextView public_type_tv = (TextView) popupWindow_view.findViewById(R.id.public_type_tv);// ????????????
		TextView public_title_tv = (TextView) popupWindow_view.findViewById(R.id.public_title_tv);// ????????????
		public_type_tv.setText(StringUtil.intType2Str(tddActivity.getActivityType()));
		public_title_tv.setText(registrationActivityTitle.getText().toString());
		popupWindow = new PopupWindow(popupWindow_view, AppContext.getAppContext().getScreenWidth() * 4 / 5, LayoutParams.WRAP_CONTENT, true);
		ImageView cancelImageView = (ImageView) popupWindow_view.findViewById(R.id.originate_chair_popwindow_delete);
		cancelImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				Intent intent = new Intent();
				intent.setAction("Refresh_FindChairDetailActivity");
				intent.putExtra("registration_state", "?????????");
				sendBroadcast(intent);
				finish();

			}
		});
		popupWindow.setOutsideTouchable(false);
		PengTextView weixinFriend, weixinZone, qqFriend;// ????????????????????????
		weixinFriend = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin);
		weixinZone = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_weixin_zone);
		qqFriend = (PengTextView) popupWindow_view.findViewById(R.id.activity_find_chairdetail_pop_qq);
		// ??????????????????
		weixinFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				friend(v);
			}
		});

		// ?????????????????????
		weixinZone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				friendline(v);
			}
		});

		// qq????????????
		qqFriend.setOnClickListener(new OnClickListener() {

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

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		// ???????????? ??????
		popupWindow.showAtLocation(this.findViewById(R.id.layout), Gravity.CENTER, 0, 0);//
		popupWindow.setOutsideTouchable(false);

	}

	/**
	 * ?????????????????????
	 * 
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
		if (tddActivity.getShareContent() != null && tddActivity.getShareImg() != null && tddActivity.getShareUrl() != null) {

			ImageLoader.getInstance().loadImage(tddActivity.getShareImg(), new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub

				}
			});
		} else {
			UiHelper.ShowOneToast(RegistrationSubmitActivity.this, "????????????????????????????????????!");
			finish();
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
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
		mTencent.shareToQQ(RegistrationSubmitActivity.this, params, new BaseUiListener() {
			protected void doComplete(JSONObject values) {
				showResult("shareToQQ:", "????????????");
			}

			@Override
			public void onError(UiError e) {
				showResult("shareToQQ:", "????????????????????????????????????");
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
			showResult("onError:", "????????????????????????????????????");
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "????????????");
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
				UiHelper.ShowOneToast(RegistrationSubmitActivity.this, msg);
//				popupWindow.dismiss();
//				finish();
			}
		});
	}

	/**
	 * ????????????
	 * 
	 */
	private void submitCheck() {
		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("????????????").setMessage("???????????????").setPositiveButton("??????", new android.content.DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// ?????????????????????????????????????????????????????????

				refreshSumit();
			}
		}).setNegativeButton("??????", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
	}

}
