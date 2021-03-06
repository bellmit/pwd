package com.newland.wstdd.find.categorylist.registrationedit.editregistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.content.Context;
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
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.AdultInfo;
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.MainSignAttr;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.CancelRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.GetEditRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanrequest.SubmitRegistrationReq;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.CancelRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.EditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.GetEditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.SubmitRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.handle.CancelRegistrationHandle;
import com.newland.wstdd.find.categorylist.registrationedit.handle.EditCancelRegistrationHandle;
import com.newland.wstdd.find.categorylist.registrationedit.handle.EditRegistrationHandle;
import com.newland.wstdd.find.categorylist.registrationedit.handle.SubmitRegistrationHandle;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
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
 * ??????????????????
 * 
 * @author Administrator
 * 
 */
public class EditRegistrationEditActivity extends BaseFragmentActivity implements OnPostListenerInterface, IWXAPIEventHandler {

	Intent intent;
	private String mainSignAttrs;// ?????????????????? ????????????????????????????????????
	SingleActivityRes singleActivityRes;// ??????????????????????????????????????????????????? ????????????????????????
	// ???????????? ListView????????????
	EditSxRegistrationEditListViews sxListViews;
	EditSxRegistrationEditAdapter sxAdapter;
	List<EditSxRegistrationEditAdapterData> sxAdapterDatas = new ArrayList<EditSxRegistrationEditAdapterData>();
	// ????????????
	private List<MainSignAttr> mineAdapterDatas = new ArrayList<MainSignAttr>();
	EditRegistrationEditAdapter mineEditAdapter;// ????????????????????????????????????
	EditSxRegistrationEditListViews mineEditListViews;
	// private List<String> mineAdapterDatas = new ArrayList<String>();
	// ??????????????????
	TextView addTextView;// ?????????????????? ??????????????????
	TextView registrationActivityIcon, registrationActivityTitle;// ????????????????????????????????????
																	// ??? ????????????
	//????????????????????????
	EditRegistrationRes submitRegistrationRes;
	EditRegistrationHandle handler = new EditRegistrationHandle(this);

	// ????????????
	private TextView cancelTextView;//????????????
	CancelRegistrationRes cancelRegistrationRes;
	EditRegistrationHandle cancelHandle = new EditRegistrationHandle(this);
	// ??????????????????
	GetEditRegistrationRes getEditRegistrationRes;// ?????????????????? ???????????????
	EditRegistrationHandle getEditRegistrationHandle = new EditRegistrationHandle(this);
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
		setContentView(R.layout.activity_registration_edit);
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		singleActivityRes = (SingleActivityRes) bundle.getSerializable("singleActivity");
		initTitle();// ???????????????
		initView();// ???????????????
		getEditRegistrationReq();// ?????????????????????????????????
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

	/**-----------------------??????????????????????????????-----------------*/
	/**
	 * ????????????????????? ????????????
	 */
	private void refreshSumit() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					SubmitRegistrationReq reqInfo = new SubmitRegistrationReq();
					mineAdapterDatas=mineEditAdapter.getRegistrationData();
					// ???????????????
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
								mainSignAttrs.add(mainSignAttr);
							}
						}
						adultInfo.setAdultSignAttr(mainSignAttrs);
						adultInfos.add(adultInfo);
					}
					reqInfo.setAdultInfos(adultInfos);
					// ????????????
					reqInfo.setPersonType(1);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SubmitRegistrationRes> ret = mgr.getOkEditRegistrationInfo(reqInfo, singleActivityRes.getSignId());// ????????????
					Message message = new Message();
					message.what = EditRegistrationHandle.OK_EDIT_REGISTRATION;// ?????????
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
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					CancelRegistrationReq reqInfo = new CancelRegistrationReq();
					reqInfo.setActivityId(singleActivityRes.getTddActivity().getActivityId());
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<CancelRegistrationRes> ret = mgr.getCancelRegistrationInfo(reqInfo);// ????????????
					Message message = new Message();
					message.what = EditRegistrationHandle.CANCEL_REGISTRATION;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						cancelRegistrationRes = new CancelRegistrationRes();
						cancelRegistrationRes.setGetResMess(StringUtil.noNull(ret.getMsg()));
						message.obj = cancelRegistrationRes;
					} else {
						message.obj = ret.getMsg();
					}
					cancelHandle.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	// ???????????? ------ ???????????? ?????? ??????
	private void getEditRegistrationReq() {
		// TODO Auto-generated method stub
		try {
			new Thread() {
				public void run() {
					// ??????????????????request?????????????????????
					GetEditRegistrationReq reqInfo = new GetEditRegistrationReq();
					reqInfo.setSignId(singleActivityRes.getSignId());
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<GetEditRegistrationRes> ret = mgr.getEditRegistrationInfo(reqInfo);// ????????????
					Message message = new Message();
					message.what = EditRegistrationHandle.GET_EDIT_REGISTRATION;// ?????????
					// ????????????????????? 1 ???????????????????????????
					if (ret.getCode() == 1) {
						if (ret.getObj() != null) {
							getEditRegistrationRes = (GetEditRegistrationRes) ret.getObj();
							message.obj = getEditRegistrationRes;
						} else {
							getEditRegistrationRes = new GetEditRegistrationRes();
							message.obj = getEditRegistrationRes;
						}
					} else {
						message.obj = ret.getMsg();
					}
					getEditRegistrationHandle.sendMessage(message);
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

	private void test() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2; i++) {
			EditSxRegistrationEditAdapterData data = new EditSxRegistrationEditAdapterData();
			data.setName("?????????");
			data.setPhone("18750736798");
			List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("??????", "?????????" + i);
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("??????", "18750736798");
			maplist.add(map1);
			maplist.add(map3);
			data.setMap(maplist);
			data.setShowRl1(true);
			data.setShowRl2(false);
			data.setShowListView(false);
			data.setInParent(i);
			sxAdapterDatas.add(data);
		}
		/**
		 * ??????????????????????????????????????????????????????????????????????????????
		 */
		sxAdapter.setRegistrationData(sxAdapterDatas);
		sxListViews.setAdapter(sxAdapter);
		sxAdapter.notifyDataSetChanged();
	}

	public void initView() {
		// TODO Auto-generated method stub
		registrationActivityIcon = (TextView) findViewById(R.id.activity_mine_personalcenter_icon_tv);
		registrationActivityTitle = (TextView) findViewById(R.id.activity_mine_personalcenter_title_tv);
		registrationActivityIcon.setText(StringUtil.intType2Str(singleActivityRes.getTddActivity().getActivityType()));
		registrationActivityTitle.setText(singleActivityRes.getTddActivity().getActivityTitle());
		sxListViews = (EditSxRegistrationEditListViews) findViewById(R.id.registration_sx_listview);
		mineEditListViews = (EditSxRegistrationEditListViews) findViewById(R.id.registration_listview);
		sxAdapter = new EditSxRegistrationEditAdapter(this, sxAdapterDatas);
		sxListViews.setAdapter(sxAdapter);
		mineEditAdapter = new EditRegistrationEditAdapter(this, mineAdapterDatas);
		cancelTextView = (TextView) findViewById(R.id.edit_registration_cancel_registration);
		cancelTextView.setOnClickListener(this);
		addTextView = (TextView) findViewById(R.id.edit_registration_addsx_registration);
		addTextView.setOnClickListener(this);
	}

	// ????????????
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		//??????????????????
		case R.id.edit_registration_addsx_registration:
			// ???????????????????????????????????????????????????????????????????????????
			if (sxAdapterDatas.size() > 0) {
				if (sxAdapterDatas.get(sxAdapterDatas.size() - 1).getName() == null || sxAdapterDatas.get(sxAdapterDatas.size() - 1).getPhone() == null
						|| "".equals(sxAdapterDatas.get(sxAdapterDatas.size() - 1).getName()) || "".equals(sxAdapterDatas.get(sxAdapterDatas.size() - 1).getPhone())) {
					UiHelper.ShowOneToast(this, "??????????????????????????????????????????11111");
				} else {

					EditSxRegistrationEditAdapterData data = new EditSxRegistrationEditAdapterData();
					data.setName(null);
					data.setPhone(null);
					List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
					List<Map<String, String>> tempInputMaplist = new ArrayList<Map<String, String>>();
					List<Map<String, String>> tempLastMaplist = new ArrayList<Map<String, String>>();
					if (singleActivityRes.getTddActivity() != null) {
						mainSignAttrs = singleActivityRes.getTddActivity().getSignAttr();
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
						}

					} else {
						UiHelper.ShowOneToast(EditRegistrationEditActivity.this, "????????????????????????????????????????????????");
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
				EditSxRegistrationEditAdapterData data = new EditSxRegistrationEditAdapterData();
				data.setName(null);
				data.setPhone(null);
				List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
				List<Map<String, String>> tempInputMaplist = new ArrayList<Map<String, String>>();
				List<Map<String, String>> tempLastMaplist = new ArrayList<Map<String, String>>();
				if (singleActivityRes.getTddActivity() != null) {
					mainSignAttrs = singleActivityRes.getTddActivity().getSignAttr();
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
					}
				} else {
					UiHelper.ShowOneToast(EditRegistrationEditActivity.this, "?????????????????????????????????????????????");
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
			//????????????
			refreshSumit();
			break;
		case R.id.edit_registration_cancel_registration:
			refreshCancelReg();// ????????????
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
	
	//???????????????????????????
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			if (dialog != null) {
				dialog.dismiss();
			}
			switch (responseId) {
			// ?????????????????? ??????  ???????????????
			case EditRegistrationHandle.GET_EDIT_REGISTRATION://0
				getEditRegistrationRes = (GetEditRegistrationRes) obj;
				if (getEditRegistrationRes != null) {
					if (getEditRegistrationRes.getJoinPerson() != null && getEditRegistrationRes.getJoinPerson().size() > 0) {
						for (int i = 0; i < getEditRegistrationRes.getJoinPerson().size(); i++) {
							if (getEditRegistrationRes.getJoinPerson().get(i).getPersonType() == 1) {
								String tempMainSign = getEditRegistrationRes.getJoinPerson().get(i).getSignAttr();
								if (tempMainSign != null && !"".equals(tempMainSign)) {

									JSONObject jsonObj = new JSONObject(tempMainSign);
									@SuppressWarnings("unchecked")
									Iterator<String> nameItr = jsonObj.keys();
									String name;
									mineAdapterDatas.clear();
									while (nameItr.hasNext()) {
										name = nameItr.next();
										MainSignAttr mainSignAttr = new MainSignAttr();
										mainSignAttr.setName(name);
										mainSignAttr.setValue(jsonObj.getString(name));
										mineAdapterDatas.add(mainSignAttr);
									}
									Collections.reverse(mineAdapterDatas);// ?????????????????????json?????????list?????????????????????????????????
																			
									mineEditAdapter.setRegistrationData(mineAdapterDatas);
									mineEditListViews.setAdapter(mineEditAdapter);
									mineEditAdapter.notifyDataSetChanged();

								}
								// ????????????????????????????????????
								else {
									MainSignAttr mainSignAttr = new MainSignAttr();
									mainSignAttr.setName("??????");
									mainSignAttr.setValue(singleActivityRes.getTddActivity().getUserName());
									MainSignAttr mainSignAttr1 = new MainSignAttr();
									mainSignAttr1.setName("??????");
									mainSignAttr1.setValue(singleActivityRes.getTddActivity().getUserMobilePhone());
									mineAdapterDatas.add(mainSignAttr);
									mineAdapterDatas.add(mainSignAttr1);
									mineEditAdapter.setRegistrationData(mineAdapterDatas);
									mineEditListViews.setAdapter(mineEditAdapter);
									mineEditAdapter.notifyDataSetChanged();
								}
							}
						}
						sxAdapterDatas.clear();
						//????????? ?????????????????????
						for (int i = 0; i < getEditRegistrationRes.getJoinPerson().size(); i++) {
							if (getEditRegistrationRes.getJoinPerson().get(i).getPersonType() == 2) {
							String tempMainSign = getEditRegistrationRes.getJoinPerson().get(i).getSignAttr();
							if (tempMainSign != null && !"".equals(tempMainSign)) {
							JSONObject jsonObj = new JSONObject(tempMainSign);
							@SuppressWarnings("unchecked")
							Iterator<String> nameItr = jsonObj.keys();
							String name;
							EditSxRegistrationEditAdapterData data = new EditSxRegistrationEditAdapterData();
							List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
							List<Map<String, String>> tempInputMaplist = new ArrayList<Map<String, String>>();
							List<Map<String, String>> tempLastMaplist = new ArrayList<Map<String, String>>();
							while (nameItr.hasNext()) {
								
								name = nameItr.next();
								if("??????".equals(name)){
									data.setName(jsonObj.getString(name));
								}
								if("??????".equals(name)){
									data.setPhone(jsonObj.getString(name));
								}
								//?????????hashmap
								Map<String, String> map = new HashMap<String, String>();
								map.put(name, jsonObj.getString(name));
								maplist.add(map);
								//?????????hashmap
								Map<String, String> map1 = new HashMap<String, String>();
								map1.put(name, jsonObj.getString(name));
								tempInputMaplist.add(map1);
								//?????????
								Map<String, String> map2 = new HashMap<String, String>();
								map2.put(name, jsonObj.getString(name));
								tempLastMaplist.add(map2);	
							}	
							Collections.reverse(maplist);
							Collections.reverse(tempInputMaplist);
							Collections.reverse(tempLastMaplist);
							data.setMap(maplist);
							data.setInputTempList(tempInputMaplist);
							data.setLastTempList(tempLastMaplist);
							data.setShowRl1(true);
							data.setShowRl2(false);
							data.setShowListView(false);
							data.setInParent(sxAdapterDatas.size());
							sxAdapterDatas.add(data);
//							Collections.reverse(sxAdapterDatas);// ?????????????????????json?????????list?????????????????????????????????
						    sxAdapter.setRegistrationData(sxAdapterDatas);
						    sxListViews.setAdapter(sxAdapter);
						    sxAdapter.notifyDataSetChanged();
						}
					}	
				}
						
					}
				}
				break;
			//???????????????????????????
			case  EditRegistrationHandle.OK_EDIT_REGISTRATION://1
				submitRegistrationRes = (EditRegistrationRes) obj;
				if (submitRegistrationRes != null) {
					String mess = null;
					mess = submitRegistrationRes.getGetResMess();
					/**
					 * ??????????????????????????????
					 */
					getPopWindow();
				}
				break;
			case EditRegistrationHandle.CANCEL_REGISTRATION:
				cancelRegistrationRes = (CancelRegistrationRes) obj;
				if(cancelRegistrationRes!=null){
					UiHelper.ShowOneToast(this, "??????????????????");
					Intent intent = new Intent();
					intent.setAction("Refresh_FindChairDetailActivity");
					intent.putExtra("registration_state", "????????????");
					sendBroadcast(intent);
					finish();
					
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
		if (dialog != null) {
			dialog.dismiss();
		}
		// TODO Auto-generated method stub

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
		if (singleActivityRes.getTddActivity().getShareContent() != null && singleActivityRes.getTddActivity().getShareImg() != null && singleActivityRes.getTddActivity().getShareUrl() != null) {

			ImageLoader.getInstance().loadImage(singleActivityRes.getTddActivity().getShareImg(), new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub
					// ????????????
					WXWebpageObject webpage = new WXWebpageObject();
					webpage.webpageUrl = singleActivityRes.getTddActivity().getShareUrl();
					WXMediaMessage msg = new WXMediaMessage(webpage);
					msg.title = singleActivityRes.getTddActivity().getActivityTitle();
					msg.description = singleActivityRes.getTddActivity().getActivityDescription();
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
					webpage.webpageUrl = singleActivityRes.getTddActivity().getShareUrl();
					WXMediaMessage msg = new WXMediaMessage(webpage);
					msg.title = singleActivityRes.getTddActivity().getActivityTitle();
					msg.description = singleActivityRes.getTddActivity().getActivityDescription();
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
			UiHelper.ShowOneToast(EditRegistrationEditActivity.this, "?????????????????????????????????????????????");
			Intent intent = new Intent();
			intent.setAction("Refresh_FindChairDetailActivity");
			intent.putExtra("registration_state", "?????????");
			sendBroadcast(intent);
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
		bundle.putString("title", singleActivityRes.getTddActivity().getActivityTitle());
		bundle.putString("imageUrl", singleActivityRes.getTddActivity().getShareImg());
		bundle.putString("targetUrl", singleActivityRes.getTddActivity().getShareUrl());
		bundle.putString("summary", singleActivityRes.getTddActivity().getActivityDescription());
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
		mTencent.shareToQQ(EditRegistrationEditActivity.this, params, new BaseUiListener() {
			protected void doComplete(JSONObject values) {
				showResult("shareToQQ:", "onComplete");
			}

			@Override
			public void onError(UiError e) {
				showResult("shareToQQ:", "????????????");
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
			showResult("onError:", "????????????????????????????????????");
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
				UiHelper.ShowOneToast(EditRegistrationEditActivity.this, msg);
				popupWindow.dismiss();
				finish();
			}
		});
	}

}
