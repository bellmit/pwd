package com.newland.wstdd.mine.receiptaddress;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineAddAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.TddDeliverAddr;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;
import com.newland.wstdd.mine.receiptaddress.handle.MineAddAddressHandle;
import com.newland.wstdd.mine.receiptaddress.handle.MineReceiptAddressHandle;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

/**
 * 增加收货地址
 * 
 * @author Administrator
 * 
 */
public class MineAddReceiveAddressActivity extends BaseFragmentActivity implements OnPostListenerInterface {
	private static final String TAG = "MineAddReceiveAddressActivity";//收集异常日志tag
	private EditText minereceiptaddress_receiptname_edt;// 收获人
	private EditText minereceiptaddress_phone_edt;// 手机号码
	private TextView minereceiptaddress_area_content_tv;// 所在区域
	private EditText minereceiptaddress_address_edt;// 详细地址
	private EditText minereceiptaddress_postalcode_edt;// 邮政编码
	private String provinceString;// 省份
	private String cityString;// 城市
	private ImageView setting_rightexpand_iv;
	private EditReceiptAddressPopupWindow popWin;
	MyAddressBroadcastReceiver myAddressBroadcastReceiver;

	// 服务器返回的信息
	MineAddAddressRes mineAddAddressRes;// 地址列表
	MineAddAddressHandle handler = new MineAddAddressHandle(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_mine_add_receive_address);
		
		 /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		setTitle();
		initView();

	}

	@Override
	protected void onDestroy() {
		/** 收集异常日志 */
		LogManager.getManager(getApplicationContext()).unregisterActivity(this);
		/** 收集异常日志 */
		super.onDestroy();
	}
	public void initView() {
		minereceiptaddress_receiptname_edt = (EditText) findViewById(R.id.minereceiptaddress_receiptname_edt);
		minereceiptaddress_phone_edt = (EditText) findViewById(R.id.minereceiptaddress_phone_edt);
		minereceiptaddress_area_content_tv = (TextView) findViewById(R.id.minereceiptaddress_area_content_tv);
		minereceiptaddress_address_edt = (EditText) findViewById(R.id.minereceiptaddress_address_edt);
		minereceiptaddress_postalcode_edt = (EditText) findViewById(R.id.minereceiptaddress_postalcode_edt);
		setting_rightexpand_iv = (ImageView) findViewById(R.id.setting_rightexpand_iv);
		minereceiptaddress_area_content_tv.setOnClickListener(this);
	}

	private void setTitle() {
		ImageView leftIv = (ImageView) findViewById(R.id.head_left_iv);
		TextView centerTitle = (TextView) findViewById(R.id.head_center_title);
		TextView rightTv = (TextView) findViewById(R.id.head_right_tv);
		leftIv.setVisibility(View.VISIBLE);
		centerTitle.setText("新增收货地址");
		rightTv.setText("添加");
		rightTv.setTextColor(getResources().getColor(R.color.red));
		rightTv.setVisibility(View.VISIBLE);
		leftIv.setOnClickListener(this);
		rightTv.setOnClickListener(this);
	}

	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		switch (responseId) {
		// 取得设置的列表
		case MineReceiptAddressHandle.GET_ADDRESS_LIST:
			if (progressDialog != null) {
				progressDialog.setContinueDialog(false);
			}
			mineAddAddressRes = (MineAddAddressRes) obj;
			if (mineAddAddressRes != null) {
				// UiHelper.ShowOneToast(this, "新增/列表 请求成功");
				// if (mineAddAddressRes.getAddresses().size() > 0) {
				// UiHelper.ShowOneToast(this, "获取地址成功！！！" +
				// mineAddAddressRes.getAddresses().get(0).getAddress());
				//
				// }
				finish();
			}
			break;
		default:
			break;
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
	protected void processMessage(Message msg) {

	}

	@Override
	public void refresh() {
		super.refreshDialog();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MineAddAddressReq addAddressReq = new MineAddAddressReq();
					TddDeliverAddr tddDeliverAddr = new TddDeliverAddr();
					tddDeliverAddr.setConnectUserName(minereceiptaddress_receiptname_edt.getText().toString());// 收货人名字
					tddDeliverAddr.setConnectPhone(minereceiptaddress_phone_edt.getText().toString());// 收货人电话
					tddDeliverAddr.setDistrict(minereceiptaddress_area_content_tv.getText().toString());// 行政区域
					tddDeliverAddr.setAddress(minereceiptaddress_address_edt.getText().toString());// 详细地址

					// 缺少邮政编码
					tddDeliverAddr.setZipCode(minereceiptaddress_postalcode_edt.getText().toString());
					// tddDeliverAddr.setAddressId();// 配送地址标识

					tddDeliverAddr.setProvince(provinceString);// 省份
					tddDeliverAddr.setCity(cityString);// 城市
					tddDeliverAddr.setIsDefault((short) 2);
					if (null != AppContext.getAppContext())
						tddDeliverAddr.setUserId(AppContext.getAppContext().getUserId());
					// tddDeliverAddr.setUserId("04d5cf60311a11e5256970e606dc2da8");
					addAddressReq.setTddDeliverAddr(tddDeliverAddr);
					RetMsg<MineAddAddressRes> ret = mgr.getMineReceiptAddressInfo(addAddressReq);// 泛型类，
					Message message = new Message();
					message.what = MineAddAddressHandle.GET_ADDRESS_LIST;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						mineAddAddressRes = (MineAddAddressRes) ret.getObj();
						message.obj = mineAddAddressRes;
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
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.head_left_iv:// 返回
			finish();
			break;
		case R.id.head_right_tv:// 完成
			// 完成添加并提交 需要做一些字符串方面的判断
			judgeOk();// 判断输入什么的是否符合需求
			break;
		case R.id.minereceiptaddress_area_content_tv:
			((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MineAddReceiveAddressActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			popWindow();
			break;
		default:
			break;
		}
	}

	private void popWindow() {
		// 设置透明度
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		popWin = new EditReceiptAddressPopupWindow(MineAddReceiveAddressActivity.this, null);
		popWin.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				setting_rightexpand_iv.setImageDrawable(getResources().getDrawable(R.drawable.setting_rightexpand));
			}
		});
		setting_rightexpand_iv.setImageDrawable(getResources().getDrawable(R.drawable.setting_down_expand_icon));
		// 显示窗口
		popWin.showAtLocation(MineAddReceiveAddressActivity.this.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	private void judgeOk() {
		// acceptPeopleEditText.setText("李");
		// acceptPhoneEditText.setText("18020008000");
		// acceptAddrDetailEditText.setText("新大陆");
		if (minereceiptaddress_address_edt != null && StringUtil.isNotEmpty(minereceiptaddress_address_edt.getText().toString()) && minereceiptaddress_receiptname_edt != null
				&& StringUtil.isNotEmpty(minereceiptaddress_receiptname_edt.getText().toString()) && minereceiptaddress_phone_edt != null
				&& StringUtil.isNotEmpty(minereceiptaddress_phone_edt.getText().toString()) && minereceiptaddress_area_content_tv != null
				&& StringUtil.isNotEmpty(minereceiptaddress_area_content_tv.getText().toString()) && minereceiptaddress_postalcode_edt != null
				&& StringUtil.isNotEmpty(minereceiptaddress_postalcode_edt.getText().toString())) {
			refresh();
		} else {
			UiHelper.ShowOneToast(this, "数据未填充完全");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		myAddressBroadcastReceiver = new MyAddressBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(AppContext.ACTION_ADDRESS);
		registerReceiver(myAddressBroadcastReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(myAddressBroadcastReceiver);
	}

	class MyAddressBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			String proviceNameString = bundle.getString("mCurrentProviceName");
			String cityNameString = bundle.getString("mCurrentCityName");
			String districtNameString = bundle.getString("mCurrentDistrictName");
			String zipCodeString = bundle.getString("mCurrentZipCode");// 邮政编码
			System.out.println("获取到：" + proviceNameString + "," + cityNameString + "," + districtNameString + "," + zipCodeString);
			minereceiptaddress_area_content_tv.setText(proviceNameString + cityNameString + districtNameString);
			minereceiptaddress_postalcode_edt.setText(zipCodeString);
			provinceString = proviceNameString;
			cityString = cityNameString;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (StringUtil.isNotEmpty(minereceiptaddress_receiptname_edt.getText().toString()) || StringUtil.isNotEmpty(minereceiptaddress_phone_edt.getText().toString())
					|| StringUtil.isNotEmpty(minereceiptaddress_area_content_tv.getText().toString()) || StringUtil.isNotEmpty(minereceiptaddress_address_edt.getText().toString())
					|| StringUtil.isNotEmpty(minereceiptaddress_postalcode_edt.getText().toString())) {
				AlertDialog dialog = new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("确定退出吗？")
//						.setView(layout)
						.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
				dialog.setCanceledOnTouchOutside(false);
			} else {
				finish();
			}
			return true;
		}
		return false;
	}
}
