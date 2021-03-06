package com.newland.wstdd.mine.receiptaddress;

import com.newland.wstdd.R;
import com.newland.wstdd.R.layout;
import com.newland.wstdd.R.menu;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.mine.receiptaddress.MineEditReceiptAddressActivity.MyAddressBroadcastReceiver;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineAddAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanrequest.TddDeliverAddr;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;
import com.newland.wstdd.mine.receiptaddress.handle.MineAddAddressHandle;
import com.newland.wstdd.mine.receiptaddress.handle.MineReceiptAddressHandle;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 增加收货地址
 * 
 * @author Administrator
 * 
 */
public class MineAddReceiveAddressActivity extends BaseFragmentActivity implements OnPostListenerInterface {
	AppContext appContext = AppContext.getAppContext();
	private EditText acceptPeopleEditText;// 收获人
	private EditText acceptPhoneEditText;// 手机号码
	private TextView acceptZoneTextView;// 所在区域
	private EditText acceptAddrDetailEditText;// 详细地址
	private EditText acceptPostCodeEditText;// 邮政编码
	private String provinceString;//省份
	private String cityString;//城市

	private EditReceiptAddressPopupWindow popWin;
	MyAddressBroadcastReceiver myAddressBroadcastReceiver;
	
	// 服务器返回的信息
	MineAddAddressRes mineAddAddressRes;// 地址列表
	MineAddAddressHandle handler = new MineAddAddressHandle(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_add_receive_address);
		setTitle();
		initView();

	}

	public void initView() {
		acceptPeopleEditText = (EditText) findViewById(R.id.minereceiptaddress_receiptname_edt);
		acceptPhoneEditText = (EditText) findViewById(R.id.minereceiptaddress_phone_edt);
		acceptZoneTextView = (TextView) findViewById(R.id.minereceiptaddress_area_content_tv);
		acceptAddrDetailEditText = (EditText) findViewById(R.id.minereceiptaddress_address_edt);
		acceptPostCodeEditText = (EditText) findViewById(R.id.minereceiptaddress_postalcode_edt);
	
		acceptZoneTextView.setOnClickListener(this);
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
			if (dialog != null) {
				dialog.dismiss();
			}
			mineAddAddressRes = (MineAddAddressRes) obj;
			if (mineAddAddressRes != null) {
//				UiHelper.ShowOneToast(this, "新增/列表 请求成功");
//				if (mineAddAddressRes.getAddresses().size() > 0) {
//					UiHelper.ShowOneToast(this, "获取地址成功！！！" + mineAddAddressRes.getAddresses().get(0).getAddress());
//
//				}
				finish();
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
	protected void processMessage(Message msg) {

	}

	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					MineAddAddressReq addAddressReq = new MineAddAddressReq();
					TddDeliverAddr tddDeliverAddr = new TddDeliverAddr();
					tddDeliverAddr.setConnectUserName(acceptPeopleEditText.getText().toString());// 收货人名字
					tddDeliverAddr.setConnectPhone(acceptPhoneEditText.getText().toString());// 收货人电话
					tddDeliverAddr.setDistrict(acceptZoneTextView.getText().toString());// 行政区域
					tddDeliverAddr.setAddress(acceptAddrDetailEditText.getText().toString());// 详细地址
					
					
					//缺少邮政编码 
					tddDeliverAddr.setZipCode(acceptPostCodeEditText.getText().toString());
//					tddDeliverAddr.setAddressId();// 配送地址标识
					
					tddDeliverAddr.setProvince(provinceString);//省份
					tddDeliverAddr.setCity(cityString);//城市
					tddDeliverAddr.setIsDefault((short)2);
					
					tddDeliverAddr.setUserId(appContext.getUserId());
//					tddDeliverAddr.setUserId("04d5cf60311a11e5256970e606dc2da8");
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
			((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MineAddReceiveAddressActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			popWindow();
			break;
		default:
			break;
		}
	}
	private void popWindow() {
		// 设置透明度
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1f;
		getWindow().setAttributes(lp);
		popWin = new EditReceiptAddressPopupWindow(MineAddReceiveAddressActivity.this, null);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);

			}
		});

		// 显示窗口
		popWin.showAtLocation(MineAddReceiveAddressActivity.this.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	private void judgeOk() {
//		acceptPeopleEditText.setText("李");
//		acceptPhoneEditText.setText("18020008000");
//		acceptAddrDetailEditText.setText("新大陆");
		if (acceptAddrDetailEditText != null && StringUtil.isNotEmpty(acceptAddrDetailEditText.getText().toString())
				&& acceptPeopleEditText != null && StringUtil.isNotEmpty(acceptPeopleEditText.getText().toString())
				&& acceptPhoneEditText != null && StringUtil.isNotEmpty(acceptPhoneEditText.getText().toString())
				&& acceptZoneTextView != null && StringUtil.isNotEmpty(acceptZoneTextView.getText().toString())
				&& acceptPostCodeEditText != null && StringUtil.isNotEmpty(acceptPostCodeEditText.getText().toString())) {
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

	class MyAddressBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			String proviceNameString=bundle.getString("mCurrentProviceName");
			String cityNameString=bundle.getString("mCurrentCityName");
			String districtNameString=bundle.getString("mCurrentDistrictName");
			String zipCodeString=bundle.getString("mCurrentZipCode");//邮政编码
			System.out.println("获取到："+proviceNameString+ "," +cityNameString + "," +districtNameString +","+zipCodeString);
			acceptZoneTextView.setText(proviceNameString+cityNameString+districtNameString);
			acceptPostCodeEditText.setText(zipCodeString);
			provinceString= proviceNameString;
			cityString =cityNameString;
		}
	}
}
