/**
 * 
 */
package com.newland.wstdd.mine.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import com.newland.wstdd.R;
import com.newland.wstdd.R.id;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.beanrequest.MinePersonInfoGetReq;
import com.newland.wstdd.mine.beanresponse.MinePersonInfoGetRes;
import com.newland.wstdd.mine.beanresponse.TddUserCertificate;
import com.newland.wstdd.mine.personalcenter.bean.MineEditPersonReq;
import com.newland.wstdd.mine.personalcenter.bean.MineEditPersonRes;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

/**
 * 我的-个人信息
 * 
 * @author H81 2015-11-10
 * 
 */
public class MinePersonalCenterActivity extends BaseFragmentActivity implements OnPostListenerInterface {
	// 头像
	private TextView mActivity_mine_personalcenter_icon_tv;
	private ImageView mActivity_mine_personalcenter_icon_extendable_iv;
	private ImageView mActivity_mine_personalcenter_icon_iv;
	private RelativeLayout mActivity_mine_personalcenter_icon_rl;
	// 昵称
	private ImageView mActivity_mine_personalcenter_nickname_extendable_iv;
	private EditText mActivity_mine_personalcenter_nickname_content_edt;
	private LinearLayout mActivity_mine_personalcenter_nickname_ll;

	private EditText mActivity_mine_personalcenter_telnum_edt;// 手机
	// 我的二维码
	private ImageView mActivity_mine_personalcenter_qrcode_extendable_iv;
	private ImageView mActivity_mine_personalcenter_qrcode_iv;
	private RelativeLayout mActivity_mine_personalcenter_qrcode_rl;
	// 绑定账号
	private ImageView mActivity_mine_personalcenter_bindonaccount_extendable_iv;
	private RelativeLayout mActivity_mine_personalcenter_bindonaccount_rl;
	// 身份证
	private ImageView mActivity_mine_personalcenter_bindonaccount_qzone_iv;
	private ImageView mActivity_mine_personalcenter_bindonaccount_xinlang_iv;
	private ImageView mActivity_mine_personalcenter_bindonaccount_weixin_iv;
	// 身份证
	private ImageView mActivity_mine_personalcenter_idcard_extendable_iv;
	private EditText mActivity_mine_personalcenter_idcard_content_edt;
	private LinearLayout mActivity_mine_personalcenter_idcard_ll;
	// 性别
	private ImageView mActivity_mine_personalcenter_sex_extendable_iv;
	private TextView mActivity_mine_personalcenter_sex_content_tv;
	private LinearLayout mActivity_mine_personalcenter_sex_ll;
	// 邮箱
	private ImageView mActivity_mine_personalcenter_mailbox_extendable_iv;
	private EditText mActivity_mine_personalcenter_mailbox_content_edt;
	private LinearLayout mActivity_mine_personalcenter_mailbox_ll;

	/** 服务器返回的信息 */
	MinePersonInfoGetRes minePersonInfoGetRes;
	private MineEditPersonRes mineEditPersonRes;
	MinePersonInfoGetHandle handler = new MinePersonInfoGetHandle(this);
	String myQucodeString;// 二维码url
	String nikeNameString;// 昵称
	String headImgUrl;// 头像url

	/** 服务器返回的信息 */
	@Override
	protected void processMessage(Message msg) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mine_personalcenter);
		setTitle();
		initView();
		refresh();
	}

	private void setTitle() {
		ImageView leftBtn = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		leftBtn.setVisibility(View.VISIBLE);
		centerTitle.setText("个人信息");
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setText("保存");
		rightTv.setTextColor(getResources().getColor(R.color.red));
		rightTv.setOnClickListener(this);
		leftBtn.setOnClickListener(this);
	}

	public void initView() {
		mActivity_mine_personalcenter_icon_rl = (RelativeLayout) findViewById(R.id.activity_mine_personalcenter_icon_rl);
		mActivity_mine_personalcenter_icon_tv = (TextView) findViewById(R.id.activity_mine_personalcenter_icon_tv);
		mActivity_mine_personalcenter_icon_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_icon_extendable_iv);
		mActivity_mine_personalcenter_icon_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_icon_iv);
		mActivity_mine_personalcenter_nickname_ll = (LinearLayout) findViewById(R.id.activity_mine_personalcenter_nickname_ll);
		mActivity_mine_personalcenter_nickname_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_nickname_extendable_iv);
		mActivity_mine_personalcenter_nickname_content_edt = (EditText) findViewById(R.id.activity_mine_personalcenter_nickname_content_edt);
		mActivity_mine_personalcenter_telnum_edt = (EditText) findViewById(R.id.activity_mine_personalcenter_telnum_edt);
		mActivity_mine_personalcenter_qrcode_rl = (RelativeLayout) findViewById(R.id.activity_mine_personalcenter_qrcode_rl);
		mActivity_mine_personalcenter_qrcode_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_qrcode_extendable_iv);
		mActivity_mine_personalcenter_qrcode_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_qrcode_iv);
		mActivity_mine_personalcenter_bindonaccount_rl = (RelativeLayout) findViewById(R.id.activity_mine_personalcenter_bindonaccount_rl);
		mActivity_mine_personalcenter_bindonaccount_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_bindonaccount_extendable_iv);
		mActivity_mine_personalcenter_bindonaccount_qzone_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_bindonaccount_azone_iv);
		mActivity_mine_personalcenter_bindonaccount_xinlang_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_bindonaccount_xinlang_iv);
		mActivity_mine_personalcenter_bindonaccount_weixin_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_bindonaccount_weixin_iv);
		mActivity_mine_personalcenter_idcard_ll = (LinearLayout) findViewById(R.id.activity_mine_personalcenter_idcard_ll);
		mActivity_mine_personalcenter_idcard_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_idcard_extendable_iv);
		mActivity_mine_personalcenter_idcard_content_edt = (EditText) findViewById(R.id.activity_mine_personalcenter_idcard_content_edt);
		mActivity_mine_personalcenter_sex_ll = (LinearLayout) findViewById(R.id.activity_mine_personalcenter_sex_ll);
		mActivity_mine_personalcenter_sex_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_sex_extendable_iv);
		mActivity_mine_personalcenter_sex_content_tv = (TextView) findViewById(R.id.activity_mine_personalcenter_sex_content_tv);
		mActivity_mine_personalcenter_mailbox_ll = (LinearLayout) findViewById(R.id.activity_mine_personalcenter_mailbox_ll);
		mActivity_mine_personalcenter_mailbox_extendable_iv = (ImageView) findViewById(R.id.activity_mine_personalcenter_mailbox_extendable_iv);
		mActivity_mine_personalcenter_mailbox_content_edt = (EditText) findViewById(R.id.activity_mine_personalcenter_mailbox_content_edt);
		mActivity_mine_personalcenter_icon_rl.setOnClickListener(this);
		mActivity_mine_personalcenter_nickname_ll.setOnClickListener(this);
		mActivity_mine_personalcenter_qrcode_rl.setOnClickListener(this);
		mActivity_mine_personalcenter_bindonaccount_rl.setOnClickListener(this);
		mActivity_mine_personalcenter_idcard_ll.setOnClickListener(this);
		mActivity_mine_personalcenter_sex_ll.setOnClickListener(this);
		mActivity_mine_personalcenter_mailbox_ll.setOnClickListener(this);

		setData();
	}

	private void setData() {
		mActivity_mine_personalcenter_nickname_content_edt.setText(AppContext.getAppContext().getNickName());
		ImageDownLoad.getDownLoadCircleImg(AppContext.getAppContext().getHeadImgUrl(), mActivity_mine_personalcenter_icon_iv);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.head_left_iv:
			this.finish();
			break;
		case R.id.head_right_tv:// 保存
			editPersonalInformation();
			break;
		case R.id.activity_mine_personalcenter_icon_rl:// 头像
//			UiHelper.ShowOneToast(this, "未开发");
			break;
		case R.id.activity_mine_personalcenter_nickname_ll:// 昵称
//			UiHelper.ShowOneToast(this, "未开发");
			break;
		case R.id.activity_mine_personalcenter_qrcode_rl:// 我的二维码
			intent = new Intent(this, MineMyQRCodeActivity.class);
			intent.putExtra("nikeNameString", nikeNameString);
			intent.putExtra("headImgUrl", headImgUrl);
			intent.putExtra("myQucodeString", myQucodeString);
			startActivity(intent);
			break;
		case R.id.activity_mine_personalcenter_bindonaccount_rl:// 绑定账号
			UiHelper.ShowOneToast(this, "未开发");
			break;
		case R.id.activity_mine_personalcenter_idcard_ll:// 身份证
//			UiHelper.ShowOneToast(this, "未开发");
			break;
		case R.id.activity_mine_personalcenter_sex_ll:// 性别
			getStatePopWind();//性别popwindow
			statePopupWindow.showAsDropDown(v);
			break;
		case R.id.activity_mine_personalcenter_mailbox_ll:// 邮箱
//			UiHelper.ShowOneToast(this, "未开发");
			break;
		default:
			break;
		}
	}

	/**
	 * 个人信息编辑
	 * 
	 */
	private void editPersonalInformation() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MineEditPersonReq reqInfo = new MineEditPersonReq();

					TddUserCertificate tddUserCertificate = new TddUserCertificate();
					// 8个元素
					tddUserCertificate.setCerStatus(1);// 状态 1.未认证 30.初步认证
														// 90.身份认证
					tddUserCertificate.setEmail(mActivity_mine_personalcenter_mailbox_content_edt.getText().toString());
					tddUserCertificate.setHeadimgurl(AppContext.getAppContext().getHeadImgUrl());
					tddUserCertificate.setMobilePhone(StringUtil.noNull(mActivity_mine_personalcenter_telnum_edt.getText().toString()));
					if (StringUtil.isNotEmpty(mActivity_mine_personalcenter_nickname_content_edt.getText().toString())) {
						tddUserCertificate.setNickName(mActivity_mine_personalcenter_nickname_content_edt.getText().toString());
					} else {
						tddUserCertificate.setNickName(AppContext.getAppContext().getNickName());
					}
					tddUserCertificate.setSex(StringUtil.getIntSex(mActivity_mine_personalcenter_sex_content_tv.getText().toString()));
					tddUserCertificate.setUserId(AppContext.getAppContext().getUserId());
					if (StringUtil.isNotEmpty(mActivity_mine_personalcenter_idcard_content_edt.getText().toString())) {
						if (StringUtil.isPid(mActivity_mine_personalcenter_idcard_content_edt.getText().toString())) {
							tddUserCertificate.setIdentity(mActivity_mine_personalcenter_idcard_content_edt.getText().toString());
							
							reqInfo.setTddUserCertificate(tddUserCertificate);
							RetMsg<MineEditPersonRes> ret = mgr.getMineEditPersonInfo(reqInfo);// 泛型类，
							Message message = new Message();
							message.what = MinePersonInfoGetHandle.PERSON_EDIT_INFO;// 设置死
							// 访问服务器成功 1 否则访问服务器失败
							if (ret.getCode() == 1) {
								mineEditPersonRes = (MineEditPersonRes) ret.getObj();
								message.obj = mineEditPersonRes;
							} else {
								message.obj = ret.getMsg();
							}
							handler.sendMessage(message);
						} else {
							UiHelper.ShowOneToast(MinePersonalCenterActivity.this, "身份证格式有误");
						}
					}else {
						tddUserCertificate.setIdentity(mActivity_mine_personalcenter_idcard_content_edt.getText().toString());
						
						reqInfo.setTddUserCertificate(tddUserCertificate);
						RetMsg<MineEditPersonRes> ret = mgr.getMineEditPersonInfo(reqInfo);// 泛型类，
						Message message = new Message();
						message.what = MinePersonInfoGetHandle.PERSON_EDIT_INFO;// 设置死
						// 访问服务器成功 1 否则访问服务器失败
						if (ret.getCode() == 1) {
							mineEditPersonRes = (MineEditPersonRes) ret.getObj();
							message.obj = mineEditPersonRes;
						} else {
							message.obj = ret.getMsg();
						}
						handler.sendMessage(message);
					}
					

				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** 个人信息获取 */
	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MinePersonInfoGetReq reqInfo = new MinePersonInfoGetReq();
					reqInfo.setUserId(AppContext.getAppContext().getUserId());
					RetMsg<MinePersonInfoGetRes> ret = mgr.getMinePersonInfoGetInfoMsg(reqInfo);// 泛型类，
					Message message = new Message();
					message.what = MinePersonInfoGetHandle.PERSON_INFO_GET;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						minePersonInfoGetRes = (MinePersonInfoGetRes) ret.getObj();
						message.obj = minePersonInfoGetRes;
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
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			switch (responseId) {
			case MinePersonInfoGetHandle.PERSON_INFO_GET:
				if (dialog != null) {
					dialog.dismiss();
				}
				minePersonInfoGetRes = (MinePersonInfoGetRes) obj;
				if (minePersonInfoGetRes != null) {
//					UiHelper.ShowOneToast(MinePersonalCenterActivity.this, "发现界面请求成功");

					// 个人信息
					TddUserCertificate tddUserCertificate = minePersonInfoGetRes.getTddUserCertificate();
					if (tddUserCertificate != null) {
						settddUserCertificate(tddUserCertificate);
					}
					myQucodeString = minePersonInfoGetRes.getMyQucode();// 二维码

					// 是否绑定qq、微信、新浪微博
					String qqOpenIdString = minePersonInfoGetRes.getQq_open_id();
					String weChatUnionIdString = minePersonInfoGetRes.getWechat_union_id();
					String weiboOpenIdString = minePersonInfoGetRes.getWeibo_open_id();
					if (StringUtil.isNotEmpty(qqOpenIdString)) {// qq绑定
						mActivity_mine_personalcenter_bindonaccount_qzone_iv.setImageDrawable(getResources().getDrawable(R.drawable.qzonebound));
					} else {
						mActivity_mine_personalcenter_bindonaccount_qzone_iv.setImageDrawable(getResources().getDrawable(R.drawable.qzone));
					}
					if (StringUtil.isNotEmpty(weChatUnionIdString)) {// 微信绑定
						mActivity_mine_personalcenter_bindonaccount_weixin_iv.setImageDrawable(getResources().getDrawable(R.drawable.wechatbound));
					} else {
						mActivity_mine_personalcenter_bindonaccount_weixin_iv.setImageDrawable(getResources().getDrawable(R.drawable.wechat));
					}
					if (StringUtil.isNotEmpty(weiboOpenIdString)) {// 微博绑定
						mActivity_mine_personalcenter_bindonaccount_xinlang_iv.setImageDrawable(getResources().getDrawable(R.drawable.weibobound));
					} else {
						mActivity_mine_personalcenter_bindonaccount_xinlang_iv.setImageDrawable(getResources().getDrawable(R.drawable.weibo));
					}
				}
				break;
			// 编辑个人信息
			case MinePersonInfoGetHandle.PERSON_EDIT_INFO:
				if (dialog != null) {
					dialog.dismiss();
				}
				mineEditPersonRes = (MineEditPersonRes) obj;
				if (mineEditPersonRes != null) {
					UiHelper.ShowOneToast(this, mineEditPersonRes.getGetResMess());
				}
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 设置个人消息
	 * 
	 * @param tddUserCertificate
	 */
	private void settddUserCertificate(TddUserCertificate tddUserCertificate) {
		// 头像
		if (StringUtil.isNotEmpty(tddUserCertificate.getHeadimgurl())) {
			ImageDownLoad.getDownLoadCircleImg(tddUserCertificate.getHeadimgurl(), mActivity_mine_personalcenter_icon_iv);
		} else {
			mActivity_mine_personalcenter_icon_iv.setImageDrawable(getResources().getDrawable(R.drawable.defaultheadimg));
		}
		// 昵称
		if (StringUtil.isNotEmpty(tddUserCertificate.getNickName())) {
			mActivity_mine_personalcenter_nickname_content_edt.setText(tddUserCertificate.getNickName());
		} else {
			mActivity_mine_personalcenter_nickname_content_edt.setText("");
		}
		// 手机号
		if (StringUtil.isNotEmpty(tddUserCertificate.getMobilePhone())) {
			mActivity_mine_personalcenter_telnum_edt.setText(tddUserCertificate.getMobilePhone());
		} else {
			mActivity_mine_personalcenter_telnum_edt.setText("");
		}
		// 身份证
		if (StringUtil.isNotEmpty(tddUserCertificate.getIdentity())) {
			mActivity_mine_personalcenter_idcard_content_edt.setText(tddUserCertificate.getIdentity());
		} else {
			mActivity_mine_personalcenter_idcard_content_edt.setText("");
		}
		// 性别
		if (tddUserCertificate.getSex() instanceof Integer) {
			mActivity_mine_personalcenter_sex_content_tv.setText(StringUtil.getSex(tddUserCertificate.getSex()));// Int（1是男性，2是女性，0是未知）',
		} else {
			mActivity_mine_personalcenter_sex_content_tv.setText("未填写");// Int（1是男性，2是女性，0是未知）',
		}
		// 邮箱
		if (StringUtil.isNotEmpty(tddUserCertificate.getEmail())) {
			mActivity_mine_personalcenter_mailbox_content_edt.setText(tddUserCertificate.getEmail());
		} else {
			mActivity_mine_personalcenter_mailbox_content_edt.setText("");
		}
	}

	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}

	}

	/**
	 * 获取活动状态popwindow
	 */
	private PopupWindow statePopupWindow;
	private void getStatePopWind() {
		if (statePopupWindow != null) {
			statePopupWindow.dismiss();
			return;
		} else {
			initStatePopupWindow();
		}
	}
	/**
	 * 初始化活动状态的popwindow
	 */
	TextView closeApplyTextView,openApplyTextView;//开启、关闭报名
	private void initStatePopupWindow() {
		//TODO
		View popupWindow_view1 = this.getLayoutInflater().inflate(R.layout.activity_mine_personal_sexpopwindow, null, false);
		statePopupWindow = new PopupWindow(popupWindow_view1, AppContext.getAppContext().getScreenWidth() , LayoutParams.WRAP_CONTENT, true);
		statePopupWindow.setOutsideTouchable(true);// 点击区域外，关闭
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
				mActivity_mine_personalcenter_sex_content_tv.setText("男");
			}
		});
		openApplyTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openApplyTextView.setTextColor(getResources().getColor(R.color.black));
				closeApplyTextView.setTextColor(getResources().getColor(R.color.grey_text));
				closeApplyTextView.setTextSize(14);
				openApplyTextView.setTextSize(18);
				mActivity_mine_personalcenter_sex_content_tv.setText("女");
			}
		});
		// 设置透明度
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		statePopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
//		statePopupWindow.setFocusable(true);
		
		statePopupWindow.update();
		// 显示窗口 位置
		statePopupWindow.showAtLocation(this.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}
}
